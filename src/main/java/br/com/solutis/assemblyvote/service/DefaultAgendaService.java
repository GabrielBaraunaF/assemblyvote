package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultAgendaService implements AgendaService{

    @Autowired
    private AgendaRepository repository;

    @Override
    public Agenda save(Agenda agenda) {
        return repository.save(agenda);
    }
}
