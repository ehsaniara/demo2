package com.example.demo2.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionTemplateRepository extends JpaRepository <QuestionTemplateEntity, UUID> {
    List<QuestionTemplateEntity> findAllByTopicEntityListContaining(Optional<TopicEntity> topicEntity);
}
