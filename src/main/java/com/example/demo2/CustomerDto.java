package com.example.demo2;

import lombok.*;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long customerId;

    private String customerName;
}
