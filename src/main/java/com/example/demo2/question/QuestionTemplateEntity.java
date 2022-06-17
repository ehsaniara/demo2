package com.example.demo2.question;


import lombok.*;

import javax.persistence.*;
import java.util.List;
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
    @Column(nullable = false)
    private UnitEnum unitEnum;
    @ManyToMany
    @JoinTable(
            name="questionTemplate_topics",
            joinColumns = @JoinColumn(name = "questionTemplateUuid"),
            inverseJoinColumns = @JoinColumn(name = "topicUuid"))
    private List<TopicEntity> topicEntityList;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String baseQuestion;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String solutionEquation;
    private String solutionUnit;

}
