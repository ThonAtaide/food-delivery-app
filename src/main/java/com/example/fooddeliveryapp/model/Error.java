package com.example.fooddeliveryapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ERROR_TABLE")
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    @Column(name = "ERROR_TYPE")
    private String errorType;

    @Column(name = "ORIGIN")
    private String origin;

    @CreatedDate
    private Instant createdAt;

}
