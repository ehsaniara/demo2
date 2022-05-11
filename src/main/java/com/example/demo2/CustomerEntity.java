package com.example.demo2;


import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String customerName;
}
