package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("questionBank")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questionTemplates")
    public List<QuestionTemplateEntity> getQuestionTemplates() {
        return questionService.getQuestionTemplatesList();
    }

    @PostMapping("/questionTemplates")
    @ResponseBody
    public QuestionTemplateEntity postQuestionTemplate(@RequestBody QuestionTemplateEntity questionTemplate) {
        return questionService.postQuestionTemplate(questionTemplate);
    }

    @DeleteMapping("/questionTemplates")
    public void deleteAllQuestionTemplates() { questionService.deleteAllQuestionTemplates();}

    //TODO
    @DeleteMapping("/questionTemplates/{id}")
    public void deleteSingleQuestionTemplateAndCorrespondingQuestions(
            @PathVariable(name = "id") UUID questionTemplateId
    ){}

    //TODO
    @GetMapping("/questions")
    public List<FinalQuestionEntity> getFinalQuestions() {
        return null;
    }



    //TODO
    @DeleteMapping("/questions")
    public void deleteAllQuestions() {}


}
