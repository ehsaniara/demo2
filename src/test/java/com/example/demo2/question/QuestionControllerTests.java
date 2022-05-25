package com.example.demo2.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.BadRequestException;
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
        List<QuestionCreateResDto> questions = new ArrayList<>();
        QuestionCreateResDto questionCreateResDtoA = new QuestionCreateResDto();
        QuestionCreateResDto questionCreateResDtoB = new QuestionCreateResDto();
        questions.add(questionCreateResDtoA);
        questions.add(questionCreateResDtoB);

        when(questionService.getQuestionTemplatesList()).thenReturn(questions);

        mockMvc.perform(get("/questionBank/questionTemplates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void postQuestionTemplate_valid_returnsQuestionTemplate() throws Exception {

        List<VariableDto> variables = new ArrayList<>();
        variables.add(new VariableDto("H", 3d, 15d, 2d, null));

        QuestionCreateDto questionCreateDto = new QuestionCreateDto(
                TopicEnum.ACCELERATION,
                "A ball is dropped from a height of ${H}m. How long will it take to hit the ground?",
                "( H / 4.9 ) ^ 0.5 )",
                "sec",
                variables
        );
        QuestionCreateResDto questionCreateResDto = new QuestionCreateResDto(
                UUID.randomUUID(),
                TopicEnum.ACCELERATION,
                "A ball is dropped from a height of ${H}m. How long will it take to hit the ground?",
                "( H / 4.9 ) ^ 0.5 )",
                "sec"
                );

        when(questionService.createQuestionTemplate(any(QuestionCreateDto.class))).thenReturn(questionCreateResDto);

        mockMvc.perform(post("/questionBank/questionTemplates").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(questionCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void postQuestionTemplate_invalidParameters_returnBadRequest() throws Exception {
        List<VariableDto> variables = new ArrayList<>();
        variables.add(new VariableDto("H", 3d, 15d, 2d, null));
        QuestionCreateDto questionCreateDto = new QuestionCreateDto(
                TopicEnum.ACCELERATION,
                null,
                "( H / 4.9 ) ^ 0.5 )",
                "sec",
                variables
        );
        when(questionService.createQuestionTemplate(questionCreateDto)).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/questionBank/questionTemplates").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(questionCreateDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAllQuestionTemplates() throws Exception {
        mockMvc.perform(delete("/questionBank/questionTemplates"))
                .andExpect(status().isOk());
        verify(questionService).deleteAllFinalQuestionsAndQuestionTemplates();
    }

    @Test
    void getAllFinalQuestionsList() throws Exception {
        List<FinalQuestionResDto> finalQuestions = new ArrayList<>();

        when(questionService.getFinalQuestionsList()).thenReturn(finalQuestions);

        mockMvc.perform(get("/questionBank/finalQuestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
