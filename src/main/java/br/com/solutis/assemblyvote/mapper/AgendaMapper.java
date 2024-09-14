package br.com.solutis.assemblyvote.mapper;

import br.com.solutis.assemblyvote.entity.Agenda;
import br.com.solutis.assemblyvote.to.AgendaTO;
import org.springframework.stereotype.Component;

@Component
public class AgendaMapper {

    public Agenda toAgenda(AgendaTO agendaTO) {
        Agenda agenda = new Agenda();
        agenda.setId(agendaTO.getId());
        agenda.setTopic(agendaTO.getTopic());
        return agenda;
    }

    public AgendaTO toAgendaTO(Agenda agenda) {
        AgendaTO agendaTO = new AgendaTO();
        agendaTO.setId(agenda.getId());
        agendaTO.setTopic(agenda.getTopic());
        return agendaTO;
    }

}
