package br.com.meu.frete.DTO;

import br.com.meu.frete.entity.Cotacao;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoResponseDTO {

    private BigDecimal vlTotalFrete;


    private LocalDate dataPrevistaEntrega;

    private String cepOrigem;

    private String cepDestino;

    public Cotacao to(CotacaoRequestDTO cotacaoRequestDTO){
       return Cotacao.builder()
                .cepOrigem(this.getCepOrigem())
                .cepDestino(this.getCepDestino())
                .dataConsulta(LocalDate.now())
                .vlTotalFrete(this.vlTotalFrete)
                .peso(cotacaoRequestDTO.getPeso())
                .dataPrevistaEntrega(this.getDataPrevistaEntrega())
                .nomeDestinatario(cotacaoRequestDTO.getNomeDestinatario())
                .build();
    }

}
