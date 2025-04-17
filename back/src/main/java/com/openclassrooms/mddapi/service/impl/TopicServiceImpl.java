package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.response.TopicResponseDTO;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.ITopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements ITopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<TopicResponseDTO> getAllTopicsWithUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));

        Set<Topic> subscriptions = user.getSubscriptions();
        List<Topic> allTopics = topicRepository.findAll();

        List<TopicResponseDTO> result = new ArrayList<>();

        for (Topic topic : allTopics) {
            TopicResponseDTO dto = topicMapper.toDto(topic);
            dto.setSubscribed(subscriptions.contains(topic));
            result.add(dto);
        }

        return result;
    }

    @Override
    public void subscribeToTopic(Long topicId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchElementException("Sujet non trouvé"));

        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    @Override
    public void unsubscribeFromTopic(Long topicId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchElementException("Sujet non trouvé"));

        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }
}
