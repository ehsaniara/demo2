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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void listQuestionTemplates() {
        List<QuestionTemplateEntity> allQuestions = questionService.getQuestionTemplatesList();
        assertThat(allQuestions).isNotNull();
    }

    @Test
    void postQuestionTemplate() {
        QuestionTemplateEntity questionTemplate = new QuestionTemplateEntity(
                UUID.randomUUID(),
                "free fall",
                "give a ball is dropped from a height of ${H}m, how long will it take to hit the ground?",
                "(H / 4.9) ** 0.5",
                "sec"
        );

        when(questionTemplateRepository.save(questionTemplate)).thenReturn(questionTemplate);

        QuestionTemplateEntity resultQuestion = questionService.postQuestionTemplate(questionTemplate);

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