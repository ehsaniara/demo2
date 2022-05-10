package com.example.demo2.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuestionService questionService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllQuestionTemplatesList() throws Exception {
        List<QuestionTemplateEntity> questions = new ArrayList<>();
        QuestionTemplateEntity questionA = new QuestionTemplateEntity();
        questionA.setTopic("1-D Motion");

        QuestionTemplateEntity questionB = new QuestionTemplateEntity();
        questionB.setTopic("Free Fall");

        questions.add(questionA);
        questions.add(questionB);

        when(questionService.getQuestionTemplatesList()).thenReturn(questions);

        mockMvc.perform(get("/questionBank/questionTemplates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void postQuestionTemplate_valid_returnsQuestionTemplate() throws Exception {

        QuestionTemplateEntity questionA = new QuestionTemplateEntity(
                UUID.randomUUID(),
                "Free Fall",
                "A ball is dropped from a height of ${H}m. How long will it take to hit the ground?",
                "( H / 4.9 ) ^ 0.5 )",
                "sec"
                );

        when(questionService.postQuestionTemplate(any(QuestionTemplateEntity.class))).thenReturn(questionA);

        mockMvc.perform(post("/questionBank/questionTemplates").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(questionA)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic", hasToString(questionA.getTopic())));
    }

    @Test
    void deleteAllQuestionTemplates() throws Exception {
        mockMvc.perform(delete("/questionBank/questionTemplates"))
                .andExpect(status().isOk());
        verify(questionService).deleteAllQuestionTemplates();
    }
}
