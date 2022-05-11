package com.example.demo2;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDto {

    @NotBlank(message = "customerName can not be null or blank")
    private String customerName;
}
