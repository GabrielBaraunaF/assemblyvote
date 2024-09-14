package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id",referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "session_id",referencedColumnName = "id")
    private Session session;

    private String agree;

    private LocalDateTime  date;

    @Column(name = "is_counted")
    private Boolean isCounted;
}
