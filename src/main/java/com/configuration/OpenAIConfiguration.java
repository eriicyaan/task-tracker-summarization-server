package com.configuration;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfiguration {

    @Value("${llm.api_token}")
    private String token;


    @Bean
    public OpenAIClient openAIClient() {
        return OpenAIOkHttpClient
                .builder()
                .apiKey(token)
                .baseUrl("https://api.deepseek.com")
                .build();
    }
}
