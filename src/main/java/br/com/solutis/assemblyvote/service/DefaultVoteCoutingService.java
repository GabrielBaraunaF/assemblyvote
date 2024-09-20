package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.VoteCounting;
import br.com.solutis.assemblyvote.repository.VoteCoutingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DefaultVoteCoutingService implements VoteCoutingService {


    @Autowired
    private VoteCoutingRepository repository;

    @Override
    public VoteCounting save(VoteCounting voteCouting) {
        return repository.save(voteCouting);
    }

    @Override
    public VoteCounting findBySessionId(Integer id) {
        return repository.findBySessionId(id);
    }

}
