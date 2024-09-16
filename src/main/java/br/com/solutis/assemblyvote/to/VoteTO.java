package br.com.solutis.assemblyvote.to;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteTO {

    private Integer voteId;
    @NotNull
    private Integer memberId;
    @NotNull
    private Integer sessionID;
    @NotEmpty
    private String agree;

}
