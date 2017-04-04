package com.hans.sses;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class TestClasses {
public static void main(String[] args) {
        
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("ZJTcIFtaCj2nTEWqmT0PUA=="); // PBE 값(XML PASSWORD설정)
        
        //String url = pbeEnc.encrypt("jdbc:oracle:thin:@127.0.0.1:1521:XE");
        //String username = pbeEnc.encrypt("tevconl");
        //String password = pbeEnc.encrypt("Tevconl!@34");
        String username = pbeEnc.encrypt("sevconl");
        String password = pbeEnc.encrypt("SEvcOnl$#21");
        
        //System.out.println(url);
        System.out.println(username);
        System.out.println(password);
    }
}
