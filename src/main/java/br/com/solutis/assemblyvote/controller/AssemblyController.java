package br.com.solutis.assemblyvote.controller;

import br.com.solutis.assemblyvote.facade.AssemblyFacade;
import br.com.solutis.assemblyvote.to.AgendaTO;
import br.com.solutis.assemblyvote.to.SessionTO;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import br.com.solutis.assemblyvote.to.VoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyFacade facade;

    @PostMapping("/agenda")
    public AgendaTO createAgenda(@RequestBody AgendaTO agendaTO) {
        return facade.createAgenda(agendaTO);
    }

    @PostMapping("/session")
    public SessionTO openSessiom(@RequestBody SessionTO sessionTO) {
        return facade.openSession(sessionTO);
    }

    @PostMapping("/vote")
    public VoteTO vote(@RequestBody VoteTO voteTO) {
        return facade.vote(voteTO);
    }

    @GetMapping("/votecouting/{sessionId}")
    public VoteCoutingTO findVoteCounting(@PathVariable Integer sessionId) {
        return facade.findVoteCounting(sessionId);
    }


}
