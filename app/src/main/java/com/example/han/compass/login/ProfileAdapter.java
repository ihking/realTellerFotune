package com.example.han.compass.login;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by home on 2016-09-16.
 */
public class ProfileAdapter {

    private static final String SERVER_URL = "http://211.249.50.198:4001"; //2부터 url뒤에 /를 입력해야 합니다.

    //OkHttpClient를 생성합니다.
    private static ProfileInterface Interface;

    public synchronized static ProfileInterface getInstance() {
        if (Interface == null) {

            //Retrofit 설정
            Interface = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())             //Json Parser 추가
                    .build().create(ProfileInterface.class);                     //인터페이스 연결

        }
        return Interface;
    }

    private static OkHttpClient getRequestHeader() {
        OkHttpClient httpClient = getUnsafeOkHttpClient();      // 인증서 무시
        return httpClient;
    }

    /**
     * UnCertificated 허용
     */
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder
                    .connectTimeout(15, TimeUnit.SECONDS)       // 연결 타임아웃 시간 설정
                    .writeTimeout(15, TimeUnit.SECONDS)         // 쓰기 타임아웃 시간 설정
                    .readTimeout(15, TimeUnit.SECONDS)          // 읽기 타임아웃 시간 설정
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
