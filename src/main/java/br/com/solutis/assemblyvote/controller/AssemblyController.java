package br.com.solutis.assemblyvote.controller;

import br.com.solutis.assemblyvote.facade.AssemblyFacade;
import br.com.solutis.assemblyvote.to.AgendaTO;
import br.com.solutis.assemblyvote.to.SessionTO;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import br.com.solutis.assemblyvote.to.VoteTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assembly/v1")
public class AssemblyController {

    @Autowired
    private AssemblyFacade facade;

    @Operation(summary = "Create Agenda",
            description = "Create a new Agenda",
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AgendaTO.class))
                    })
            }
    )
    @PostMapping("/agenda")
    public ResponseEntity<AgendaTO> createUser(@Valid @RequestBody AgendaTO agendaTO) {
        AgendaTO agenda = facade.createAgenda(agendaTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenda);
    }

    @Operation(summary = "Create Session",
            description = "Create a new Session",
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SessionTO.class))
                    })
            }
    )
    @PostMapping("/session")
    public ResponseEntity<SessionTO> openSessiom(@RequestBody @Valid SessionTO sessionTO) {
        SessionTO session = facade.openSession(sessionTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @Operation(summary = "Create Vote",
            description = "Create a new Vote",
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VoteTO.class))
                    })
            }
    )
    @PostMapping("/vote")
    public ResponseEntity<VoteTO> vote(@RequestBody @Valid VoteTO voteTO) {
        VoteTO vote = facade.vote(voteTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @Operation(summary = "Consult vote Couting",
            description = "consult votes counted from the session ",
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VoteCoutingTO.class))
                    })
            }
    )
    @GetMapping("/votecouting/{sessionId}")
    public VoteCoutingTO findVoteCounting(@PathVariable Integer sessionId) {
        return facade.findVoteCounting(sessionId);
    }


}
