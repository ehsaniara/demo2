package com.example.demo2.question;

import lombok.*;

import java.util.List;
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
    private UnitEnum unitEnum;
    private List<TopicEntity> topicEntityList;
    private String baseQuestion;
    private String solutionEquation;
    private String solutionUnit;
}
