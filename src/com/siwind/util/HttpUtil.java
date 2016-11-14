package com.siwind.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by admin on 2016/11/10.
 */
public class HttpUtil {

    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     *
     */
    private HttpURLConnection httpConn = null;

    private String strUrl;
    private HashMap<String,String> heads = new HashMap<String,String>();
    private HashMap<String,String> params = new HashMap<String,String>();
    private StringBuffer strEntity = new StringBuffer();

    /**
     *
     * @param strUrl
     */
    public HttpUtil(String strUrl){
        this.strUrl = strUrl;
    }

    /**
     * 添加报文头部属性
     *
     * @param name
     * @param value
     * @return
     */
    public boolean addHeaderField(String name, String value) {
        if (name.length() == 0 || (value.length() == 0)) {
            return false;
        }
        heads.put(name.trim(), value.trim());
        return true;

    }

    /**
     * 添加报文头部属性
     *
     * @param nameAndValue like: "name:value"
     * @return
     */
    public boolean addHeaderField(String nameAndValue) {
        if (nameAndValue.length() == 0)
            return false;
        int mid = nameAndValue.indexOf(':');
        if (mid == -1)
            return false;
        String name = nameAndValue.substring(0, mid);
        String value = nameAndValue.substring(mid + 1, nameAndValue.length());
        return addHeaderField(name, value);
    }

    /**
     * Add entity content
     * @param strEntity
     * @return
     */
    public boolean addEntity(String strEntity){
        this.strEntity.append(strEntity);
        return true;
    }
    /**
     * 添加参数
     *
     * @param name
     * @param value
     * @return
     */
    public boolean addParam(String name, String value) {
        name = name.trim();
        if (name.length() == 0 ) {
            return false;
        }
        value = value.trim();
        if( value == null ){
            value = "";
        }
        params.put(name, value);
        return true;
    }
    /**
     * 添加参数
     *
     * @param names
     * @param values
     * @return
     */
    public boolean addParam(String[] names, String[] values) {
        if (names.length == 0 || (values.length == 0)) {
            return false;
        }
        if( names.length != values.length ) return false;
        for(int i=0; i<names.length;i++){
            addParam(names[i], values[i]);
        }
        return true;
    }
    public boolean addParam(java.util.Map<String, String> p ){
        if( p != null ){
            params.putAll(p);
        }
        return true;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean addBasicAuth(String username, String password){

        String usernameAndPassword = username + ":" + password;

        String key = "Authorization";
        String value = "Basic " + Base64Util.encode( usernameAndPassword.getBytes() );
        return addHeaderField(key,value);
    }
    /**
     * 获得请求的字符串, 例如 name=micke&age=12
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getParams() throws UnsupportedEncodingException {
        StringBuffer sBuffer = new StringBuffer();
        Set<String> keys = params.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String name = it.next();
            String value = params.get(name);
            if( value.length() > 0 ){
                sBuffer.append(name + "=" + URLEncoder.encode(value, "UTF-8") + "&");
            }
            else{
                sBuffer.append(name + "=" +  "&");
            }
        }
        if( sBuffer.length()>0) {
            sBuffer.deleteCharAt(sBuffer.length() - 1);
        }
        return sBuffer.toString();
    }

    /**
     * 获取报文头部属性字符串, 不包含"Host: xxx" , "Content-Length: xxx", 和第一行请求行
     *
     * @return
     */
    private String getHeaderFields() {
        StringBuffer sBuffer = new StringBuffer();
        Set<String> keys = heads.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String name = it.next();
            String value = heads.get(name);
            sBuffer.append(name + ": " + value + "\r\n");
        }
        sBuffer.append("\r\n");

        return sBuffer.toString();

    }

    //==========================

    /**
     *
     * @param method
     * @return ResponseCode
     * @throws Exception
     */
    public int sendRequest(Method method) throws Exception{
        String requrl = this.strUrl;

        if( (method == Method.GET) && !getParams().isEmpty()){
            requrl += "?" + getParams();
        }
        URL url = new URL(requrl);
        httpConn = (HttpURLConnection) url.openConnection();
        //method
        httpConn.setRequestMethod(method.toString());

        //set default User-Agent
        httpConn.setRequestProperty("User-Agent", USER_AGENT);
        //request property
        if( heads!=null && heads.size()>0 ){
            Iterator<String> paramIterator = heads.keySet().iterator();
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                String value = heads.get(key);
                httpConn.setRequestProperty(key,value);
            }
        }


        httpConn.setUseCaches(false);
        httpConn.setDoInput(true); // true if we want to read server's response

        switch (method){
            case GET:
                //httpConn.setDoInput(true); // true if we want to read server's response
                httpConn.setDoOutput(false); // false indicates this is a GET request
                break;
            case POST:
                //httpConn.setDoInput(true); // true indicates the server returns response

                StringBuffer requestParams = new StringBuffer();

                if (params != null && params.size() > 0) {
                    // creates the params string, encode them using URLEncoder
                    Iterator<String> paramIterator = params.keySet().iterator();
                    while (paramIterator.hasNext()) {
                        String key = paramIterator.next();
                        String value = params.get(key);
                        requestParams.append(URLEncoder.encode(key, "UTF-8"));
                        requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
                        requestParams.append("&");
                    }

                }

                requestParams.append(strEntity); //add entity content

                if( !requestParams.toString().isEmpty()) { //request content is NOT empty.
                    httpConn.setDoOutput(true); // true indicates POST request
                    // sends POST data
                    OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
                    writer.write(requestParams.toString());
                    writer.flush();
                    writer.close();
                }
                break;
            default:
                break;
        }

        return httpConn.getResponseCode();

    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String getResponseHeader() throws IOException{
        String value = "";
        if( httpConn!=null ){
            value = httpConn.getResponseMessage();
        }
        return value;
    }
    /**
     *
     * @return
     * @throws IOException
     */
    public String[] readResponse() throws IOException{
        InputStream inputStream = null;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> response = new ArrayList<String>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();

        return (String[]) response.toArray(new String[0]);
    }
    /**
     *
     */
    public void Close(){
        if (httpConn!= null){
            httpConn.disconnect();
            httpConn = null;
        }
    }
    //==========================

    /**
     *
     */
    public enum Method{
        //method available
        GET("GET"), POST("POST"), PUT("PUT"),DELETE("DELETE");


        //method name
        private String method;
        private Method(String method){
            this.method = method;
        }

        @Override
        public String toString() {
            return this.method;
        }
    }

    //===========================
    public enum Status{
        //
        OK(200),NOT_FOUND(404),MOVE_PERMANENTLY(301);

        private int statusCode;
        private Status(int statusCode){
            this.statusCode = statusCode;
        }

        /**
         * Status Code
         * @return
         */
        public int value(){
            return this.statusCode;
        }
    }
}
