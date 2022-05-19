package com.example.demo2.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    QuestionTemplateRepository questionTemplateRepository;
    @Mock
    FinalQuestionRepository finalQuestionRepository;
    @Mock
    QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionTemplateRepository, finalQuestionRepository, questionMapper);
    }

    @Test
    void listQuestionTemplates() {
        List<QuestionCreateResDto> allQuestions = questionService.getQuestionTemplatesList();
        assertThat(allQuestions).isNotNull();
    }

    @Test
    void createQuestionTemplate_savesQuestionTemplateAndFinalQuestions() {
        UUID uuid = UUID.randomUUID();
        List<VariableDto> variables = new ArrayList<>();
        variables.add(new VariableDto("H", 0d, 10d, 2d, new ArrayList<>()));
        QuestionCreateDto questionCreateDto = new QuestionCreateDto (
                "free fall",
                "give a ball is dropped from a height of &H&m, how long will it take to hit the ground?",
                "(&H& / 4.9) ** 0.5",
                "sec",
                variables

        );
        QuestionTemplateEntity questionTemplateEntity = new QuestionTemplateEntity(
                uuid,
                "free fall",
                "give a ball is dropped from a height of &H&m, how long will it take to hit the ground?",
                "(&H& / 4.9) ** 0.5",
                "sec"
        );
        QuestionCreateResDto questionCreateResDto = new QuestionCreateResDto(
                uuid,
                "free fall",
                "give a ball is dropped from a height of &H&m, how long will it take to hit the ground?",
                "(&H& / 4.9) ** 0.5",
                "sec"
        );

        doReturn(questionTemplateEntity).when(questionMapper).dtoToEntity(questionCreateDto);
        doReturn(questionTemplateEntity).when(questionTemplateRepository).save(questionTemplateEntity);
        doReturn(questionCreateResDto).when(questionMapper).questionToDto(questionTemplateEntity);

        QuestionCreateResDto resultQuestion = questionService.createQuestionTemplate(questionCreateDto);

        verify(questionTemplateRepository).save(any());
        verify(finalQuestionRepository).saveAll(anyList());
        assertThat(resultQuestion).isNotNull();
    }

    @Test
    void verifyVariableDto_throwsBadRequest() {
        List<VariableDto> variables_hasMin_noMax = new ArrayList<>();
        variables_hasMin_noMax.add(VariableDto.builder().min(0d).build());

        assertThrows(ResponseStatusException.class , () -> {
            questionService.verifyVariableDtoInputs(variables_hasMin_noMax);
        });

        List<VariableDto> variables_noMin_hasMax = new ArrayList<>();
        variables_noMin_hasMax.add(VariableDto.builder().max(0d).build());

        assertThrows(ResponseStatusException.class , () -> {
            questionService.verifyVariableDtoInputs(variables_noMin_hasMax);
        });

        List<VariableDto> variables_maxLessThanMin = new ArrayList<>();
        variables_maxLessThanMin.add(VariableDto.builder().min(1d).max(0d).build());

        assertThrows(ResponseStatusException.class , () -> {
            questionService.verifyVariableDtoInputs(variables_maxLessThanMin);
        });

        List<VariableDto> variables_zeroInterval = new ArrayList<>();
        variables_zeroInterval.add(VariableDto.builder().min(0d).max(10d).interval(0d).build());
        List<VariableDto> variables_nonPositiveInterval = new ArrayList<>();
        variables_nonPositiveInterval.add(VariableDto.builder().min(0d).max(10d).interval(-1d).build());

        assertThrows(ResponseStatusException.class , () -> {
            questionService.verifyVariableDtoInputs(variables_zeroInterval);
        });
        assertThrows(ResponseStatusException.class , () -> {
            questionService.verifyVariableDtoInputs(variables_nonPositiveInterval);
        });
    }

    @Test
    void createQuestionTemplate_buildsFinalQuestions() {
        String baseQuestion = "foo &F& bar &B& and foo again &F&";
        String baseEquation = "2 * &F& + &B&";
        List<VariableDto> variables = new ArrayList<>();
        variables.add(VariableDto.builder().name("F").min(0d).max(9d).values(new ArrayList<>()).build()); // 10 values
        variables.add(VariableDto.builder().name("B").values(new ArrayList<>(Arrays.asList(10d, 20d))).build()); // 2 values
        QuestionTemplateEntity questionTemplate = QuestionTemplateEntity.builder().baseQuestion(baseQuestion).solutionEquation(baseEquation).build();
        List<FinalQuestionEntity> actualResult = questionService.generateFinalEntities(questionTemplate, variables);

        assertThat(actualResult).hasSize(20);
        assertThat(actualResult.get(0).getFinalQuestion()).doesNotContain("&F&", "&B&");
        assertThat(actualResult.get(0).getFinalEquation()).doesNotContain("&F&", "&B&");
    }

    @Test
    void calculateEquationResult() {
        String equation = "2 * 3 + 4";
        Double actualResult = questionService.calculateResult(equation);
        Double expected = 10d;

        assertThat(actualResult).isEqualTo(expected);
    }

    @Test
    void deleteAllQuestionTemplates() {
        questionService.deleteAllFinalQuestionsAndQuestionTemplates();
        verify(questionTemplateRepository).deleteAll();
    }

    @Test
    void getAllFinalQuestions() {
        List<FinalQuestionResDto> finalQuestionResDtos = questionService.getFinalQuestionsList();

        assertThat(finalQuestionResDtos).isNotNull();
        verify(finalQuestionRepository).findAll();
    }

    //TODO
    @Test
    @Disabled("TODO")
    void deleteAllQuestions() {
    }

}