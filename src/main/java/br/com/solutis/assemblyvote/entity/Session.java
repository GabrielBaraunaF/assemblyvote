package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "PAUTA_ID",referencedColumnName ="ID")
    private Agenda agenda;

    private LocalDateTime opening;

    private LocalDateTime time;

    private String state;
}
