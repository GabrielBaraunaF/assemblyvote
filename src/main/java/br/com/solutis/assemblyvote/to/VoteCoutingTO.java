package br.com.solutis.assemblyvote.to;

import lombok.Data;

@Data
public class VoteCoutingTO {

    private Integer sessionId;
    private Integer yesVotes;
    private Integer noVotes;
    private Float percentYesVotes;
    private Float percentNoVotes;
    private Integer total;
    private String status;
    private String winneer;
}
