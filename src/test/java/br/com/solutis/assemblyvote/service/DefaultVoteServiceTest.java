package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Member;
import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.entity.Vote;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultVoteServiceTest {

    @Mock
    private VoteRepository repository;

    @InjectMocks
    private DefaultVoteService voteService;

    @Test
    void givenVoteSessionWhenMemberVoteWhenAlredyVoteMustThrowException() {
        Vote vote = getVote();
        vote.setSession(getSessionOpen());

        when(repository.findByMemberIdAndSessionId(vote.getMember().getId(), vote.getSession().getId())).thenReturn(vote);

        assertThrows(ApplicationException.class, () -> voteService.save(vote));
    }

    @Test
    void givenVoteSessionWhenMemberVoteSessionWithStateClosedMustThrowException() {
        Vote vote = getVote();
        vote.setSession(getSessionClosed());

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    voteService.save(vote);
                }
        );
        assertEquals("A Sessão ja está fechada", exception.getMessage());
    }

    @Test
    void givenVoteWhenSaveReturnWithSucessVote() {
        Vote vote = new Vote();
        Member member = new Member();
        member.setId(1);
        vote.setMember(member);
        vote.setSession(getSessionOpen());

        Vote voteExpected = getVote();
        voteExpected.setSession(getSessionOpen());

        when(repository.save(vote)).thenReturn(voteExpected);

        Vote voteActual = voteService.save(vote);

        assertEquals(voteExpected, voteActual);
    }


    private static Vote getVote() {
        Member member = new Member();
        member.setId(1);

        Vote vote = new Vote();
        vote.setId(1);
        vote.setMember(member);

        return vote;
    }

    private static Session getSessionOpen() {
        Session session = new Session();
        session.setId(1);
        session.setState("aberto");
        return session;
    }

    private static Session getSessionClosed() {
        Session session = new Session();
        session.setId(1);
        session.setState("F");
        return session;
    }

}