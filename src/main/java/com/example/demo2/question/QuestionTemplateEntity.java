package com.example.demo2.question;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@Entity
@Table(name = "question_templates")
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTemplateEntity {
    @Id
    @GeneratedValue
    private UUID questionTemplateUuid;
    @JoinColumn(name="topic_uuid", nullable = false)
    private TopicEnum topicEnum;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String baseQuestion;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String solutionEquation;
    private String solutionUnit;

}
