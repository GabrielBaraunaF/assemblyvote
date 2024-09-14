package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.VoteCouting;
import br.com.solutis.assemblyvote.repository.VoteCoutingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DefaultVoteCoutingService implements VoteCoutingService {


    @Autowired
    private VoteCoutingRepository repository;

    @Override
    public VoteCouting save(VoteCouting voteCouting) {
        voteCouting.setStatus("Aberta");
        return repository.save(voteCouting);
    }

    @Override
    public VoteCouting findBySessionId(Integer id) {
        return repository.findBySessionId(id);
    }
}
