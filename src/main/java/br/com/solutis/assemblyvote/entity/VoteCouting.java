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
    @JoinColumn(name = "SESSION_ID",referencedColumnName = "ID",unique = true)
    private Session session;

    @Column(name = "YES_VOTES")
    private Integer yesVotes;

    @Column(name = "PERCENT_YES_VOTES")
    private Integer percentYesVotes;

    @Column(name = "NO_VOTES")
    private Integer noVotes;

    @Column(name = "PERCENT_NO_VOTES")
    private Integer percentNoVotes;

    private Integer total;
}
