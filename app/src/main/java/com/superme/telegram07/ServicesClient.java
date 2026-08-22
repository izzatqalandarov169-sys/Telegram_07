package com.superme.telegram07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Single gateway for Superme-owned Stars, Premium, Business and Gifts services. */
public final class ServicesClient {
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private ServicesClient() {}

    public interface Callback { void onResult(boolean ok, String body); }

    public static void get(String path, Callback callback) {
        EXECUTOR.execute(() -> {
            HttpURLConnection connection = null;
            try {
                String base = BuildConfig.SERVICES_BASE_URL;
                if (base.contains("YOUR-SERVICES-SERVER")) {
                    callback.onResult(false, "Services server URL is not configured.");
                    return;
                }
                URL url = new URL(base + (path.startsWith("/") ? path.substring(1) : path));
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setRequestMethod("GET");
                int code = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        code >= 200 && code < 400 ? connection.getInputStream() : connection.getErrorStream()));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) out.append(line);
                reader.close();
                callback.onResult(code >= 200 && code < 400, out.toString());
            } catch (Exception e) {
                callback.onResult(false, e.getMessage() == null ? "Network error" : e.getMessage());
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    public static void stars(Callback cb) { get("api/stars", cb); }
    public static void premium(Callback cb) { get("api/premium", cb); }
    public static void business(Callback cb) { get("api/business", cb); }
    public static void gifts(Callback cb) { get("api/gifts", cb); }
}
