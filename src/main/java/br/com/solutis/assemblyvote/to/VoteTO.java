package br.com.solutis.assemblyvote.to;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "O status do pedido",
            allowableValues = {"PENDING", "APPROVED"},
            example = "y")

    private String agree;

}
