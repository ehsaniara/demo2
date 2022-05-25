package com.example.demo2.question;

import lombok.*;

import javax.persistence.*;
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
    @Column(nullable = false)
    private String topic;
    @Column(nullable = false)
    private int topicOrdinal;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UnitEnum unitEnum;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private int unitOrdinal;
}


