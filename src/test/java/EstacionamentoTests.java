import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.example.Estacionamento;

public class EstacionamentoTests {

    @ParameterizedTest
    @CsvSource({
            // entrada,saida,isVip,valorEsperado
            "2024-10-07T08:00,2024-10-07T08:10,false,0.0",
            "2024-10-07T08:00,2024-10-07T08:15,false,0.0",
            "2024-10-07T08:00,2024-10-07T08:16,false,5.9",
            "2024-10-07T08:00,2024-10-07T09:00,false,5.9",
            "2024-10-07T08:00,2024-10-07T09:01,false,8.4",
            "2024-10-07T08:00,2024-10-07T12:00,false,13.4",
            "2024-10-07T22:00,2024-10-08T09:00,false,50.0",
            "2024-10-07T22:00,2024-10-09T09:00,false,100.0",
            "2024-10-07T08:00,2024-10-07T08:10,true,0.0",
            "2024-10-07T08:00,2024-10-07T09:00,true,2.95",
            "2024-10-07T08:00,2024-10-07T09:01,true,4.2",
            "2024-10-07T22:00,2024-10-08T09:00,true,25.0",
            "2024-10-07T22:00,2024-10-09T09:00,true,50.0",
            "2024-10-07T06:00,2024-10-07T22:00,false,-1.0",
            "2024-10-07T08:00,2024-10-08T06:00,false,-1.0",
            "2024-10-07T09:00,2024-10-07T08:00,false,-1.0"
    })
    void testCalculoTarifaEstacionamento(String entrada, String saida, boolean isVip, double valorEsperado) {
        LocalDateTime dataHoraEntrada = LocalDateTime.parse(entrada);
        LocalDateTime dataHoraSaida = LocalDateTime.parse(saida);

        Estacionamento estacionamento = new Estacionamento(dataHoraEntrada, dataHoraSaida, isVip);
        double valorCalculado = estacionamento.calcularValor();

        assertEquals(valorEsperado, valorCalculado, 0.01,
                "O valor calculado não corresponde ao valor esperado para o cenário de teste.");
    }
}