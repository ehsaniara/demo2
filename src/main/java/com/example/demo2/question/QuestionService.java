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

    public List<QuestionDto> getQuestionTemplatesList() {
        List<QuestionTemplateEntity> questTempEntities = questionTemplateRepository.findAll();
        List<QuestionDto> QuestTempDtos = new ArrayList<>();
        questTempEntities.forEach(questTempEntity -> QuestTempDtos.add(questionMapper.questionDto(questTempEntity)));
        return QuestTempDtos;
    }

    public QuestionDto postQuestionTemplate(QuestionCreateDto questionCreateDto) {
        QuestionTemplateEntity questionTemplate = QuestionTemplateEntity.builder()
                .topic(questionCreateDto.getTopic())
                .baseQuestion(questionCreateDto.getBaseQuestion())
                .solutionEquation(questionCreateDto.getSolutionEquation())
                .solutionUnit(questionCreateDto.getSolutionUnit())
                .build();

        return questionMapper.questionDto(questionTemplateRepository.save(questionTemplate));
    }

    public void deleteAllQuestionTemplates() {
        questionTemplateRepository.deleteAll();
    }

}
