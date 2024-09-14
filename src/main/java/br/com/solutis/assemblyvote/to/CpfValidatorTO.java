package br.com.solutis.assemblyvote.to;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpfValidatorTO implements Serializable {
    private Boolean isValid;
    private String cpf;
}
