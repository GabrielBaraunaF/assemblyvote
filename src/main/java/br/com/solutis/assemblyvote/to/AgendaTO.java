package br.com.solutis.assemblyvote.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;



@Component
@Data
public class AgendaTO {

    private Integer id;

    @NotEmpty
    private String topic;
}
