package com.example.demo.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class InitDataParser {
    public static String extractTelegramId(String initData) {
        System.out.println("RAW initData: " + initData);

        Map<String, String> map = new HashMap<>();
        for (String pair : initData.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                String key = kv[0];
                String value = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                map.put(key, value);
                System.out.println("Parsed: " + key + " = " + value);
            }
        }

        String userJson = map.get("user");
        if (userJson == null) {
            throw new RuntimeException("No 'user' in initData");
        }

        // Ищем "id":123456789 (с кавычками или без)
        int idIndex = userJson.indexOf("\"id\":");
        if (idIndex == -1) idIndex = userJson.indexOf("id:");
        if (idIndex == -1) throw new RuntimeException("No 'id' in user");

        int valueStart = userJson.indexOf(":", idIndex) + 1;
        while (valueStart < userJson.length() && (userJson.charAt(valueStart) == ' ' || userJson.charAt(valueStart) == '"')) {
            valueStart++;
        }

        int valueEnd = userJson.indexOf(",", valueStart);
        if (valueEnd == -1) valueEnd = userJson.indexOf("}", valueStart);

        String id = userJson.substring(valueStart, valueEnd).trim().replace("\"", "");
        System.out.println("Extracted telegramId: " + id);
        return id;
    }
}