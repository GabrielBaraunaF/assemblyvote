package br.com.solutis.assemblyvote.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VoteCouting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id", unique = true)
    private Session session;

    @Column(name = "yes_votes")
    private Integer yesVotes;

    @Column(name = "percent_yes_votes")
    private Float percentYesVotes;

    @Column(name = "no_votes")
    private Integer noVotes;

    @Column(name = "percent_no_votes")
    private Float percentNoVotes;

    private Integer total;

    private String winner;

    private String status;
}
