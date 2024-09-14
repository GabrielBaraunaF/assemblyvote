package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.VoteCouting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteCoutingRepository extends JpaRepository<VoteCouting,Integer> {

    @Query(value = "select * from vote_couting where session_id =(:session_id) ",nativeQuery = true)
    VoteCouting findBySessionId(Integer session_id);
}
