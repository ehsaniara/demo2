package com.example.demo2.question;

import lombok.*;

import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TopicResDto {
    private UUID topicUuid;
    private TopicEnum topicEnum;
    private String topic;
    private int topicOrdinal;
    private UnitEnum unitEnum;
    private String unit;
    private int unitOrdinal;
}
