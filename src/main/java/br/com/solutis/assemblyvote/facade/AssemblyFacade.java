package br.com.solutis.assemblyvote.facade;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.entity.Vote;
import br.com.solutis.assemblyvote.entity.VoteCouting;

public interface AssemblyFacade {

    Agenda createAgenda(Agenda agenda);
    Session openSession(Session session);
    Vote vote(Vote vote);
    VoteCouting coutingVotes(int SessionId);



}
