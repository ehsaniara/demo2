package com.example.demo2.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    QuestionTemplateRepository questionTemplateRepository;
    @Mock
    QuestionVariableRepo questionVariableRepo;
    @Mock
    QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionTemplateRepository, questionVariableRepo, questionMapper);
    }

    @Test
    void listQuestionTemplates() {
        List<QuestionCreateResDto> allQuestions = questionService.getQuestionTemplatesList();
        assertThat(allQuestions).isNotNull();
    }

    @Test
    void createQuestionTemplate() {
        UUID uuid = UUID.randomUUID();
        List<VariableDto> variables = new ArrayList<>();
        QuestionCreateDto questionCreateDto = new QuestionCreateDto (
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec",
                variables

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