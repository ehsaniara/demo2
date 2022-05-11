package com.example.demo2.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    QuestionTemplateRepository questionTemplateRepository;
    @Mock
    QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionTemplateRepository, questionMapper);
    }

    @Test
    void listQuestionTemplates() {
        List<QuestionCreateResDto> allQuestions = questionService.getQuestionTemplatesList();
        assertThat(allQuestions).isNotNull();
    }

    @Test
    void createQuestionTemplate() {
        UUID uuid = UUID.randomUUID();
        QuestionCreateDto questionCreateDto = new QuestionCreateDto (
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec",
                null
        );
        QuestionTemplateEntity questionTemplateEntity = new QuestionTemplateEntity(
                uuid,
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec"
        );
        QuestionCreateResDto questionCreateResDto = new QuestionCreateResDto(
                uuid,
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec"
        );

        doReturn(questionTemplateEntity).when(questionMapper).dtoToEntity(questionCreateDto);
        doReturn(questionTemplateEntity).when(questionTemplateRepository).save(questionTemplateEntity);
        doReturn(questionCreateResDto).when(questionMapper).questionToDto(questionTemplateEntity);

        QuestionCreateResDto resultQuestion = questionService.createQuestionTemplate(questionCreateDto);

        assertThat(resultQuestion).isNotNull();
    }

    @Test
    void createQuestionTemplate_incompleteData_returnBadRequest403() {
        UUID uuid = UUID.randomUUID();
        QuestionCreateDto questionCreateDto = new QuestionCreateDto (
                "free fall",
                null,
                "(H / 4.9) ** 0.5",
                "sec",
                null
        );
    }

    @Test
    void deleteAllQuestionTemplates() {
        questionService.deleteAllQuestionTemplates();
        verify(questionTemplateRepository).deleteAll();
    }

    //TODO
    @Test
    @Disabled("TODO")
    void deleteAllQuestions() {
    }

}