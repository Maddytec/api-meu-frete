package br.com.meu.frete.exception;

public class EnderecoNotFoundException extends RuntimeException {

    public EnderecoNotFoundException() {
        super("Cep não encontrado.");
    }

    public EnderecoNotFoundException(String cep) {
        super("Cep: " + cep + " não encontrado ou Inválido");
    }

}