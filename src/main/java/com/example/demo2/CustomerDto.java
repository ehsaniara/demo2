package com.example.demo2;

import lombok.*;

import java.util.UUID;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private UUID customerUuid;

    private String customerName;
}
