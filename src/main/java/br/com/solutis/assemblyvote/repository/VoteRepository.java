package br.com.solutis.assemblyvote.repository;

import br.com.solutis.assemblyvote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote,Integer> {

    @Query(value = "SELECT * FROM Vote WHERE session_id =(:sessionId) AND member_id =(:memberId)",nativeQuery = true)
    Vote findByMemberIdAndSessionId(Integer memberId,Integer sessionId);
}
