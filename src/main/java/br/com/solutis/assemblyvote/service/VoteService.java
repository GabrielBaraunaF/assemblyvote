package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Vote;

import java.util.List;

public interface VoteService {
    Vote save(Vote vote);

    Vote update(Vote vote);

    List<Vote> findAllVoteWithOpenSession();
}
