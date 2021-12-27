package com.jlearn.es.jest.config;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dingjuru
 * @date 2021/12/23
 */
@Configuration
public class JestConfig {

    @Bean
    public JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        List<String> urlList = Arrays.asList("http://localhost:9200");
        factory.setHttpClientConfig(new HttpClientConfig.Builder(urlList)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create())
                .multiThreaded(true)
                .connTimeout(60000)
                .readTimeout(60000)
                .build());
        return factory.getObject();
    }

    public void closeJestClient(JestClient jestClient) throws Exception {
        if(jestClient != null) {
            jestClient.close();
        }
    }
}
