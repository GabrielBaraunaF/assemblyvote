package br.com.solutis.assemblyvote.mapper;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.to.SessionTO;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    public Session toSession(SessionTO sessionTo) {

        Session session = new Session();

        Agenda agenda = new Agenda();
        agenda.setId(sessionTo.getAgendaId());
        session.setAgenda(agenda);
        session.setTime(sessionTo.getTimeOpen());

        return session;
    }

    public SessionTO toSessionTO(Session session) {

        SessionTO sessionTO = new SessionTO();
        sessionTO.setSessionId(session.getId());
        sessionTO.setTimeOpen(session.getTime());
        sessionTO.setAgendaId(session.getAgenda().getId());

        return sessionTO;
    }
}
