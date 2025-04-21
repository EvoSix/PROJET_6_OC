package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.TopicSubscriptionRequest;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.ITopicService;
import com.openclassrooms.mddapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;
    private final IUserService userService;


    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            User user=    userService.getUserByMail(email);


            return ResponseEntity.ok(topicService.getAllTopicsWithUserSubscriptions(user.getId()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long topicId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user=    userService.getUserByMail(email);

            topicService.subscribeToTopic(topicId, user.getId());
            return ResponseEntity.ok("Abonnement effectué.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable Long topicId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user=    userService.getUserByMail(email);

            topicService.unsubscribeFromTopic(topicId, user.getId());
            return ResponseEntity.ok("Désabonnement effectué.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
