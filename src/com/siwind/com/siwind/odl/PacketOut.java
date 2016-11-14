package com.siwind.com.siwind.odl;

import com.siwind.util.Base64Util;
import com.siwind.util.HttpUtil;
import io.nayuki.json.Json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by admin on 2016/11/10.
 */
public class PacketOut {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void sendRequestInternal(String strUrl, String method, List<String> pros) throws Exception{
        URL obj = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        //con.setRequestMethod("GET");
        if( (method==null) || (method.isEmpty()) ) {
            con.setRequestMethod("GET");
        }else{
            con.setRequestMethod(method);
        }

        //add default USER_AGENT
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + strUrl);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }

    public static void test1(){
        try {
            sendRequestInternal("http://bbs.byr.cn",null,null);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void testODL() throws Exception{
        String strUrl = "";
        strUrl = "http://192.168.0.90:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:10/node-connector/openflow:10:1";
        strUrl = "http://192.168.0.90:8181/restconf/operational/opendaylight-inventory:nodes";

        HttpUtil http = new HttpUtil(strUrl);
        http.addBasicAuth("admin","admin");
        http.addHeaderField("Accept","application/json");
        http.addHeaderField("Content-type","application/xml");
        if( http.sendRequest(HttpUtil.Method.GET) == HttpUtil.Status.OK.value()){
            System.out.println(http.getResponseHeader());
            String[] res = http.readResponse();
            for(String line : res){
                System.out.println(line);
            }
        }

    }

    public static void testODL1() throws Exception{
        String strJson = buildInput_transmit_packet();
        System.out.println(strJson);
    }

    /**
     *
     * @param strPacket
     * @return
     */
    public static byte[] getRawByteFromString(String strPacket){
        ArrayList<Byte> bytes = new ArrayList<Byte>();

        int i=0;
        char[] c = new char[2];
        while(i<strPacket.length()){
            c[0] = strPacket.charAt(i);
            c[1] = strPacket.charAt(i+1);

            byte b = (byte)Integer.parseInt(String.valueOf(c),16);

            bytes.add(b);
            i+=3;
        }

        byte[] bs = new byte[bytes.size()];
        for(i=0;i<bytes.size();i++){
            bs[i] = bytes.get(i);
        }
        return bs;
    }

    /**
     * construct the input json string
     *
     packet-processing(2013-07-09):
     ......
     rpc transmit-packet {
        description "Sending packet out through openflow device.";
        input {
            uses inv:node-context-ref;

            leaf connection-cookie {
                type connection-cookie;
            }

            leaf egress {
                type inv:node-connector-ref;
            }
            leaf buffer-id {
                type uint32;
            }

            uses raw-packet;
            uses action-type:action-list;
        }
     }
     *
     * @return json string of
     *
     */
    public static String buildInput_transmit_packet(){
        String icmpEchoRequest =
                "40 0c 29 c8 96 6a 00 0c 29 d3 a7 31 08 00 45 00 " +
                "00 3c 7d 1d 00 00 40 01 68 28 c0 a8 0a 15 c0 a8 " +
                "0a 16 08 00 4c 97 02 00 fe c4 61 62 63 64 65 66 " +
                "67 68 69 6a 6b 6c 6d 6e 6f 70 71 72 73 74 75 76 " +
                "77 61 62 63 64 65 66 67 68 69 ";

        byte[] rawPacket=getRawByteFromString(icmpEchoRequest);
        String nodeid="openflow:11";
        String egress="openflow:11:2";
        String desMac="00:0C:29:C8:96:6A"; //00:0C:29:C8:96:6A, the destination of mac address

        Map<String,Object> input = new TreeMap<String,Object>();

        input.put("connection-cookie", 123456);
        //input.put("buffer-id", "-1");  //none buffer data in switch. uint, DON'T SET HERE!

        String strNode = "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='"+nodeid+"']";
        input.put("node",strNode);
        input.put("egress",strNode + "/opendaylight-inventory:node-connector[opendaylight-inventory:id='"+ egress+"']");

        //payload
        input.put("payload", Base64Util.encode(rawPacket));

        // == optional, action-list ==
        //String strSetDstMac = "{\"set-dl-dst-action\": {\"address\": \"" + desMac + "\"}";
        Map<String,Object> mac = new TreeMap<String,Object>();
        mac.put("address", desMac);

        Map<String,Object> setDstMac = new TreeMap<String,Object>();
        setDstMac.put("set-dl-dst-action", mac);
        setDstMac.put("order","1"); //asc-order

        Map<String,Object> outAction = new TreeMap<String,Object>();
        Map<String,Object> outPort = new HashMap<String,Object>();
        outPort.put("output-node-connector",egress);
        outAction.put("output-action", outPort);
        outAction.put("order", "10"); //big number means execute latter.

        List<Object> listAction = new ArrayList<Object>();
        listAction.add(setDstMac);
        listAction.add(outAction);

        //input.put("action", listAction); //add list action
        // == end of optional, action-list ==

        Map<String,Object> packet = new TreeMap<String,Object>();
        packet.put("input", (Object)input);
        return Json.serialize(packet);
    }
    public static void testTransmitPacket() throws Exception{
        String strUrl = "http://192.168.100.126:8181/restconf/operations/packet-processing:transmit-packet";

        HttpUtil http = new HttpUtil(strUrl);
        http.addBasicAuth("admin","admin");
        http.addHeaderField("Accept","application/json");
        http.addHeaderField("Content-type","application/json");

        String strEntity = buildInput_transmit_packet();

        System.out.println(strEntity);

        http.addEntity(strEntity);

        if( http.sendRequest(HttpUtil.Method.POST) == HttpUtil.Status.OK.value()){
            System.out.println(http.getResponseHeader());
            String[] res = http.readResponse();
            for(String line : res){
                System.out.println(line);
            }
        }
    }


    public static String buildInput_send_packet(){
        String icmpEchoRequest =
                "40 0c 29 c8 96 6a 00 0c 29 d3 a7 31 08 00 45 00 " +
                        "00 3c 7d 1d 00 00 40 01 68 28 c0 a8 0a 15 c0 a8 " +
                        "0a 16 08 00 4c 97 02 00 fe c4 61 62 63 64 65 66 " +
                        "67 68 69 6a 6b 6c 6d 6e 6f 70 71 72 73 74 75 76 " +
                        "77 61 62 63 64 65 66 67 68 69 ";

        byte[] rawPacket=getRawByteFromString(icmpEchoRequest);
        String nodeid="openflow:10";
        String egress="openflow:10:2";
        String desMac="00:0C:29:C8:96:6A"; //00:0C:29:C8:96:6A, the destination of mac address

        Map<String,Object> input = new TreeMap<String,Object>();

        String strNode = "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='"+nodeid+"']";
        input.put("node",strNode);
        input.put("egress",strNode + "/opendaylight-inventory:node-connector[opendaylight-inventory:id='"+ egress+"']");

        //payload
        input.put("rawpacket", Base64Util.encode(rawPacket));

        Map<String,Object> packet = new TreeMap<String,Object>();
        packet.put("input", (Object)input);
        return Json.serialize(packet);
    }

    public static void testSendPacket() throws Exception{
        String strUrl;
        strUrl = "http://192.168.0.90:8181/restconf/operations/packet:send-packet";
        strUrl = "http://192.168.100.75:8181/restconf/operations/mypacket:send-packet";

        HttpUtil http = new HttpUtil(strUrl);
        http.addBasicAuth("admin","admin");
        http.addHeaderField("Accept","application/json");
        http.addHeaderField("Content-type","application/json");

        String strEntity = buildInput_send_packet();

        System.out.println(strEntity);

        http.addEntity(strEntity);

        if( http.sendRequest(HttpUtil.Method.POST) == HttpUtil.Status.OK.value()){
            System.out.println(http.getResponseHeader());
            String[] res = http.readResponse();
            for(String line : res){
                System.out.println(line);
            }
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            //test1();
            //testODL();
            testODL1();

            //testTransmitPacket();

            //Integer.parseInt("0",16);
            //testSendPacket();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
