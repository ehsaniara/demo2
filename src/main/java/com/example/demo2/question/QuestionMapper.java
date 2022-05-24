package com.example.demo2.question;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuestionMapper {
    QuestionCreateResDto questionToDto (QuestionTemplateEntity questionTemplateEntity);
    QuestionTemplateEntity dtoToEntity (QuestionCreateDto questionCreateDto);
    FinalQuestionResDto finalQuestionEntityToDto(FinalQuestionEntity finalQuestionEntity);
}
