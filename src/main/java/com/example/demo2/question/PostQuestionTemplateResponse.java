package com.example.demo2.question;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostQuestionTemplateResponse {
    private QuestionTemplateEntity questionTemplate;
    private List<FinalQuestionEntity> finalQuestions;
}
