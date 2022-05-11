package com.example.demo2.question;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class VariableDto {
    private String name;
    private Double min;
    private Double max;
    private Double interval;
    private List<Double> values;
}
