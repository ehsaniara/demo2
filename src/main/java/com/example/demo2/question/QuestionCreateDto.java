package com.example.demo2.question;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDto {
    @NotNull(message = "Unit is mandatory")
    private UnitEnum unitEnum;
    @NotNull( message = "Topic is mandatory")
    private List<TopicEntity> topicEntityList;
    @NotEmpty(message = "Base Question is mandatory")
    private String baseQuestion;
//    @NotEmpty(message = "Equation is mandatory")
    private String solutionEquation;
    @NotEmpty(message = "Solution unit is mandatory")
    private String solutionUnit;
//    @NotEmpty(message = "Variable List is mandatory")
    private List<VariableDto> variables;
}
