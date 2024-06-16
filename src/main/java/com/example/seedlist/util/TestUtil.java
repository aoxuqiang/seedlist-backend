package com.example.seedlist.util;

import java.util.UUID;

public class TestUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));


        System.out.println(EncryptionUtil.encryptWithAES("123456","seedlist"));
    }


}
