package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultSessionService implements  SessionService{

    @Autowired
    private SessionRepository repository;

    @Override
    public Session save(Session session){
       Session sessionSaved = repository.findByAgendaId(session.getAgenda().getId());
       if (sessionSaved!=null){
           throw new ApplicationException("Ja existe uma sessão para essa pauta");
       }
       return repository.save(session);
    }


}
