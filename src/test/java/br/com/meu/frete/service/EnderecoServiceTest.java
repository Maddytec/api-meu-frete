package br.com.meu.frete.service;

import br.com.meu.frete.DTO.EnderecoViaCepResponseDTO;
import br.com.meu.frete.client.ViaCepClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private ViaCepClient viaCepClient;

    private EnderecoViaCepResponseDTO enderecoViaCepResponseDTO;

    private static final String CEP = "87456123";

    @BeforeEach
    private void init() {

        enderecoViaCepResponseDTO = EnderecoViaCepResponseDTO.builder()
                .cep("87456123")
                .bairro("Ondina")
                .localidade("Salvador")
                .complemento("Casa")
                .uf("BA")
                .logradouro("Travessa Jardim Botânico")
                .build();
    }

    @Test
    void getEnderecoTest() {

        when(viaCepClient.getEndereco(CEP)).thenReturn(this.enderecoViaCepResponseDTO);
        EnderecoViaCepResponseDTO enderecoViaCepResponseDTO = enderecoService.getEndereco(CEP);

        assertNotNull(enderecoViaCepResponseDTO);
        assertEquals(CEP, enderecoViaCepResponseDTO.getCep());
        assertEquals("Salvador", enderecoViaCepResponseDTO.getLocalidade());
    }

}