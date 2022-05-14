package com.example.demo2;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

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
    private UUID customerUuid;

    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String city;
}
