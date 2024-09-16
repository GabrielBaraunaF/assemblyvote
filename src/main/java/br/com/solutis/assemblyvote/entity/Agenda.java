package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String topic;

}

