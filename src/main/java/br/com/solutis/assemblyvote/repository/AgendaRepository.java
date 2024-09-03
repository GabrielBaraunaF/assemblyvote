package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda,Integer> {
}
