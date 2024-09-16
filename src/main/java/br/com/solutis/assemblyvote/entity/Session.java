package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pauta_id", referencedColumnName = "id")
    private Agenda agenda;

    private LocalDateTime opening;

    private Integer time;

    private String state;

    public Boolean isTheVotingDeadlineHasExpired() {
        return LocalDateTime.now().isAfter(opening.plusMinutes(time));
    }
}
