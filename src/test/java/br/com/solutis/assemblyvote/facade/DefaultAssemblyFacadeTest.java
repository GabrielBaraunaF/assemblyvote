package br.com.solutis.assemblyvote.facade;

import br.com.solutis.assemblyvote.entity.*;
import br.com.solutis.assemblyvote.event.SessionCloseEvent;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.mapper.AgendaMapper;
import br.com.solutis.assemblyvote.mapper.SessionMapper;
import br.com.solutis.assemblyvote.mapper.VoteCoutingMapper;
import br.com.solutis.assemblyvote.mapper.VoteMapper;
import br.com.solutis.assemblyvote.service.*;
import br.com.solutis.assemblyvote.to.AgendaTO;
import br.com.solutis.assemblyvote.to.SessionTO;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import br.com.solutis.assemblyvote.to.VoteTO;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAssemblyFacadeTest {

    @InjectMocks
    private DefaultAssemblyFacade facade;
    @Mock
    private AgendaService agendaService;
    @Mock
    private AgendaMapper agendaMapper;
    @Mock
    private SessionService sessionService;
    @Mock
    private SessionMapper sessionMapper;
    @Mock
    private VoteService voteService;
    @Mock
    private VoteMapper voteMapper;
    @Mock
    private CpfValidatorAPIService cpfValidatorAPIService;
    @Mock
    private MemberService memberService;
    @Mock
    private VoteCoutingMapper voteCoutingMapper;
    @Mock
    private VoteCoutingService voteCoutingService;
    @Captor
    ArgumentCaptor<VoteCounting> voteCoutingCaptor;
    @Mock
    private SessionCloseEvent producer;
    @Captor
    ArgumentCaptor<Session> sessionArgumentCaptor;


    @Test
    void shoulCreatedNewAgenda() {
        AgendaTO agendaTO = new AgendaTO();
        Agenda agenda = new Agenda();
        agendaTO.setTopic("dinheiro");
        agenda.setTopic("dinheiro");

        when(agendaMapper.toAgenda(agendaTO)).thenReturn(agenda);

        agenda.setId(1);
        when(agendaService.save(agenda)).thenReturn(agenda);

        agendaTO.setId(1);
        when(agendaMapper.toAgendaTO(agenda)).thenReturn(agendaTO);

        AgendaTO result = facade.createAgenda(agendaTO);

        assertEquals(agendaTO, result);
    }

    @Test
    void givenOpenSessionWithAgendaNotExitsMustThrowException() {
        SessionTO sessionTo = new SessionTO();
        sessionTo.setAgendaId(1);
        sessionTo.setTimeOpen(2);
        when(agendaService.findById(anyInt())).thenReturn(null);
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    facade.openSession(sessionTo);
                }
        );
        assertEquals("Agenda not found", exception.getMessage());
    }

    @Test
    void shouldOpenNewSession() {
        SessionTO sessionTo = new SessionTO();
        sessionTo.setAgendaId(1);
        sessionTo.setTimeOpen(2);

        SessionTO sessionEntrada = new SessionTO();
        sessionEntrada.setAgendaId(1);
        sessionEntrada.setTimeOpen(2);

        Agenda agenda = getAgenda();
        agenda.setTopic("topic");

        when(agendaService.findById(anyInt())).thenReturn(agenda);

        Session session = new Session();
        session.setAgenda(agenda);
        session.setTime(2);

        when(sessionMapper.toSession(sessionEntrada)).thenReturn(session);

        Session sessionPersist = new Session();
        sessionPersist.setTime(2);
        sessionPersist.setOpening(LocalDateTime.now());
        sessionPersist.setId(1);
        sessionPersist.setAgenda(agenda);
        sessionPersist.setState("A");

        when(sessionService.save(session)).thenReturn(sessionPersist);

        sessionTo.setSessionId(1);

        when(sessionMapper.toSessionTO(sessionPersist)).thenReturn(sessionTo);

        SessionTO result = facade.openSession(sessionEntrada);

        assertEquals(result, sessionTo);

    }

    @Test
    void givenNewVoteWhenSavedReturVoteTO() {
        Member member = getMember();

        when(memberService.findById(1)).thenReturn(member);

        Agenda agenda = getAgenda();

        Session session = getSession(agenda);

        when(sessionService.findById(1)).thenReturn(session);

        Vote vote = getVote(member, session);
        VoteTO voteTO = new VoteTO();
        voteTO.setMemberId(1);
        voteTO.setSessionID(1);
        voteTO.setAgree("y");

        when(cpfValidatorAPIService.isValid(vote.getMember().getCpf())).thenReturn(true);

        Vote votePersist = new Vote();
        votePersist.setId(1);
        votePersist.setIsCounted(false);
        votePersist.setMember(member);
        votePersist.setSession(session);
        votePersist.setAgree("y");
        votePersist.setIsCounted(false);

        when(voteService.save(any())).thenReturn(votePersist);

        VoteTO voteTOPersist = new VoteTO();
        voteTOPersist.setVoteId(1);
        voteTOPersist.setMemberId(1);
        voteTOPersist.setSessionID(1);
        voteTOPersist.setAgree("y");

        when(voteMapper.toVoteTO(votePersist)).thenReturn(voteTOPersist);
        VoteTO result = facade.vote(voteTO);

        assertEquals(result, voteTOPersist);
    }

    @Test
    void ShouldMustThorwExcepetionWhenCpfValidatorIsFalse() {
        Member member = getMember();

        when(memberService.findById(1)).thenReturn(member);

        Agenda agenda = getAgenda();

        Session session = getSession(agenda);

        Vote vote = getVote(member, session);
        VoteTO voteTO = new VoteTO();
        voteTO.setMemberId(1);
        voteTO.setSessionID(1);
        voteTO.setAgree("y");

        when(cpfValidatorAPIService.isValid(vote.getMember().getCpf())).thenReturn(false);

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    facade.vote(voteTO);
                }
        );
        assertEquals("Association prevented from voting", exception.getMessage());
    }


    @Test
    void givenSessionIdExistsShouldReturnVoteCouting() {
        voteCoutingService.findBySessionId(1);
        Session session = new Session();
        session.setId(1);

        VoteCounting voteCouting = new VoteCounting();
        voteCouting.setNoVotes(1);
        voteCouting.setYesVotes(1);
        voteCouting.setTotal(2);
        voteCouting.setPercentNoVotes(50f);
        voteCouting.setPercentYesVotes(50f);
        voteCouting.setSession(session);
        voteCouting.setId(1);
        when(voteCoutingService.findBySessionId(1)).thenReturn(voteCouting);

        VoteCoutingTO voteCoutingTO = new VoteCoutingTO();
        voteCoutingTO.setNoVotes(1);
        voteCoutingTO.setYesVotes(1);
        voteCoutingTO.setTotal(2);
        voteCoutingTO.setPercentNoVotes(50f);
        voteCoutingTO.setPercentYesVotes(50f);
        voteCoutingTO.setSessionId(1);
        when(voteCoutingMapper.toVoteCoutingTo(voteCouting)).thenReturn(voteCoutingTO);

        VoteCoutingTO result = facade.findVoteCounting(1);

        assertEquals(result, voteCoutingTO);
    }

    @Test
    void givenSessionIdAlredyNotExitsMustTrhowException() {
        voteCoutingService.findBySessionId(1);
        Session session = new Session();
        session.setId(1);

        VoteCounting voteCouting = new VoteCounting();
        voteCouting.setNoVotes(1);
        voteCouting.setYesVotes(1);
        voteCouting.setTotal(2);

        voteCouting.setPercentNoVotes(50f);
        voteCouting.setPercentYesVotes(50f);
        voteCouting.setSession(session);
        voteCouting.setId(1);
        when(voteCoutingService.findBySessionId(1)).thenReturn(null);
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    facade.findVoteCounting(1);
                }
        );
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void givenVoteWithOpenSession() {

        Member member = getMember();
        Agenda agenda = getAgenda();
        Session session = getSession(agenda);
        Vote vote = getVote(member, session);

        List<Vote> listVote = new ArrayList<>();
        listVote.add(vote);
        listVote.add(vote);
        listVote.add(vote);

        when(voteService.findAllVoteWithOpenSession()).thenReturn(listVote);
        when(voteCoutingService.findBySessionId(anyInt())).thenReturn(null);

        facade.countVote();
        verify(voteCoutingService, times(3)).findBySessionId(vote.getSession().getId());
    }

    @Test
    void givenVoteWithExistingVoteCountingOpenSession() {

        Member member = getMember();
        Agenda agenda = getAgenda();
        Session session = getSession(agenda);
        Vote vote = getVote(member, session);

        List<Vote> listVote = new ArrayList<>();
        listVote.add(vote);
        listVote.add(vote);
        listVote.add(vote);

        VoteCounting voteCouting = new VoteCounting();
        voteCouting.setId(1);
        voteCouting.setSession(vote.getSession());
        voteCouting.setYesVotes(10);
        voteCouting.setNoVotes(5);

        when(voteService.findAllVoteWithOpenSession()).thenReturn(listVote);
        when(voteCoutingService.findBySessionId(anyInt())).thenReturn(voteCouting);

        facade.countVote();

        verify(voteCoutingService, times(3)).save(voteCoutingCaptor.capture());
        assertEquals(voteCouting.getYesVotes(), voteCoutingCaptor.getValue().getYesVotes());
        assertEquals(voteCouting.getNoVotes(), voteCoutingCaptor.getValue().getNoVotes());
    }

    @Test
    void ShouldClosedOpenSessions() {
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(getSession(getAgenda()));
        when(sessionService.findAllOpenSession()).thenReturn(sessionList);

        Session session = sessionList.get(0);
        LocalDateTime dataHora = LocalDateTime.of(2023, 3, 15, 10, 30);
        session.setOpening(dataHora);
        session.setTime(1);

        SessionTO sessionTO = new SessionTO();
        sessionTO.setSessionId(session.getId());
        sessionTO.setTimeOpen(1);
        sessionTO.setAgendaId(session.getAgenda().getId());

        Session sessionPersist = getSession(getAgenda());
        sessionPersist.setOpening(dataHora);
        sessionPersist.setTime(1);
        sessionPersist.setState("F");

        VoteCounting voteCounting = new VoteCounting();

        when(sessionService.update(session)).thenReturn(sessionPersist);
        when(sessionMapper.toSessionTO(session)).thenReturn(sessionTO);
        when(voteCoutingService.findBySessionId(1)).thenReturn(voteCounting);

        facade.closeExpiredOpenSession();

        verify(sessionService, times(1)).update(sessionArgumentCaptor.capture());
        verify(producer).sendMessage(sessionTO);

        assertEquals(sessionPersist, sessionArgumentCaptor.getValue());

    }

    private Vote getVote(Member member, Session session) {
        Vote vote = new Vote();
        vote.setMember(member);
        vote.setSession(session);
        vote.setAgree("y");
        return vote;
    }

    private Session getSession(Agenda agenda) {
        Session session = new Session();
        session.setId(1);
        session.setState("A");
        session.setAgenda(agenda);
        return session;
    }

    private Agenda getAgenda() {
        Agenda agenda = new Agenda();
        agenda.setId(1);
        return agenda;
    }

    private Member getMember() {
        Member member = new Member();
        member.setId(1);
        member.setCpf("09190851560");
        return member;
    }

    public void closedSession() {
        sessionService.findAllOpenSession();
    }


}