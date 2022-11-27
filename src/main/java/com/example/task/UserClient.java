package com.example.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class UserClient {

    public List<User> users() {
        List<User> userList = new ArrayList<>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet getRequest = new HttpGet("https://randomuser.me/api/");
            getRequest.addHeader("accept", "application/xml");
            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Status code is not 200");
            }

            HttpEntity entity = response.getEntity();
            String output = EntityUtils.toString(entity);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            output = output.split("\"results\":")[1].split(",\"info\"")[0];
            userList = objectMapper.reader()
                    .forType(new TypeReference<List<User>>() {} )
                    .readValue(output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
}
