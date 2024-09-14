package br.com.solutis.assemblyvote.mapper;

import br.com.solutis.assemblyvote.entity.Member;
import br.com.solutis.assemblyvote.entity.Session;
import br.com.solutis.assemblyvote.entity.Vote;
import br.com.solutis.assemblyvote.to.VoteTO;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    public Vote toVote(VoteTO voteTo) {
        Vote vote = new Vote();

        Member member = new Member();
        member.setId(voteTo.getMemberId());

        Session session = new Session();
        session.setId(voteTo.getSessionID());

        vote.setMember(member);
        vote.setSession(session);
        vote.setAgree(voteTo.getAgree());

        return vote;
    }

    public VoteTO toVoteTO(Vote vote) {
        VoteTO voteTO = new VoteTO();
        voteTO.setSessionID(vote.getSession().getId());
        voteTO.setMemberId(vote.getMember().getId());
        voteTO.setAgree(vote.getAgree());
        voteTO.setVoteId(vote.getId());
        System.out.println(vote);
        return voteTO;
    }
}
