package com.example.demo2.question;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@Entity
@Table(name = "final_questions")
@AllArgsConstructor
@NoArgsConstructor
public class FinalQuestionEntity {
    @Id
    @GeneratedValue
    private UUID finalQuestionUuid;

    @ManyToOne
    @JoinColumn(name="question_template_uuid", nullable = false)
    private QuestionTemplateEntity questionTemplate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String finalQuestion;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String finalEquation;
    @Column(nullable = false)
    private double result;
}
