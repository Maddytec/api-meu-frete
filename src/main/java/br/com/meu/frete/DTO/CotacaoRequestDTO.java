package br.com.meu.frete.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoRequestDTO {

    @NonNull
    private String nomeDestinatario;
    @NonNull
    private BigDecimal peso;
    @NonNull
    private String cepOrigem;
    @NonNull
    private String cepDestino;

}
