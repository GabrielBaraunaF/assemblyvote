package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.VoteCouting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteCoutingRepository extends JpaRepository<VoteCouting,Integer> {
}
