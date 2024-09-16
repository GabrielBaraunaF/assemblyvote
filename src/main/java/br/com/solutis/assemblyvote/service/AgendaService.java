package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Agenda;

public interface AgendaService {

    Agenda save(Agenda agenda);

    Agenda findById(Integer agendaId);
}
