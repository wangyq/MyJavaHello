package com.siwind.hello;

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

    public static void test(){
        System.out.println(enc7());
        System.out.println(enc8());
        System.out.println(dec8("bXl1c2VybmFtZTpteVBhc3N3b3Jk"));
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        test();
    }
}