package br.com.solutis.assemblyvote.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorOutbound {
    private HttpStatus status;
    private String message;
}
