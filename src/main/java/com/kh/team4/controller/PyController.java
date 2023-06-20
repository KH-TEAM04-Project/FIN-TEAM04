package com.kh.team4.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tospring")
@ToString
public class PyController {

    @GetMapping
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();

        String url = "http://127.0.0.1:5000/tospring";
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            response.put("success", true);
            response.put("message", sb.toString());
            System.out.println(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "URL Error");
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "IO Error");
        }
        return response;
    }
}
