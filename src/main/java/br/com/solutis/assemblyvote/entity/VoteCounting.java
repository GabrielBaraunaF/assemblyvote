package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VoteCounting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id", unique = true)
    private Session session;

    @Column(name = "yes_votes")
    private int yesVotes;

    @Column(name = "percent_yes_votes")
    private Float percentYesVotes;

    @Column(name = "no_votes")
    private int noVotes;

    @Column(name = "percent_no_votes")
    private Float percentNoVotes;

    private int total;

    private String winner;

    private String status;
}
