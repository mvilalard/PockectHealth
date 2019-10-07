package com.esgi.pockethealth.application;

import android.os.AsyncTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestManager {

    public enum RequestType {
        POST,
        GET
    }

    static final private String TAG = "Tag.NetworkUtilsAcceptSelfSignedSslCert";
    private static String serverURL = "https://test8000.nox-industry.com";
    private static String cookie = null;


    public static JSONObject executeHttpsRequest(RequestType type, String url,
                                                 HashMap<String, String> params) {
        RequestTask requestTask = new RequestTask(type, url, params);
        requestTask.execute();
        try {
            requestTask.get(); // wait for end of execution
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestTask.toReturn;
    }

    static class RequestTask extends AsyncTask<Void, Integer, Long> {
        public JSONObject toReturn = null;
        public HashMap<String, String> params;
        public String url;
        public RequestType type;

        public RequestTask(RequestType _type, String _url,
                           HashMap<String, String> _params) {
            params = _params;
            url = _url;
            type = _type;
        }
        protected Long doInBackground(Void... arg0) {
            OkHttpClient okHttpClient = getOkHttpClient();

            try {

//                    if(cookie == null || cookie.isEmpty()) {
//                        initCookie(okHttpClient);
//                    }

                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                Request.Builder requestBuilder = new Request.Builder()
                        .url(serverURL + url)
                        .header("Set-Cookie", cookie);

                if(params!=null) {
                    if(type.equals(RequestType.POST)) {
                        JSONObject jsonParams = new JSONObject();
                        for (String key : params.keySet()) {
                            String value = params.get(key);
                            jsonParams.put(key, value);
                        }
                        RequestBody body = RequestBody.create(jsonParams.toString(), mediaType);
                        requestBuilder.post(body);
                    } else if(type.equals(RequestType.GET)) {
                        for (String key : params.keySet()) {
                            String value = params.get(key);
                            requestBuilder.header(key, value);
                        }
                    }
                }

                Request request = requestBuilder.build();

                Call call2 = okHttpClient.newCall(request);
                Response response2 = call2.execute();

                if (response2.isSuccessful()) {
                    String responseBody = response2.body().string();
                    toReturn = new JSONObject(responseBody);
                } else {
                    toReturn =  null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                toReturn =  null;
            }
            return null;
        }

        protected void onPostExecute(Long result) {
            System.out.println(this.toReturn);
        }
    }

    private static void initCookie(OkHttpClient okHttpClient) throws IOException {
        Request request = new Request.Builder()
                .url("https://test8000.nox-industry.com/")
                .build();
        Call call = okHttpClient.newCall(request);
        Response originalResponse = call.execute();
        originalResponse.headers();

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookie = header;
            }
        }
    }

    @NotNull
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        addCertificateChecker(okHttpClientBuilder);

        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        addAutomaticCookieHandling(okHttpClientBuilder);

        return okHttpClientBuilder.build();
    }

    private static void addCertificateChecker(OkHttpClient.Builder okHttpClientBuilder) {
        final TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
                System.out.println(TAG + ": authType: " + String.valueOf(authType));
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
                System.out.println(TAG + ": authType: " + String.valueOf(authType));
            }
        }};

        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
                System.out.println(TAG + ": authType: " + String.valueOf(authType));
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
                System.out.println(TAG + ": authType: " + String.valueOf(authType));
            }
        };


        try {
            final String PROTOCOL = "SSL";
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
            KeyManager[] keyManagers = null;
            SecureRandom secureRandom = new SecureRandom();
            sslContext.init(keyManagers, trustManagers, secureRandom);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addAutomaticCookieHandling(OkHttpClient.Builder okHttpClientBuilder) {
        CookieJar cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };

        okHttpClientBuilder.cookieJar(cookieJar);
    }
}


