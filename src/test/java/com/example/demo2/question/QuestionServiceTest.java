package com.example.demo2.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    QuestionTemplateRepository questionTemplateRepository;
    @Mock
    FinalQuestionRepository questionVariantRepository;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionTemplateRepository, questionVariantRepository);
    }

    @Test
    void listQuestions() {
        List<QuestionTemplateEntity> allQuestions = questionService.getQuestionTemplatesList();

        assertThat(allQuestions).isNotNull();
    }

    //TODO
    @Test
    @Disabled("TODO")
    void postQuestion() {
    }

    //TODO
    @Test
    @Disabled("TODO")
    void deleteAllQuestions() {
    }

    //TODO
    @Test
    @Disabled("TODO")
    void deleteAllQuestionTemplates() {
    }
}