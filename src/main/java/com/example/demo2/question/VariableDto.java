package com.example.demo2.question;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class VariableDto {
    @NotEmpty
    private String name;
    private Double min;
    private Double max;
    private Double interval;
    @NotEmpty
    private List<Double> values;
}
