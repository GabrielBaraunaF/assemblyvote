package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.VoteCounting;

import java.util.List;

public interface VoteCoutingService {
    VoteCounting save(VoteCounting voteCouting);
    VoteCounting findBySessionId(Integer id);

    void saveAll(List<VoteCounting> voteCountingList);
}
