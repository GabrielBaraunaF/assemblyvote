package br.com.solutis.assemblyvote.to;

import lombok.Data;

@Data
public class VoteTO {

    private Integer voteId;
    private Integer memberId;
    private Integer sessionID;
    private String agree;
    private String status;
    private String winner;
}
