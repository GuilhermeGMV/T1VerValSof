package org.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Estacionamento {
    private static final double VALOR_FIXO = 5.90;
    private static final double VALOR_INCREMENTAL = 2.50;
    private static final double VALOR_PERNOITE = 50.00;
    private static final int MINUTOS_CORTESIA = 15;
    private static final int HORA_ABERTURA = 8;
    private static final int HORA_FECHAMENTO = 2;

    private LocalDateTime entrada;
    private LocalDateTime saida;
    private boolean isVip;

    public Estacionamento(LocalDateTime entrada, LocalDateTime saida, boolean isVip) {
        this.entrada = entrada;
        this.saida = saida;
        this.isVip = isVip;
    }

    public double calcularValor() {
        if (!horarioValido() || saida.isBefore(entrada)) {
            return -1.0;
        }

        long minutosPermanencia = ChronoUnit.MINUTES.between(entrada, saida);

        if (minutosPermanencia <= MINUTOS_CORTESIA) {
            return 0.0;
        }

        boolean pernoite = verificaPernoite();

        double valorTotal = 0.0;

        if (pernoite) {
            valorTotal = calcularValorPernoite();
        } else if (minutosPermanencia <= 60) {
            valorTotal = VALOR_FIXO;
        } else {
            long horasCobradas = minutosPermanencia/60;
            if (minutosPermanencia%60 == 0){
                horasCobradas--;
            }
            valorTotal = VALOR_FIXO + (horasCobradas * VALOR_INCREMENTAL);
        }

        if (isVip) {
            valorTotal *= 0.5;
        }

        return valorTotal;
    }

    private boolean horarioValido() {
        int horaEntrada = entrada.getHour();
        int horaSaida = saida.getHour();

        boolean entradaValida = (horaEntrada >= HORA_ABERTURA || horaEntrada < HORA_FECHAMENTO);
        boolean saidaValida = (horaSaida >= HORA_ABERTURA || horaSaida < HORA_FECHAMENTO);

        return entradaValida && saidaValida;
    }

    private boolean verificaPernoite() {
        LocalDateTime limitePernoite = entrada.withHour(HORA_ABERTURA).withMinute(0);
        if (ChronoUnit.DAYS.between(entrada.toLocalDate(), saida.toLocalDate()) >= 1.0){
            return saida.isAfter(limitePernoite);
        }
        return false;

    }

    private double calcularValorPernoite() {
        long diasPernoite = ChronoUnit.DAYS.between(entrada.toLocalDate(), saida.toLocalDate());
        if (saida.getHour() < HORA_ABERTURA) {
            diasPernoite--;
        }
        return diasPernoite * VALOR_PERNOITE;
    }

    public static void main(String[] args) {
        LocalDateTime entrada = LocalDateTime.of(2024, 10, 7, 22, 0);
        LocalDateTime saida = LocalDateTime.of(2024, 10, 8, 9, 0);
        boolean isVip = false;

        Estacionamento estacionamento = new Estacionamento(entrada, saida, isVip);
        double valorAPagar = estacionamento.calcularValor();
        System.out.println("Valor a pagar: R$ " + valorAPagar);
    }
}
