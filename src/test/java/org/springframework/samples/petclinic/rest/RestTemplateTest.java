package org.springframework.samples.petclinic.rest;

import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

    public RestTemplate getRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        KeyStore keyStore;
        HttpComponentsClientHttpRequestFactory requestFactory = null;

        try {
            keyStore = KeyStore.getInstance("jks");
            ClassPathResource classPathResource = new ClassPathResource("ssl-client.jks");
            InputStream inputStream = classPathResource.getInputStream();
            keyStore.load(inputStream, "changeit".toCharArray());

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, "changeit".toCharArray()).build(),
                NoopHostnameVerifier.INSTANCE);

            HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
                .setMaxConnTotal(Integer.valueOf(5))
                .setMaxConnPerRoute(Integer.valueOf(5))
                .build();

            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setReadTimeout(Integer.valueOf(10000));
            requestFactory.setConnectTimeout(Integer.valueOf(10000));

            restTemplate.setRequestFactory(requestFactory);
        } catch (Exception exception) {
            System.out.println("Exception Occured while creating restTemplate "+exception);
            exception.printStackTrace();
        }
        return restTemplate;
    }

    @Test
    public void testRestTemplate1(){
        RestTemplate restTemplate = getRestTemplate();
        ResponseEntity result = restTemplate.getForEntity("https://spring-pet-ssl-yanni-test.192.168.64.2.nip.io/", String.class);
        System.out.println(result.getBody().toString());
    }


}
