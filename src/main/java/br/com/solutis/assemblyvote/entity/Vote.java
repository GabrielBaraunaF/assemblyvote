package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID",referencedColumnName = "ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "SESSION_ID",referencedColumnName = "ID")
    private Session session;

    private String agree;

    private Date date;
}
