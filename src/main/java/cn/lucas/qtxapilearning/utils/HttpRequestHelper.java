package cn.lucas.qtxapilearning.utils;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;

import static cn.lucas.qtxapilearning.Main.client;
import static cn.lucas.qtxapilearning.Main.gson;

public class HttpRequestHelper {
    public static Map<String, Object> sendPostRequest(String url, Map<String, Object> payload) throws IOException {
        String jsonPayload = gson.toJson(payload);

        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return sendRequest(request);
    }

    public static Map<String, Object> sendGetRequest(String url,String sessionId) throws IOException {
        Headers headers = new Headers.Builder()
                .add("Cookie", "hfs-session-id=" + sessionId)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .get()
                .build();

        return sendRequest(request);
    }

    public static Map<String, Object> sendPutRequest(String url, Map<String, Object> payload) throws IOException {
        String jsonPayload = gson.toJson(payload);
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return sendRequest(request);
    }

    private static Map<String, Object> sendRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return gson.fromJson(responseBody, Map.class);
            } else {
                throw new IOException("Request failed with status code: " + response.code());
            }
        }
    }
}
