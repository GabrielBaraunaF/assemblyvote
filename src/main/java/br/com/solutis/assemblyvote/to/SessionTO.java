package br.com.solutis.assemblyvote.to;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionTO {

    private Integer sessionId;
    private Integer agendaId;
    private Integer timeOpen;
}
