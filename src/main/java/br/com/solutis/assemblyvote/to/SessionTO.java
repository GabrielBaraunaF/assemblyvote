package br.com.solutis.assemblyvote.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class SessionTO {

    private Integer sessionId;
    @NotNull
    private Integer agendaId;
    @Min(value = 1,message = "The value must be positive")
    private Integer timeOpen;
}
