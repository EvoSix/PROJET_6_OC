package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.response.TopicResponseDTO;

import java.util.List;

public interface ITopicService {
    List<TopicResponseDTO> getAllTopicsWithUserSubscriptions(String email);
    void subscribeToTopic(Long topicId, String email);
    void unsubscribeFromTopic(Long topicId, String email);
    List<TopicResponseDTO>  getAllTopics ();
}
