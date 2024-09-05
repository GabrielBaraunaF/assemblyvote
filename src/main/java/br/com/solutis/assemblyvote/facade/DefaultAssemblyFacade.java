package br.com.solutis.assemblyvote.facade;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.entity.Vote;
import br.com.solutis.assemblyvote.entity.VoteCouting;
import br.com.solutis.assemblyvote.service.AgendaService;
import br.com.solutis.assemblyvote.service.SessionService;
import br.com.solutis.assemblyvote.service.VoteCoutingService;
import br.com.solutis.assemblyvote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DefaultAssemblyFacade implements AssemblyFacade {

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteCoutingService voteCoutingService;

    @Override
    public Agenda createAgenda(Agenda agenda) {
        return agendaService.save(agenda);
    }

    @Override
    public Session openSession(Session session) {
        return sessionService.save(session);
    }

    @Override
    public Vote vote(Vote vote) {
        return voteService.save(vote);
    }

    @Override
    public VoteCouting coutingVotes(int sessionId) {
        return null;
    }

    public void contaabilizarVotos() {
        List<Vote> votes = voteService.findAllSession();
        VoteCouting voteCouting = new VoteCouting();

        for (Vote vote : votes) {
            VoteCouting voteCoutingPO = voteCoutingService.findBySessionId(vote.getId());
            if (voteCoutingPO!=null) {
                voteCouting = voteCoutingPO;
            } else {
                voteCouting.setSession(vote.getSession());
            }

            if (vote.getAgree().equals("S")) {
                voteCouting.setYesVotes(voteCouting.getYesVotes() + 1);
                voteCouting.setTotal(voteCouting.getTotal() + 1);
            } else {
                voteCouting.setNoVotes(voteCouting.getNoVotes() + 1);
                voteCouting.setTotal(voteCouting.getTotal() + 1);
            }

            voteCouting.setPercentNoVotes(voteCouting.getNoVotes() / voteCouting.getTotal());
            voteCouting.setPercentYesVotes(voteCouting.getYesVotes() / voteCouting.getTotal());

            voteCoutingService.save(voteCouting);
        }
    }
}
