package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionTemplateRepository questionTemplateRepository;
    private final FinalQuestionRepository finalQuestionRepository;
    private final QuestionMapper questionMapper;

    public List<QuestionCreateResDto> getQuestionTemplatesList() {
        List<QuestionTemplateEntity> questionTemplateEntities = questionTemplateRepository.findAll();
        List<QuestionCreateResDto> questionTemplateDtos = new ArrayList<>();
        questionTemplateEntities.forEach(questTempEntity -> questionTemplateDtos.add(questionMapper.questionToDto(questTempEntity)));
        return questionTemplateDtos;
    }

    public QuestionCreateResDto createQuestionTemplate(QuestionCreateDto questionCreateDto) {
        // check that the variableDto is valid
        verifyVariableDtoInputs(questionCreateDto.getVariables());

        // create questionTemplate
        QuestionTemplateEntity questionTemplateEntity = questionTemplateRepository.save(questionMapper.dtoToEntity(questionCreateDto));

        // create finalQuestion with correct question, solution string, and result
        List<FinalQuestionEntity> finalQuestionEntities = generateFinalEntities(questionTemplateEntity, questionCreateDto.getVariables());

        // batch save final questions and save question template
        finalQuestionRepository.saveAll(finalQuestionEntities);
        // return question template and meta-data about the final question (what should this look like?)
        return questionMapper.questionToDto(questionTemplateEntity);
    }

    public void verifyVariableDtoInputs (List<VariableDto> variableDtos) {
        for(VariableDto variableDto : variableDtos) {
            if (variableDto.getMin() != null && variableDto.getMax() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Min and Max must be provided"
                );
            }
            if (variableDto.getMin() == null && variableDto.getMax() != null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Min and Max must be provided"
                );
            }
            if (variableDto.getMin() > variableDto.getMax()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Min must be less than Max"
                );
            }
            if (variableDto.getInterval() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Interval must be a positive value"
                );
            }
        }
    }

    public List<FinalQuestionEntity> generateFinalEntities (QuestionTemplateEntity questionTemplate, List<VariableDto> variables) {
        generateVariableValues(variables);

        int numOfVariations = 1;
        for (VariableDto variable : variables) {
            numOfVariations *= variable.getValues().size();
        }

        List<FinalQuestionEntity> finalQuestionEntities = new ArrayList<>(numOfVariations);
        finalQuestionEntities = applySearchAndReplace(
                questionTemplate,
                questionTemplate.getBaseQuestion(),
                questionTemplate.getSolutionEquation(),
                0,
                variables,
                finalQuestionEntities
        );

        // solve equation
        for(FinalQuestionEntity questionEntity: finalQuestionEntities) {
            questionEntity.setResult(calculateResult(questionEntity.getFinalEquation()));
        }

        return finalQuestionEntities;
    }

    public void generateVariableValues (List<VariableDto> variables) {
        for (VariableDto variable: variables) {
            if (variable.getMin() != null) {
                if (variable.getInterval() == null) {
                    variable.setInterval(1d);
                }
                for (Double value = variable.getMin(); value <= variable.getMax(); value = value + variable.getInterval()) {
                    variable.getValues().add(value);
                }
            }
        }
    }

    public List<FinalQuestionEntity> applySearchAndReplace (
            QuestionTemplateEntity questionTemplate,
            String currentQuestion,
            String currentEquation,
            int variablesIndex,
            List<VariableDto> variables,
            List<FinalQuestionEntity> results
    ) {
        if (variablesIndex >= variables.size()) {
            results.add(FinalQuestionEntity.builder()
                    .questionTemplate(questionTemplate)
                    .topic(questionTemplate.getTopic())
                    .finalQuestion(currentQuestion)
                    .finalEquation(currentEquation)
                    .build()
            );
            return  results;
        } else {
            int valuesLength = variables.get(variablesIndex).getValues().size();
            for (int i = 0; i < valuesLength; i++) {
                String updateQuestion = currentQuestion.replaceAll(
                        "&" + variables.get(variablesIndex).getName() + "&",
                        String.valueOf(variables.get(variablesIndex).getValues().get(i))
                );
                String updateEquation = currentEquation.replaceAll(
                        "&" + variables.get(variablesIndex).getName() + "&",
                        String.valueOf(variables.get(variablesIndex).getValues().get(i))
                );
                applySearchAndReplace(questionTemplate, updateQuestion, updateEquation, variablesIndex + 1, variables, results);
            }
        }
        return results;
    }

    public Double calculateResult(String equation) {
        try(PythonInterpreter pythonInterpreter = new PythonInterpreter()) {
            PyObject result = pythonInterpreter.eval(equation);
            System.out.println("the result is: " + result);
            return result.asDouble();
        }
    }

    public void deleteAllFinalQuestionsAndQuestionTemplates() {
        finalQuestionRepository.deleteAll();
        questionTemplateRepository.deleteAll();
    }

    public List<FinalQuestionResDto> getFinalQuestionsList() {
        List<FinalQuestionEntity> finalQuestionEntities = finalQuestionRepository.findAll();
        List<FinalQuestionResDto> finalQuestionResDtos = new ArrayList<>();
        finalQuestionEntities.forEach(entity -> finalQuestionResDtos.add(questionMapper.finalQuestionEntityToDto(entity)));
        return finalQuestionResDtos;
    }

    public List<FinalQuestionResDto> getFinalQuestionsListByTopic(String topic) {
        List<FinalQuestionEntity> finalQuestionEntities = finalQuestionRepository.findAllByTopic(topic);
        List<FinalQuestionResDto> finalQuestionResDtos = new ArrayList<>();
        finalQuestionEntities.forEach(entity -> finalQuestionResDtos.add(questionMapper.finalQuestionEntityToDto(entity)));
        return finalQuestionResDtos;
    }
}
