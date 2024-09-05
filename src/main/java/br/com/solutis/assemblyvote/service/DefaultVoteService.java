package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Vote;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultVoteService implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Override
    public Vote save(Vote vote) {
        validateInsert(vote);
        return repository.save(vote);
    }

    @Override
    public List<Vote> findAllSession() {
        return repository.findAll().stream().filter(vote1 -> vote1.getSession().getState().equals("A")).toList();
    }


    private void validateInsert(Vote vote) {
        if (vote.getSession().getState().equals("F")){
            throw new ApplicationException("A Sessão ja está fechada");
        }

        Vote voteSaved = repository.findByMemberIdAndSessionId(vote.getMember().getId(), vote.getSession().getId());

        if (voteSaved!=null ){
            throw new ApplicationException("Associado ja computou voto nessa sessão");
        }
    }
}
