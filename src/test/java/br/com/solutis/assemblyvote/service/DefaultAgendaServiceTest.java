package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.repository.AgendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAgendaServiceTest {

    @InjectMocks
    private DefaultAgendaService service;
    @Mock
    private AgendaRepository repository;

    @Test
     void findById_shouldReturnAgenda_whenFound() {
        Integer agendaId = 1;
        Agenda expectedAgenda = new Agenda();

        when(repository.findById(agendaId)).thenReturn(Optional.of(expectedAgenda));

        Agenda actualAgenda = service.findById(agendaId);

        assertEquals(expectedAgenda, actualAgenda);
    }

    @Test
     void findById_shouldReturnNull_whenNotFound() {
        Integer agendaId = 2;

        when(repository.findById(agendaId)).thenReturn(Optional.empty());

        Agenda actualAgenda = service.findById(agendaId);

        assertNull(actualAgenda);
    }
    @Test
    void shouldReturnAgendaWhenSave(){
        Agenda agenda = new Agenda();
        Agenda savedAgenda = new Agenda();
        savedAgenda.setId(1);

        when(repository.save(agenda)).thenReturn(savedAgenda);
        Agenda result = service.save(agenda);

        verify(repository).save(agenda);
        assertEquals(savedAgenda, result);
    }


}


