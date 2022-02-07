package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import controller.MResponse;
import controller.UserController;
import okhttp3.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MRequest implements Serializable {

    private final String BASE_URL = "http://localhost:5678";
    private final String BASE = "localhost";

    private String path;
    private HashMap<String, Object> arguments;
    private Object object;

    public MRequest() {
        arguments = new LinkedHashMap<>();
    }

    public MRequest(String path, HashMap<String, Object> arguments, Object object) {
        this.path = path;
        this.arguments = arguments;
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public MRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public MRequest addArg(String key, Object value) {
        this.arguments.put(key, value);
        return this;
    }

    public Object getArg(String key) {
        return arguments.get(key);
    }

    public HashMap<String, Object> getArguments() {
        return arguments;
    }

    public MRequest setArguments(HashMap<String, Object> arguments) {
        this.arguments = arguments;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public MRequest setObject(Object object) {
        this.object = object;
        return this;
    }

    public MResponse post(String path) {
        try {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), gson.toJson(this));

            Request request = new Request.Builder()
                    .url(BASE_URL + path)
                    .post(body)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();

            return gson.fromJson(response.body().string(), MResponse.class);
        } catch (IOException ioException) {
            return null;
        }
    }

    public MResponse post() {
        try {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), gson.toJson(this));

            String token = UserController.getToken();

            boolean setToken = token != null;
            Request request = null;

            if (!setToken) {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .post(body)
                        .build();
            } else {
                System.out.println(token);
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .addHeader("token", token)
                        .post(body)
                        .build();
            }

            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            System.out.println(res);
            MResponse mResponse = gson.fromJson(res, MResponse.class);
            JsonElement jsonElement = new JsonParser().parse(res);
            if (jsonElement.getAsJsonObject().get("object") != null) {
                String object = jsonElement.getAsJsonObject().get("object").toString();
                mResponse.setObject(object);
            }
            return mResponse;
        } catch (IOException ioException) {
            return null;
        }
    }

    public MResponse put() {
        try {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), gson.toJson(this));

            String token = UserController.getToken();

            boolean setToken = token != null;
            Request request = null;

            if (!setToken) {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .put(body)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .addHeader("token", token)
                        .put(body)
                        .build();
            }

            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            System.out.println(res);
            MResponse mResponse = gson.fromJson(res, MResponse.class);
            JsonElement jsonElement = new JsonParser().parse(res);
            if (jsonElement.getAsJsonObject().get("object") != null) {
                String object = jsonElement.getAsJsonObject().get("object").toString();
                mResponse.setObject(object);
            }
            return mResponse;
        } catch (IOException ioException) {
            return null;
        }
    }

    public MResponse patch() {
        try {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), gson.toJson(this));

            String token = UserController.getToken();

            boolean setToken = token != null;
            Request request = null;

            if (!setToken) {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .patch(body)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .addHeader("token", token)
                        .patch(body)
                        .build();
            }

            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            System.out.println(res);
            MResponse mResponse = gson.fromJson(res, MResponse.class);

            JsonElement jsonElement = new JsonParser().parse(res);
            if (jsonElement.getAsJsonObject().get("object") != null) {
                String object = jsonElement.getAsJsonObject().get("object").toString();
                mResponse.setObject(object);
            }
            return mResponse;
        } catch (IOException ioException) {
            return null;
        }
    }

    public MResponse delete() {
        try {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), gson.toJson(this));

            String token = UserController.getToken();

            boolean setToken = token != null;
            Request request = null;

            if (!setToken) {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .delete(body)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(BASE_URL + path)
                        .addHeader("token", token)
                        .delete(body)
                        .build();
            }

            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            System.out.println(res);
            MResponse mResponse = gson.fromJson(res, MResponse.class);
            JsonElement jsonElement = new JsonParser().parse(res);
            if (jsonElement.getAsJsonObject().get("object") != null) {
                String object = jsonElement.getAsJsonObject().get("object").toString();
                mResponse.setObject(object);
            }
            return mResponse;
        } catch (IOException ioException) {
            return null;
        }
    }

    public MResponse get() {
        try {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder url = new HttpUrl
                    .Builder()
                    .scheme("http")
                    .host(BASE)
                    .port(5678);

            for (String segment : path.split("/"))
                url.addPathSegment(segment);

            for (String arg : arguments.keySet())
                url.addQueryParameter(arg, (String) arguments.get(arg));

            String token = UserController.getToken();

            boolean setToken = token != null;
            Request request = null;

            if (!setToken) {
                request = new Request.Builder()
                        .url(url.build())
                        .get()
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url.build())
                        .addHeader("token", token)
                        .get()
                        .build();
            }

            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            System.out.println(this.path);
            System.out.println(res);
            MResponse mResponse = gson.fromJson(res, MResponse.class);
            JsonElement jsonElement = new JsonParser().parse(res);
            if (jsonElement.getAsJsonObject().get("object") != null) {
                String object = jsonElement.getAsJsonObject().get("object").toString();
                mResponse.setObject(object);
            }
            return mResponse;
        } catch (IOException ioException) {
            return null;
        }
    }
}