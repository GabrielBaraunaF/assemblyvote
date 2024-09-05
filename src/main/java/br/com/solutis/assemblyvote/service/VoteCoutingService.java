package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.VoteCouting;

import java.util.Optional;

public interface VoteCoutingService {
    VoteCouting save(VoteCouting voteCouting);
    VoteCouting findBySessionId(Integer id);
}
