package br.com.solutis.assemblyvote.facade;

import br.com.solutis.assemblyvote.to.AgendaTO;
import br.com.solutis.assemblyvote.to.SessionTO;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import br.com.solutis.assemblyvote.to.VoteTO;

public interface AssemblyFacade {

    AgendaTO createAgenda(AgendaTO agendaTO);

    SessionTO openSession(SessionTO sessionTO);

    VoteTO vote(VoteTO voteTO);

    VoteCoutingTO findVoteCounting(int SessionId);

    void countVote();

    void closeExpiredOpenSession();
}
