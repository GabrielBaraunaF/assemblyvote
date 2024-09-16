package br.com.solutis.assemblyvote.controller;

import br.com.solutis.assemblyvote.facade.AssemblyFacade;
import br.com.solutis.assemblyvote.to.AgendaTO;
import br.com.solutis.assemblyvote.to.SessionTO;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import br.com.solutis.assemblyvote.to.VoteTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyFacade facade;


    @PostMapping("/agenda")
    public ResponseEntity<AgendaTO> createUser(@Valid @RequestBody AgendaTO agendaTO) {
        AgendaTO agenda = facade.createAgenda(agendaTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenda);
    }

    @PostMapping("/session")
    public ResponseEntity<SessionTO> openSessiom(@RequestBody @Valid SessionTO sessionTO) {
        SessionTO session = facade.openSession(sessionTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @PostMapping("/vote")
    public ResponseEntity<VoteTO> vote(@RequestBody @Valid VoteTO voteTO) {
        VoteTO vote = facade.vote(voteTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @GetMapping("/votecouting/{sessionId}")
    public VoteCoutingTO findVoteCounting(@PathVariable Integer sessionId) {
        return facade.findVoteCounting(sessionId);
    }


}
