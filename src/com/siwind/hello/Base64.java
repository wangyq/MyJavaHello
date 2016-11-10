package com.siwind.hello;

//import sun.misc.BASE64Decoder;

/**
 * Created by admin on 2016/11/10.
 */
public class Base64 {

    public static String dec8(String str){
        return new String(java.util.Base64.getDecoder().decode(str));
    }
    /**
     * working for jdk8
     * @return
     */
    public static String enc8(){
        String username = "myusername";
        String password = "myPassword";

        String usernameAndPassword = username + ":" + password;
        String authorizationHeaderName = "Authorization";
        String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );
        return authorizationHeaderName + ": " + authorizationHeaderValue;
    }

    /**
     * working for jdk7 and before
     * @return
     */
    public static String enc7(){
        String username = "myusername";
        String password = "myPassword";

        String usernameAndPassword = username + ":" + password;
        String authorizationHeaderName = "Authorization";
        String authorizationHeaderValue = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary( usernameAndPassword.getBytes() );
        return authorizationHeaderName + ": " + authorizationHeaderValue;
    }

    public static String enc(){
        String username = "myusername";
        String password = "myPassword";

        String usernameAndPassword = username + ":" + password;
        String authorizationHeaderName = "Authorization";
        String authorizationHeaderValue = "Basic " + Base64Util.encode( usernameAndPassword.getBytes() );
        return authorizationHeaderName + ": " + authorizationHeaderValue;
    }
    public static String dec(String str){
        return new String(Base64Util.decode(str));
    }

    public static void test(){
        System.out.println(enc());
        System.out.println(dec("bXl1c2VybmFtZTpteVBhc3N3b3Jk"));
        System.out.println(enc7());
        System.out.println(enc8());
        System.out.println(dec8("bXl1c2VybmFtZTpteVBhc3N3b3Jk"));
        System.out.println("testing0 = " + Base64Util.encode("testing0".getBytes()));
        System.out.println(dec8("dGVzdGluZzA="));
        System.out.println(dec("dGVzdGluZzA="));
        System.out.println(dec8("AAAAAAAAAAABAg=="));
        System.out.println(dec("AAAAAAAAAAABAg=="));
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        test();
    }
}
