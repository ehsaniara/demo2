package com.example.demo2.question;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionTemplateRepository questionTemplateRepository;
    private final FinalQuestionRepository finalQuestionRepository;
    private final TopicRepository topicRepository;
    private final QuestionMapper questionMapper;

    public List<TopicEntity> seedTopicRepository() {
        List<TopicEntity> topicEntities = new ArrayList<>();

        for (TopicEnum topic : TopicEnum.values()) {
            topicEntities.add(new TopicEntity(
                    null,
                    topic,
                    new ArrayList<>()));
        }

        return topicRepository.saveAll(topicEntities);
    }

    public List<QuestionCreateResDto> getQuestionTemplatesList() {
        List<QuestionTemplateEntity> questionTemplateEntities = questionTemplateRepository.findAll();
        List<QuestionCreateResDto> questionTemplateDtos = new ArrayList<>();
        questionTemplateEntities.forEach(questTempEntity -> questionTemplateDtos.add(questionMapper.questionToDto(questTempEntity)));
        return questionTemplateDtos;
    }

    public QuestionCreateResDto createQuestionTemplate(QuestionCreateDto questionCreateDto) {
        QuestionTemplateEntity questionTemplateEntity = questionTemplateRepository.save(questionMapper.dtoToEntity(questionCreateDto));
        return questionMapper.questionToDto(questionTemplateEntity);
    }

    public void verifyVariableDtoInputs(List<VariableDto> variableDtos) {
        for (VariableDto variableDto : variableDtos) {
            if (variableDto.getMin() != null && variableDto.getMax() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Min and Max must be provided"
                );
            }
            if (variableDto.getMin() == null && variableDto.getMax() != null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Min and Max must be provided"
                );
            }
            if (variableDto.getMin() > variableDto.getMax()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Min must be less than Max"
                );
            }
            if (variableDto.getInterval() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Interval must be a positive value"
                );
            }
        }
    }

    public void deleteAllFinalQuestionsAndQuestionTemplates() {
        finalQuestionRepository.deleteAll();
        questionTemplateRepository.deleteAll();
    }

    public List<FinalQuestionResDto> getFinalQuestionsList() {
        List<FinalQuestionEntity> finalQuestionEntities = finalQuestionRepository.findAll();
        List<FinalQuestionResDto> finalQuestionResDtos = new ArrayList<>();
        finalQuestionEntities.forEach(entity -> finalQuestionResDtos.add(questionMapper.finalQuestionEntityToDto(entity)));
        return finalQuestionResDtos;
    }

    public List<QuestionCreateResDto> allQuestionTemplatesByTopic(UUID topicUuid) {
        Optional<TopicEntity> topicEntity = topicRepository.findById(topicUuid);

        List<QuestionTemplateEntity> questionTemplates = questionTemplateRepository.findAllByTopicEntityListContaining(topicEntity);

        List<QuestionCreateResDto> res = new ArrayList<>();
        questionTemplates.forEach(template -> {
            res.add(questionMapper.questionToDto(template));
        });

        return res;
    }

    public List<TopicResDto> getAllTopics() {
        List<TopicEntity> topicEntities = topicRepository.findAll();
        List<TopicResDto> topicResDtoList = new ArrayList<>();

        topicEntities.forEach((topicEntity) -> topicResDtoList.add(
                        TopicResDto.builder()
                                .topicUuid(topicEntity.getTopicUuid())
                                .topicEnum(topicEntity.getTopicEnum())
                                .topic(topicEntity.getTopicEnum().label)
                                .topicOrdinal(topicEntity.getTopicEnum().ordinal())
                                .unitEnum(topicEntity.getTopicEnum().unit)
                                .unit(topicEntity.getTopicEnum().unit.label)
                                .unitOrdinal(topicEntity.getTopicEnum().unit.ordinal())
                .build()));
        return topicResDtoList;
    }
}
