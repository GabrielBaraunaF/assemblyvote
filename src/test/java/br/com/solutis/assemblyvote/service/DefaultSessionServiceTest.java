package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSessionServiceTest {

    @Mock
    private SessionRepository repository;

    @InjectMocks
    private DefaultSessionService service;

    @Test
    void givenNewSessionSaveWithAgendaExitsMustThrowException(){
        Session session= getSession();

        when(repository.findByAgendaId(1)).thenReturn(session);

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    service.save(session);
                }
        );
        assertEquals("There is already a session for this agenda", exception.getMessage());
    }

    @Test
    void givenNewSessionSavedWithSucess(){
        Agenda agenda = getAgenda();
        Session session = new Session();
        session.setAgenda(agenda);

        Session sessionExpected =getSession();

        when(repository.save(session)).thenReturn(sessionExpected);

        Session sessionActual= service.save(session);

        assertEquals(sessionExpected,sessionActual);

    }
    @Test
    void givenSessionIDNotExitsMustThorwException(){
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    service.findById(anyInt());
                }
        );
        assertEquals("session not found", exception.getMessage());
    }

    private Session getSession(){
        Agenda agenda = getAgenda();

        Session session= new Session();
        session.setId(1);
        session.setAgenda(agenda);

        return session;
    }

    private static Agenda getAgenda() {
        Agenda agenda = new Agenda();
        agenda.setId(1);
        return agenda;
    }
}