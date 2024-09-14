package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Integer> {
}
