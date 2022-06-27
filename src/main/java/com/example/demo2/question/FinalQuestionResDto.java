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
public class FinalQuestionResDto {
    private UUID finalQuestionUuid;
    private QuestionTemplateEntity questionTemplate;
    private String finalQuestion;
}
