package com.fancode.assignment;

import com.fancode.assignment.models.Todo;
import com.fancode.assignment.models.User;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";

    private final HttpClient httpClient;

    public ApiClient() {
        this.httpClient = HttpClients.createDefault();
    }

    public List<User> getUsers() throws IOException {
        String endpoint = BASE_URL + "/users";
        HttpGet request = new HttpGet(endpoint);
        String json = httpClient.execute(request, response ->
                EntityUtils.toString(response.getEntity()));
        return Arrays.asList(new com.google.gson.Gson().fromJson(json, User[].class));
    }

    public List<Todo> getTodos(String uri) throws IOException {
        String endpoint = BASE_URL + "/" + uri;
        HttpGet request = new HttpGet(endpoint);
        String json = httpClient.execute(request, response ->
                EntityUtils.toString(response.getEntity()));
        return Arrays.asList(new com.google.gson.Gson().fromJson(json, Todo[].class));
    }
}