package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionTemplateRepository questionTemplateRepository;
    private final QuestionVariableRepo questionVariableRepo;
    private final QuestionMapper questionMapper;

    public List<QuestionCreateResDto> getQuestionTemplatesList() {
        List<QuestionTemplateEntity> questionTemplateEntities = questionTemplateRepository.findAll();
        List<QuestionCreateResDto> questionTemplateDtos = new ArrayList<>();
        questionTemplateEntities.forEach(questTempEntity -> questionTemplateDtos.add(questionMapper.questionToDto(questTempEntity)));
        return questionTemplateDtos;
    }

    public QuestionCreateResDto createQuestionTemplate(QuestionCreateDto questionCreateDto) {
        QuestionTemplateEntity questionTemplateEntity = questionTemplateRepository.save(questionMapper.dtoToEntity(questionCreateDto));
        createQuestionVariables(questionCreateDto.getVariables(), questionTemplateEntity);
        return questionMapper.questionToDto(questionTemplateEntity);
    }

    public void createQuestionVariables(List<VariableDto> variables, QuestionTemplateEntity questionTemplate) {
        for (VariableDto variable : variables) {
            if (variable.getMin() != null && variable.getMax() != null && variable.getMin() < variable.getMax() ) {
                if (variable.getInterval() == null) { variable.setInterval(1d); }
                for (Double value = variable.getMin(); value < variable.getMax(); value = value + variable.getInterval()) {
                    variable.getValues().add(value);
                }
            }
            for (Double value : variable.getValues()) {
                QuestionVariableEntity questionVariableEntity = new QuestionVariableEntity(
                        null,
                        questionTemplate,
                        variable.getName(),
                        value
                );
                questionVariableRepo.save(questionVariableEntity);
            }
        }
    }

    public void deleteAllQuestionTemplates() {
        questionTemplateRepository.deleteAll();
    }

}
