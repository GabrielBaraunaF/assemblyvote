package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAgendaService implements AgendaService{

    @Autowired
    private AgendaRepository repository;

    @Override
    public Agenda save(Agenda agenda) {
        return repository.save(agenda);
    }

    @Override
    public Agenda findById(Integer agendaId) {
        Optional<Agenda> agendaOptional =repository.findById(agendaId);
       if (agendaOptional.isPresent()){
           return agendaOptional.get();
       }
       return null;
    }
}
