package br.com.solutis.assemblyvote.mapper;

import br.com.solutis.assemblyvote.entity.VoteCouting;
import br.com.solutis.assemblyvote.to.VoteCoutingTO;
import org.springframework.stereotype.Component;

@Component
public class VoteCoutingMapper {

    public VoteCoutingTO toVoteCoutingTo(VoteCouting voteCouting) {
        VoteCoutingTO voteCoutingTO = new VoteCoutingTO();
        voteCoutingTO.setSessionId(voteCouting.getSession().getId());
        voteCoutingTO.setNoVotes(voteCouting.getNoVotes());
        voteCoutingTO.setPercentNoVotes(voteCouting.getPercentNoVotes());
        voteCoutingTO.setYesVotes(voteCouting.getYesVotes());
        voteCoutingTO.setPercentYesVotes(voteCouting.getPercentYesVotes());
        voteCoutingTO.setTotal(voteCouting.getTotal());
        voteCoutingTO.setStatus(voteCouting.getStatus());
        voteCoutingTO.setWinneer(voteCouting.getWinner());

        return voteCoutingTO;
    }
}
