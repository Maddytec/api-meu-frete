package br.com.meu.frete.service;

import br.com.meu.frete.DTO.CotacaoCalculadaResponseDTO;
import br.com.meu.frete.DTO.CotacaoRequestDTO;
import br.com.meu.frete.DTO.CotacaoResponseDTO;
import br.com.meu.frete.DTO.EnderecoViaCepResponseDTO;
import br.com.meu.frete.client.ViaCepClient;
import br.com.meu.frete.entity.Cotacao;
import br.com.meu.frete.repository.CotacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CotacaoService {

    private final ViaCepClient viaCepClient;

    private final CotacaoRepository cotacaoRepository;

    private BigDecimal valorQuilo = BigDecimal.ONE;
    private BigDecimal ZERO_PORCENTO = BigDecimal.ZERO;
    private BigDecimal CINQUENTA_PORCENTO = new BigDecimal(0.5);
    private BigDecimal SETENTA_E_CINCO_PORCENTO = new BigDecimal(0.75);
    private BigDecimal desconto = BigDecimal.ZERO;
    private Long UM_DIA = 1L;
    private Long TRES_DIAS = 3L;
    private Long DEZ_DIAS = 10L;

    public CotacaoService(ViaCepClient viaCepClient, CotacaoRepository cotacaoRepository) {
        this.viaCepClient = viaCepClient;
        this.cotacaoRepository = cotacaoRepository;
    }

    public CotacaoResponseDTO obterCotacao(CotacaoRequestDTO cotacaoRequestDTO) {

        CotacaoResponseDTO cotacaoResponseDTO = CotacaoResponseDTO.builder()
                .cepOrigem(cotacaoRequestDTO.getCepOrigem())
                .cepDestino(cotacaoRequestDTO.getCepDestino())
                .build();

        BigDecimal valorTotalFrete = getValorTotalFrete(cotacaoRequestDTO);

        EnderecoViaCepResponseDTO enderecoOrigem = viaCepClient.getEndereco(cotacaoRequestDTO.getCepOrigem());
        EnderecoViaCepResponseDTO enderecoDestino = viaCepClient.getEndereco(cotacaoRequestDTO.getCepDestino());

        if (enderecoOrigem.getUf().equals(enderecoDestino.getUf())
                && enderecoOrigem.getDdd().equals(enderecoDestino.getDdd())) {
            setCotacao(cotacaoResponseDTO, valorTotalFrete, CINQUENTA_PORCENTO, UM_DIA);
        } else if (enderecoOrigem.getUf().equals(enderecoDestino.getUf())) {
            setCotacao(cotacaoResponseDTO, valorTotalFrete, SETENTA_E_CINCO_PORCENTO, TRES_DIAS);
        } else {
            setCotacao(cotacaoResponseDTO, valorTotalFrete, ZERO_PORCENTO, DEZ_DIAS);
        }

        cotacaoRepository.save(cotacaoResponseDTO.to(cotacaoRequestDTO));
        return cotacaoResponseDTO;

    }

    private void setCotacao(CotacaoResponseDTO cotacaoResponseDTO, BigDecimal valorTotalFrete, BigDecimal percentualDesconto, Long diasEntregas) {
        desconto = valorTotalFrete.multiply(percentualDesconto);
        cotacaoResponseDTO.setVlTotalFrete(valorTotalFrete.subtract(desconto));
        cotacaoResponseDTO.setDataPrevistaEntrega(LocalDate.now().plusDays(diasEntregas));

    }

    private BigDecimal getValorTotalFrete(CotacaoRequestDTO cotacaoRequestDTO) {
        return valorQuilo.multiply(cotacaoRequestDTO.getPeso());
    }

    public List<CotacaoCalculadaResponseDTO> getCotacaoList() {
        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        List<CotacaoCalculadaResponseDTO> cotacaoCalculadaResponseDTOList = new ArrayList<>();
        cotacaoList.stream().forEach(cotacao -> {
                    cotacaoCalculadaResponseDTOList.add(CotacaoCalculadaResponseDTO.builder()
                            .cepOrigem(cotacao.getCepOrigem())
                            .cepDestino(cotacao.getCepDestino())
                            .dataPrevistaEntrega(cotacao.getDataPrevistaEntrega())
                            .vlTotalFrete(cotacao.getVlTotalFrete())
                            .dataConsulta(cotacao.getDataConsulta())
                            .peso(cotacao.getPeso())
                            .nomeDestinatario(cotacao.getNomeDestinatario())
                            .build());
                }
        );
        return cotacaoCalculadaResponseDTOList;
    }
}
