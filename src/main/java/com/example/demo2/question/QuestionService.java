package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionTemplateRepository questionTemplateRepository;
    private final FinalQuestionRepository finalQuestionRepository;

    public List<QuestionTemplateEntity> getQuestionTemplatesList() {
        return questionTemplateRepository.findAll();
    }

    public QuestionTemplateEntity postQuestionTemplate(QuestionTemplateEntity question) {
        return questionTemplateRepository.save(question);
    }

    public void deleteAllQuestionTemplates() {
        questionTemplateRepository.deleteAll();
    }

    //TODO
    public List<FinalQuestionEntity> postFinalQuestions(UUID questionTemplateId, QuestionTemplateEntity questionTemplate) {
        return null;
    }
}
