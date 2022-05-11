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
        List<QuestionDto> allQuestions = questionService.getQuestionTemplatesList();
        assertThat(allQuestions).isNotNull();
    }

    @Test
    void postQuestionTemplate() {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto (
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec",
                null
        );
        QuestionTemplateEntity questionTemplate = new QuestionTemplateEntity(
                UUID.randomUUID(),
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec"
        );
        QuestionDto questionDto = new QuestionDto(
                UUID.randomUUID(),
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec"
        );

        doReturn(questionTemplate).when(questionTemplateRepository).save(any());
        doReturn(questionDto).when(questionMapper).questionDto(any());

        QuestionDto resultQuestion = questionService.postQuestionTemplate(questionCreateDto);

        assertThat(resultQuestion).isNotNull();
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