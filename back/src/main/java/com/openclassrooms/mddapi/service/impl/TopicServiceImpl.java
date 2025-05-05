package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.response.TopicResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements ITopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<TopicResponseDTO> getAllTopicsWithUserSubscriptions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));

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

    public List<TopicResponseDTO> getAllTopics() {


        return topicRepository.findAll().stream().map(topicMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void subscribeToTopic(Long topicId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Sujet non trouvé"));

        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    @Override
    public void unsubscribeFromTopic(Long topicId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Sujet non trouvé"));

        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }
}
