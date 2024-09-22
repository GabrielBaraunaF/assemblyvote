package br.com.solutis.assemblyvote.facade;

import br.com.solutis.assemblyvote.entity.*;
import br.com.solutis.assemblyvote.enums.YesNoEnum;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static br.com.solutis.assemblyvote.service.DefaultSessionService.CLOSED;

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
        Agenda agenda = agendaService.findById(sessionTO.getAgendaId());

        if (agenda == null) {
            throw new ApplicationException("Agenda not found");
        }

        Session session = sessionMapper.toSession(sessionTO);
        session = sessionService.save(session);
        return sessionMapper.toSessionTO(session);
    }

    @Override
    public VoteTO vote(VoteTO voteTO) {

        if (!YesNoEnum.isValid(voteTO.getAgree())){
            throw new ApplicationException("invalid vote");
        }

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
            throw new ApplicationException("Association prevented from voting");
        }

    }

    @Override
    public VoteCoutingTO findVoteCounting(int sessionId) {
        VoteCounting voteCouting = voteCoutingService.findBySessionId(sessionId);
        if (voteCouting == null) {
            throw new ApplicationException("Session not found");
        }
        return voteCoutingMapper.toVoteCoutingTo(voteCouting);
    }


    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    private void rotine() {
        countVote();
        closeExpiredOpenSession();
    }


    @Override
    public void countVote() {
        List<Vote> votes = voteService.findAllVoteWithOpenSession();

        for (Vote vote : votes) {
            VoteCounting voteCounting = getOrCreateVoteCounting(vote);

            updateVoteCounts(vote, voteCounting);
            determineWinner(voteCounting);

            vote.setIsCounted(true);
            voteService.update(vote);
            voteCoutingService.save(voteCounting);

        }

    }

    private VoteCounting getOrCreateVoteCounting(Vote vote) {
        VoteCounting voteCounting = voteCoutingService.findBySessionId(vote.getSession().getId());
        if (voteCounting == null) {
            voteCounting = new VoteCounting();
            voteCounting.setSession(vote.getSession());
            voteCounting.setStatus("Open");
        }
        return voteCounting;
    }

    private void updateVoteCounts(Vote vote, VoteCounting voteCounting) {

        YesNoEnum answer = YesNoEnum.fromValue(vote.getAgree());

        if (answer == YesNoEnum.YES) {
            voteCounting.setYesVotes(voteCounting.getYesVotes() + 1);
        } else {
            voteCounting.setNoVotes(voteCounting.getNoVotes() + 1);
        }

        voteCounting.setTotal(voteCounting.getTotal() + 1);
        float total = voteCounting.getTotal();
        voteCounting.setPercentNoVotes(voteCounting.getNoVotes() / total * 100);
        voteCounting.setPercentYesVotes(voteCounting.getYesVotes() / total * 100);
    }

    private void determineWinner(VoteCounting voteCounting) {
        if (voteCounting.getYesVotes() == voteCounting.getNoVotes()) {
            voteCounting.setWinner("Tied Vote");
        } else if (voteCounting.getYesVotes() > voteCounting.getNoVotes()) {
            voteCounting.setWinner("In Favor of the Agenda");
        } else {
            voteCounting.setWinner("Against the Agenda");
        }
    }

    @Override
    public void closeExpiredOpenSession() {
        List<Session> sessions = sessionService.findAllOpenSession();
        for (Session session : sessions) {
            if (session.isTheVotingDeadlineHasExpired()) {
                VoteCounting voteCounting = voteCoutingService.findBySessionId(session.getId());
                voteCounting.setStatus("closed");
                voteCoutingService.save(voteCounting);
                session.setState(CLOSED);
                session = sessionService.update(session);
                SessionTO sessionTO = sessionMapper.toSessionTO(session);
                producer.sendMessage(sessionTO);
            }
        }
    }
}
