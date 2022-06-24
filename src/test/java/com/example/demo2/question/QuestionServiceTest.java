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
    TopicRepository topicRepository;
    @Mock
    QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionTemplateRepository, finalQuestionRepository, topicRepository, questionMapper);
    }

    @Test
    void listQuestionTemplates() {
        List<QuestionCreateResDto> allQuestions = questionService.getQuestionTemplatesList();
        assertThat(allQuestions).isNotNull();
    }

    @Test
    void createQuestionTemplate_savesQuestionTemplate() {
        UUID uuid = UUID.randomUUID();
        List<TopicEntity> topicEntities = new ArrayList<>();
        topicEntities.add(TopicEntity.builder().topicEnum(TopicEnum.ACCELERATION).build());

        QuestionCreateDto questionCreateDto = new QuestionCreateDto (
                UnitEnum.INTRO_TO_KINEMATICS,
                topicEntities,
                "give a ball is dropped from a height of &H&m, how long will it take to hit the ground?",
                "sec"
        );
        QuestionTemplateEntity questionTemplateEntity = new QuestionTemplateEntity(
                uuid,
                UnitEnum.INTRO_TO_KINEMATICS,
                topicEntities,
                "give a ball is dropped from a height of &H&m, how long will it take to hit the ground?",
                "sec"
        );
        QuestionCreateResDto questionCreateResDto = new QuestionCreateResDto(
                uuid,
                UnitEnum.INTRO_TO_KINEMATICS,
                topicEntities,
                "give a ball is dropped from a height of &H&m, how long will it take to hit the ground?",
                "sec"
        );

        doReturn(questionTemplateEntity).when(questionMapper).dtoToEntity(questionCreateDto);
        doReturn(questionTemplateEntity).when(questionTemplateRepository).save(questionTemplateEntity);
        doReturn(questionCreateResDto).when(questionMapper).questionToDto(questionTemplateEntity);

        QuestionCreateResDto resultQuestion = questionService.createQuestionTemplate(questionCreateDto);

        verify(questionTemplateRepository).save(any());
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

    @Test
    void getAllBaseQuestionsWithTopic() {
        List<QuestionCreateResDto> res = questionService.allQuestionTemplatesByTopic(UUID.randomUUID());

        verify(topicRepository).findById(any(UUID.class));
        verify(questionTemplateRepository).findAllByTopicEntityListContaining(any());
    }

}