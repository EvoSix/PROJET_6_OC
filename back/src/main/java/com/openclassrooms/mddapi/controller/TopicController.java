package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.TopicSubscriptionRequest;
import com.openclassrooms.mddapi.service.ITopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;

    @GetMapping
    public ResponseEntity<?> getAllTopics(@RequestParam Long userId) {
        return ResponseEntity.ok(topicService.getAllTopicsWithUserSubscriptions(userId));
    }

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<?> subscribe(
            @PathVariable Long topicId,
            @RequestBody TopicSubscriptionRequest request
    ) {
        topicService.subscribeToTopic(topicId, request.getUserId());
        return ResponseEntity.ok("Abonnement effectué.");
    }

    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<?> unsubscribe(
            @PathVariable Long topicId,
            @RequestBody TopicSubscriptionRequest request
    ) {
        topicService.unsubscribeFromTopic(topicId, request.getUserId());
        return ResponseEntity.ok("Désabonnement effectué.");
    }
}
