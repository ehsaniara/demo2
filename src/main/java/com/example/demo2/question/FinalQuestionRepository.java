package com.example.demo2.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface FinalQuestionRepository extends JpaRepository<FinalQuestionEntity, UUID> {
//    List<FinalQuestionEntity> findAllByTopicEnum(String topic);
}
