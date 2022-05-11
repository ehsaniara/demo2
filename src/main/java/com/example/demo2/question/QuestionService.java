package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionTemplateRepository questionTemplateRepository;
    private final QuestionMapper questionMapper;

    public List<QuestionCreateResDto> getQuestionTemplatesList() {
        List<QuestionTemplateEntity> questTempEntities = questionTemplateRepository.findAll();
        List<QuestionCreateResDto> QuestTempDtos = new ArrayList<>();
        questTempEntities.forEach(questTempEntity -> QuestTempDtos.add(questionMapper.questionToDto(questTempEntity)));
        return QuestTempDtos;
    }

    public QuestionCreateResDto createQuestionTemplate(QuestionCreateDto questionCreateDto) {
        return questionMapper.questionToDto(questionTemplateRepository.save(questionMapper.dtoToEntity(questionCreateDto)));
    }

    public void deleteAllQuestionTemplates() {
        questionTemplateRepository.deleteAll();
    }

}
