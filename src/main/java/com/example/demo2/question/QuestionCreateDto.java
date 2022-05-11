package com.example.demo2.question;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDto {

    private String topic;
    private String baseQuestion;
    private String solutionEquation;
    private String solutionUnit;
    private List<VariableDto> variables;

}
