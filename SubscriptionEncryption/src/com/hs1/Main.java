package com.hs1;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsOne.SubscriptionChecker;
import org.apache.commons.io.IOUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class  Main {

    public static void main(String[] args) {
        SubscriptionChecker subscriptionChecker = new SubscriptionChecker();
        var json = subscriptionChecker.getSubscriptions(1);
        var file = new File("subscriptions.txt");
        encodeToFile(json, file);
        var decodedJson = decodeFromFile(file);
        printOutSubscriptions(decodedJson);
    }

    private static void encodeToFile(final String json, final File file) {
        try {
            var encodedJson = Security.encode(json);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(encodedJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String decodeFromFile(final File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            var encodedData = IOUtils.toString(fis, "UTF-8");
            return Security.decode(encodedData);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void printOutSubscriptions(final String decodedJson) {
        try {
            var subscriptions = new ObjectMapper().readValue(decodedJson, SubscriptionsList.class);
            subscriptions.getSubscriptions().forEach(sub -> {
                var name = sub.getName();
                var isSubscribed = sub.isSubscribed();
                System.out.println("Name: " + name + "Is Subscribed: " + isSubscribed);
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
