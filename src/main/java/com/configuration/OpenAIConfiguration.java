package com.configuration;


import com.entity.LLMProperties;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfiguration {

    private final LLMProperties llmProperties;


    @Bean
    public OpenAIClient openAIClient() {
        return OpenAIOkHttpClient
                .builder()
                .apiKey(llmProperties.apiToken())
                .baseUrl(llmProperties.baseUrl())
                .build();
    }
}
