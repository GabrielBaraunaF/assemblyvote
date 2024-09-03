package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session,Integer> {

    @Query(value = "select * from session where pauta_id =(:id)",nativeQuery = true)
    Session findByAgendaId(Integer id);
}
