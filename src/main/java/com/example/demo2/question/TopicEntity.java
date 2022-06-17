package com.example.demo2.question;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
public class TopicEntity {
    @Id
    @GeneratedValue
    private UUID topicUuid;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TopicEnum topicEnum;
    @ManyToMany(mappedBy = "topicEntityList")
    private List<QuestionTemplateEntity> questionTemplateEntityList;
}


