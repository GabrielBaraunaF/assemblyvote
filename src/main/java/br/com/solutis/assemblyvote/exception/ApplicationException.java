package br.com.solutis.assemblyvote.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message){
        super(message);
    }
}
