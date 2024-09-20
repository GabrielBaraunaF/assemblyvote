package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.VoteCounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteCoutingRepository extends JpaRepository<VoteCounting,Integer> {

    @Query(value = "select * from vote_counting where session_id =(:session_id) ",nativeQuery = true)
    VoteCounting findBySessionId(Integer session_id);
}
