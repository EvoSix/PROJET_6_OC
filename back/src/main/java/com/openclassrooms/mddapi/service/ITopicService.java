package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.response.TopicResponseDTO;

import java.util.List;

public interface ITopicService {
    List<TopicResponseDTO> getAllTopicsWithUserSubscriptions(Long userId);
    void subscribeToTopic(Long topicId, Long userId);
    void unsubscribeFromTopic(Long topicId, Long userId);
}
