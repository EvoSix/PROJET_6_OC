package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.response.MessageResponseDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ITopicService;
import com.openclassrooms.mddapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getTopicSubscribe() {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();




            return ResponseEntity.ok(topicService.getAllTopicsWithUserSubscriptions(email));

    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllTopics() {

            return ResponseEntity.ok(topicService.getAllTopics());

    }

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long topicId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            topicService.subscribeToTopic(topicId, email);
            return ResponseEntity.ok(new MessageResponseDTO("Abonnement effectué."));
            ///ResponseEntity.status(HttpStatus.)
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable Long topicId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            topicService.unsubscribeFromTopic(topicId, email);
            return ResponseEntity.ok(new MessageResponseDTO("Désabonnment Fait"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
