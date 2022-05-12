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
        for (VariableDto var : variables) {
            if (var.getMin() != null) {
                for (Double value = var.getMin(); value < var.getMax(); value = value + var.getInterval()) {
                    QuestionVariableEntity questionVariableEntity = new QuestionVariableEntity(
                            null,
                            questionTemplate,
                            var.getName(),
                            value
                    );
                     questionVariableRepo.save(questionVariableEntity);
                }
            }
            if (var.getValues() != null){
                // iterate through var.getValues
                    // create question variable entity
                // questionVariableRepo.save()
            }

        }
    }

    public void deleteAllQuestionTemplates() {
        questionTemplateRepository.deleteAll();
    }

}
