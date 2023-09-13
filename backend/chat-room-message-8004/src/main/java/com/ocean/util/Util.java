package com.ocean.util;

public class Util {
    public static String spliceSenderAndReceiver(String sender, String receiver) {
        String key;
        if (Integer.valueOf(sender) <= Integer.valueOf(receiver)) {
            key = sender + "-" + receiver;
        } else {
            key = receiver + "-" + sender;
        }
        return key;
    }
}
