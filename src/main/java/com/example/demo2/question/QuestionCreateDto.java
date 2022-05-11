package com.example.demo2.question;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDto {
    @NotEmpty(message = "Topic is mandatory")
    private String topic;
    @NotEmpty(message = "Base Question is mandatory")
    private String baseQuestion;
    @NotEmpty(message = "Equation is mandatory")
    private String solutionEquation;
    @NotEmpty(message = "Solution unit is mandatory")
    private String solutionUnit;
    @NotEmpty(message = "Variable List is mandatory")
    private List<VariableDto> variables;

}
