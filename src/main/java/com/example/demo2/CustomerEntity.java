package com.example.demo2;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Builder
@Setter
@Getter
@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity extends EntityAudit {

    @Id
    @GeneratedValue
    private Long customerId;

    private String customerName;
}
