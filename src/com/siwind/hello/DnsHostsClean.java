package com.siwind.hello;


import com.siwind.util.FileUtil;

import java.io.*;

/**
 * Created by admin on 2017/3/4.
 */
public class DnsHostsClean {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        if( args.length <=0 ){
            System.out.println("Usage: DnsHostClean <hostfile>");

            System.out.println("        Clean the comment line with middle char '#' in hosts file. ");
            System.out.println("        ");

        }else {
            doDnsHostFileClean(args[0]);
            //doDnsHostFileClean("");

        }
    }




    public static void doDnsHostFileClean(String file) {
        //String strFile = "D:/new 1.txt";

        String strFile = FileUtil.getAbsoluteFilePath(file); //"D:/hosts.ipv6.txt";
        String prefix[] = FileUtil.getFilePathPrefixSufix(strFile);

        String strFileOut = prefix[0] + ".new" + prefix[2] + prefix[1];

        System.out.println("Input  file = " + strFile);
        System.out.println("Output file = " + strFileOut);

        BufferedReader inFile = null;
        BufferedWriter outFile = null;

        String line = "";
        try {
            inFile = new BufferedReader(new FileReader(strFile));
            outFile = new BufferedWriter(new FileWriter(strFileOut));

            while ((line = inFile.readLine()) != null) {
                //System.out.println(line);
                line = line.trim();

                if( !line.startsWith("#")){
                    int index = line.indexOf("#");
                    if( index>0 ){
                        line = line.substring(0,index);
                    }
                }

                //System.out.println(line);
                outFile.write(line);
                outFile.newLine();
            }
            inFile.close();
        } catch (Exception e) {
            System.out.println("Input file = " + file);

            e.printStackTrace();
        } finally {
            try {
                if (inFile != null) inFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outFile != null) outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done.");
    }
}
