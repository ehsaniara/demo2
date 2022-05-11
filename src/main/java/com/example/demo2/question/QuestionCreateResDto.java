package com.example.demo2.question;

import lombok.*;

import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateResDto {
    private UUID questionTemplateUuid;
    private String topic;
    private String baseQuestion;
    private String solutionEquation;
    private String solutionUnit;
}
