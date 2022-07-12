package com.letscode.dto;

import lombok.Data;

@Data
public class Cep {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}
