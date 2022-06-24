package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/questionBank")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questionTemplates")
    public List<QuestionCreateResDto> getQuestionTemplates() {
        return questionService.getQuestionTemplatesList();
    }

    @PostMapping("/questionTemplates")
    @ResponseBody
    public QuestionCreateResDto postQuestionTemplate(@Valid @RequestBody QuestionCreateDto questionCreateDto) {
        return questionService.createQuestionTemplate(questionCreateDto);
    }

    @DeleteMapping("/questionTemplates")
    public void deleteAllQuestionTemplates() { questionService.deleteAllFinalQuestionsAndQuestionTemplates();}

    @GetMapping("/finalQuestions")
    public List<FinalQuestionResDto> getFinalQuestions() {
        return questionService.getFinalQuestionsList();
    }

    @GetMapping("/topics/{topicUuid}")
    @ResponseBody
    public List<QuestionCreateResDto> getQuestionsByTopic(@PathVariable UUID topicUuid) {
        return questionService.allQuestionTemplatesByTopic(topicUuid);
    }

    @GetMapping("/topics")
    public List<TopicResDto> getTopics() {
        return questionService.getAllTopics();
    }

    @PostMapping("/topics")
    public List<TopicEntity> seedTopics() {
        return questionService.seedTopicRepository();
    }


}
