package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Vote;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultVoteService implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Override
    public Vote save(Vote vote) {
        validateInsert(vote);
        vote.setIsCounted(false);
        return repository.save(vote);
    }
    @Override
    public Vote update(Vote vote){
        return repository.save(vote);
    }

    @Override
    public List<Vote> findAllVoteWithOpenSession() {
        return repository.findAll().stream().filter(vote1 -> vote1.getSession().getState().equals("A") && !vote1.getIsCounted()).toList();
    }


    private void validateInsert(Vote vote) {
        if (vote.getSession().getState().equals("F") || vote.getSession().isTheVotingDeadlineHasExpired()){
            throw new ApplicationException("Essa sessão não pode receber mais votos.");
        }

        Vote voteSaved = repository.findByMemberIdAndSessionId(vote.getMember().getId(), vote.getSession().getId());

        if (voteSaved!=null ){
            throw new ApplicationException("Associado ja computou voto nessa sessão");
        }
    }
}
