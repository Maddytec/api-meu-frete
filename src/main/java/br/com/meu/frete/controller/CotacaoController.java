package br.com.meu.frete.controller;

import br.com.meu.frete.DTO.CotacaoCalculadaResponseDTO;
import br.com.meu.frete.DTO.CotacaoRequestDTO;
import br.com.meu.frete.DTO.CotacaoResponseDTO;
import br.com.meu.frete.service.CotacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cotacao")
@AllArgsConstructor
public class CotacaoController {

    private CotacaoService cotacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CotacaoResponseDTO obterCotacao(@Valid @RequestBody CotacaoRequestDTO cotacaoRequestDTO) {
        return cotacaoService.obterCotacao(cotacaoRequestDTO);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CotacaoCalculadaResponseDTO> getCotacaoList() {
        return cotacaoService.getCotacaoList();
    }
}
