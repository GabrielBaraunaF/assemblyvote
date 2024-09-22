package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultSessionService implements SessionService {

    @Autowired
    private SessionRepository repository;

    public static final String OPEN="A";
    public static final String CLOSED="F";

    @Override
    public Session save(Session session) {
        Session sessionSaved = repository.findByAgendaId(session.getAgenda().getId());
        if (sessionSaved != null) {
            throw new ApplicationException("There is already a session for this agenda");
        }
        if (session.getTime() == null || session.getTime() == 0) {
            session.setTime(1);
        }
        session.setOpening(LocalDateTime.now());
        session.setState(OPEN);
        return repository.save(session);
    }

    @Override
    public Session update(Session session) {
        return repository.save(session);
    }

    @Override
    public List<Session> findAllOpenSession() {
        return repository.findAll().stream().filter(session -> session.getState().equals("A")).toList();
    }

    @Override
    public Session findById(Integer id) {
        Optional<Session> session = repository.findById(id);
        if (session.isEmpty()) {
            throw new ApplicationException("session not found");
        }
        return session.get();
    }


}
