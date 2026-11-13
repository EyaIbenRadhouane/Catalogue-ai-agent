package com.example.gestion_catalogue.controller;

import com.example.gestion_catalogue.tools.ProductTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ChatMemory chatMemory, ProductTools productTools) {
        this.chatClient = builder
                .defaultAdvisors(
                        a -> a.advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                                .param(ChatMemory.CONVERSATION_ID, "default")
                )
                .defaultSystem("""
                        Tu es un assistant e-commerce d'elite pour la boutique Polytechnique Sousse.
                        Regles strictes que tu dois imperativement respecter :
                        - Reponds UNIQUEMENT aux questions relatives aux produits du catalogue.
                        - Utilise SYSTEMATIQUEMENT tes outils pour verifier les prix et disponibilites.
                        - Si la question concerne un autre sujet, reponds exactement 'Je ne sais pas'.
                        - Ne divulgue jamais de fausses informations inventees.
                        """)
                .defaultTools(productTools)
                .build();
    }

    @GetMapping("/agent")
    public String chat(@RequestParam String message) {
        try {
            return chatClient.prompt().user(message).call().content();
        } catch (NonTransientAiException e) {
            return "Le service IA est momentanément saturé, réessayez dans quelques secondes.";
        }
    }
}