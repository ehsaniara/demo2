package com.example.demo2.question;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@Entity
@Table(name = "question_variables")
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVariableEntity {

    @Id
    @GeneratedValue
    private UUID questionVariableUuid;

    @ManyToOne
    @JoinColumn(name="question_template_uuid", nullable = false)
    private QuestionTemplateEntity questionTemplate;
    @Column(nullable = false)
    private String variableName;
    @Column(nullable = false)
    private double variableValue;
}
