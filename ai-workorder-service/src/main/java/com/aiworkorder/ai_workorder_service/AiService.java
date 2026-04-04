package com.aiworkorder.ai_workorder_service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
//Ai操作
@Service
public class AiService {
    //声明
    private final ChatClient client;

    //构造
    public AiService(ChatClient.Builder builder){
        this.client=builder.build();
    }

    //普通对话
    public String chat(String text){
        return client.prompt(text).call().content();
    }

    //流动对话
    public Flux<String> stream(String text){
        return client.prompt(text).stream().content();
    }

}
