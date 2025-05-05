package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.response.MessageResponseDTO;
import com.openclassrooms.mddapi.dto.response.TopicResponseDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ITopicService;
import com.openclassrooms.mddapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<TopicResponseDTO>> getAllTopics() {

            return ResponseEntity.ok(topicService.getAllTopics());

    }

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<MessageResponseDTO> subscribe(@PathVariable Long topicId) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            topicService.subscribeToTopic(topicId, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDTO("Abonnement effectué."));


    }

    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<MessageResponseDTO> unsubscribe(@PathVariable Long topicId) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            topicService.unsubscribeFromTopic(topicId, email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponseDTO("Désabonnment Fait"));

    }
}
