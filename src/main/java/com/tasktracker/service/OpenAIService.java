package com.tasktracker.service;


import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final OpenAIClient openAIClient;



    public String generateReport(String prompt, String model) {

        ChatCompletionUserMessageParam userMessage = ChatCompletionUserMessageParam.builder()
                .content(ChatCompletionUserMessageParam.Content.ofText(prompt))
                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addMessage(userMessage)
                .model(model)
                .build();

        ChatCompletion chatCompletion = openAIClient.chat().completions().create(params);

        return chatCompletion.choices()
                .get(0)
                .message()
                .content()
                .orElse("");
    }



}
