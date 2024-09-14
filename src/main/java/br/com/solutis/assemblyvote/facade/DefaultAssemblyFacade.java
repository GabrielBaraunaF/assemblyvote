package br.com.solutis.assemblyvote.facade;

import br.com.solutis.assemblyvote.entity.*;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.mapper.AgendaMapper;
import br.com.solutis.assemblyvote.mapper.SessionMapper;
import br.com.solutis.assemblyvote.mapper.VoteCoutingMapper;
import br.com.solutis.assemblyvote.mapper.VoteMapper;
import br.com.solutis.assemblyvote.event.SessionCloseEvent;
import br.com.solutis.assemblyvote.service.*;
import br.com.solutis.assemblyvote.to.AgendaTO;
import br.com.solutis.assemblyvote.to.SessionTO;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import br.com.solutis.assemblyvote.to.VoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultAssemblyFacade implements AssemblyFacade {

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteCoutingService voteCoutingService;
    @Autowired
    private CpfValidatorAPIService cpfValidatorAPIService;
    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private VoteCoutingMapper voteCoutingMapper;
    @Autowired
    private AgendaMapper agendaMapper;
    @Autowired
    private SessionCloseEvent producer;

    @Override
    public AgendaTO createAgenda(AgendaTO agendaTO) {
        Agenda agenda = agendaMapper.toAgenda(agendaTO);
        agenda = agendaService.save(agenda);
        return agendaMapper.toAgendaTO(agenda);
    }

    @Override
    public SessionTO openSession(SessionTO sessionTO) {
        Session session = sessionMapper.toSession(sessionTO);
        session = sessionService.save(session);
        return sessionMapper.toSessionTO(session);
    }

    @Override
    public VoteTO vote(VoteTO voteTO) {

        Member member = memberService.findById(voteTO.getMemberId());

        if (cpfValidatorAPIService.isValid(member.getCpf())) {
            Session session = sessionService.findById(voteTO.getSessionID());
            Vote vote = new Vote();
            vote.setSession(session);
            vote.setMember(member);
            vote.setAgree(voteTO.getAgree());
            vote.setDate(LocalDateTime.now());
            vote = voteService.save(vote);
            return voteMapper.toVoteTO(vote);
        } else {
            throw new ApplicationException("Associado impedido de votar");
        }

    }

    @Override
    public VoteCoutingTO findVoteCounting(int sessionId) {
        VoteCouting voteCouting = voteCoutingService.findBySessionId(sessionId);
        return voteCoutingMapper.toVoteCoutingTo(voteCouting);
    }


    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void rotine() {
        countVote();
        closeExpiredOpenSession();
    }


    @Override
    public void countVote() {
        List<Vote> votes = voteService.findAllVoteWithOpenSession();

        VoteCouting voteCouting = new VoteCouting();

        for (Vote vote : votes) {
            VoteCouting voteCoutingPO = voteCoutingService.findBySessionId(vote.getSession().getId());
            if (voteCoutingPO != null) {
                voteCouting = voteCoutingPO;
            } else {
                voteCouting.setSession(vote.getSession());
            }

            if (voteCouting.getNoVotes() == null) voteCouting.setNoVotes(0);
            if (voteCouting.getYesVotes() == null) voteCouting.setYesVotes(0);

            if (vote.getAgree().equals("S")) {
                voteCouting.setYesVotes(voteCouting.getYesVotes() + 1);
            } else {
                voteCouting.setNoVotes(voteCouting.getNoVotes() + 1);
            }

            voteCouting.setTotal(voteCouting.getTotal() == null ? 1 : voteCouting.getTotal() + 1);

            vote.setIsCounted(true);

            Float total = Float.valueOf(voteCouting.getTotal());
            voteCouting.setPercentNoVotes((float) (voteCouting.getNoVotes() == 0 ? 0 : voteCouting.getNoVotes() / total * 100));
            voteCouting.setPercentYesVotes((float) (voteCouting.getYesVotes() == 0 ? 0 : voteCouting.getYesVotes() / total * 100));

            if (Objects.equals(voteCouting.getYesVotes(), voteCouting.getNoVotes())){
                voteCouting.setWinner("Votação Empatada");
            } else if (voteCouting.getYesVotes()> voteCouting.getNoVotes()) {
                voteCouting.setWinner("A favor da pauta");
            }else{
                voteCouting.setWinner("Contra a Pauta");
            }
            voteCoutingService.save(voteCouting);
            voteService.update(vote);
        }
    }

    @Override
    public void closeExpiredOpenSession() {
        List<Session> sessions = sessionService.findAllOpenSession();
        for (Session session : sessions) {
            if (session.isTheVotingDeadlineHasExpired()) {
                session.setState("F");
                //TODO: CRIAR COSTANTE PARA STATE
                session = sessionService.update(session);
                SessionTO sessionTO = sessionMapper.toSessionTO(session);
                producer.sendMessage(sessionTO);
            }
        }
    }
}
