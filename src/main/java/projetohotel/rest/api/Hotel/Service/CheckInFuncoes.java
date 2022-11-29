package projetohotel.rest.api.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projetohotel.rest.api.Hotel.domain.dto.FiltroHospede;
import projetohotel.rest.api.Hotel.domain.Entities.CheckIn;
import projetohotel.rest.api.Hotel.domain.dto.FiltroFazerCheckIn;
import projetohotel.rest.api.Hotel.domain.dto.RetornoCheckIn;
import projetohotel.rest.api.Hotel.Repository.CheckInRepository;
import projetohotel.rest.api.Hotel.domain.Entities.Hospede;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CheckInFuncoes {

    @Autowired
    private CheckInRepository repository;

    @Autowired
    private HospedeFuncoes hospedeFuncoes;

    public void FazerCheckIn(FiltroFazerCheckIn filtroCheckIn){
        Hospede hospede = hospedeFuncoes.BuscaHospede(new FiltroHospede(filtroCheckIn.getNomeHospede(), filtroCheckIn.getNumeroHospede(), filtroCheckIn.getDocumentoHospede()));

        if (hospede == null)
            return;

        CheckIn checkIn = new CheckIn();
        checkIn.setHospede_id(hospede.getId());
        checkIn.setAdicionalveiculo(filtroCheckIn.getAdicionalVeiculo());
        checkIn.setDt_entrada(filtroCheckIn.getDataEntrada());
        checkIn.setDt_saida(filtroCheckIn.getDataSaida());

        repository.save(checkIn);
    }

    public List<RetornoCheckIn> BuscaHospedesNoHotel(){
        return BuscaHospedesCheckIn(true);
    }

    public List<RetornoCheckIn> BuscaHospedesForaHotel(){
        return BuscaHospedesCheckOut(false);
    }
    public List<RetornoCheckIn> BuscaHospedesCheckIn(boolean noHotel){
        ArrayList<Integer> listaCodigoHospede = new ArrayList<>();
        List<RetornoCheckIn> listaRetorno = new LinkedList<RetornoCheckIn>();

        for (CheckIn checkIn : repository.findAll(Sort.by(Sort.Direction.DESC, "id"))) {
            if (listaCodigoHospede.contains(checkIn.getHospede_id()) == true)
                continue;

            listaCodigoHospede.add(checkIn.getHospede_id());

            if (checkIn.getDt_saida().isEmpty()){
                boolean estaNoHotel = true;

                if ((noHotel == true && estaNoHotel == false)
                    || noHotel == false && estaNoHotel == true) {
                    continue;
                }

                RetornoCheckIn retorno = new RetornoCheckIn();

                Hospede hospede = hospedeFuncoes.BuscaHospedePeloCodigo(checkIn.getHospede_id()).get();

                retorno.setNome(hospede.getNome());
                retorno.setDocumento(hospede.getDocumento());
                retorno.setTelefone(hospede.getNumero());
                if(!checkIn.getDt_saida().isEmpty()) {
                    retorno.setValorTotal(
                        CalculaValorHospedagem(StringToLocalDateTime(checkIn.getDt_entrada()),
                            StringToLocalDateTime(checkIn.getDt_saida()),
                            checkIn.isAdicionalveiculo()));
                    retorno.setValorUltimaHospedagem(CalculaValorUltimaHospedagem(hospede));
                }
                listaRetorno.add(retorno);
            }
        }
        return listaRetorno;
    }
    public List<RetornoCheckIn> BuscaHospedesCheckOut(boolean noHotel){
        ArrayList<Integer> listaCodigoHospede = new ArrayList<>();
        List<RetornoCheckIn> listaRetorno = new LinkedList<RetornoCheckIn>();

        for (CheckIn checkIn : repository.findAll(Sort.by(Sort.Direction.DESC, "id"))) {
            if (listaCodigoHospede.contains(checkIn.getHospede_id()) == true)
                continue;

            listaCodigoHospede.add(checkIn.getHospede_id());

            if (!checkIn.getDt_saida().isEmpty()){
                boolean estaNoHotel = AindaEstaNoHotel(
                    StringToLocalDateTime(checkIn.getDt_saida()));

                if ((noHotel == true && estaNoHotel == false)
                    || noHotel == false && estaNoHotel == true) {
                    continue;
                }

            RetornoCheckIn retorno = new RetornoCheckIn();

            Hospede hospede = hospedeFuncoes.BuscaHospedePeloCodigo(checkIn.getHospede_id()).get();

            retorno.setNome(hospede.getNome());
            retorno.setDocumento(hospede.getDocumento());
            retorno.setTelefone(hospede.getNumero());
            if(!checkIn.getDt_saida().isEmpty()) {
                retorno.setValorTotal(
                    CalculaValorHospedagem(StringToLocalDateTime(checkIn.getDt_entrada()),
                        StringToLocalDateTime(checkIn.getDt_saida()),
                        checkIn.isAdicionalveiculo()));
                retorno.setValorUltimaHospedagem(CalculaValorUltimaHospedagem(hospede));
            }
            listaRetorno.add(retorno);
        }
        }
        return listaRetorno;
    }

    protected boolean AindaEstaNoHotel(LocalDateTime dataHoraSaida){
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        return dataHoraSaida.compareTo(dataHoraAtual) > 0;
    }

    protected float CalculaValorHospedagem(LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, boolean usouGaragem){
        float valor = 0;

        LocalDate dataEntrada = dataHoraEntrada.toLocalDate();
        LocalDate dataSaida = dataHoraSaida.toLocalDate();

        for (LocalDate dataAtual = dataEntrada; !dataAtual.isAfter(dataSaida); dataAtual = dataAtual.plusDays(1))
        {
            if (DataEhFinalDeSemana(dataAtual))
                valor += usouGaragem == true ? 170 : 150;
            else
                valor += usouGaragem == true ? 135 : 120;
        }

        LocalTime horaSaida = dataHoraSaida.toLocalTime();

        if (horaSaida.getHour() > 16 || (horaSaida.getHour() == 16 && horaSaida.getMinute() > 30))
        {
            if (DataEhFinalDeSemana(dataSaida))
                valor += usouGaragem == true ? 170 : 150;
            else
                valor += usouGaragem == true ? 135 : 120;
        }

        return valor;
    }

    private float CalculaValorUltimaHospedagem(Hospede hospede){
        ArrayList<CheckIn> listaCheckIn = new ArrayList<>();

        for (CheckIn checkIn : repository.findAll(Sort.by(Sort.Direction.ASC, "id"))){
            if (checkIn.getHospede_id() != hospede.getId())
                continue;

            listaCheckIn.add(checkIn);
        }

        if (listaCheckIn.size() <= 1)
            return 0;

        CheckIn checkIn = listaCheckIn.get(listaCheckIn.size() - 2);

        return CalculaValorHospedagem(StringToLocalDateTime(checkIn.getDt_entrada()), StringToLocalDateTime(checkIn.getDt_saida()), checkIn.isAdicionalveiculo());
    }

    protected boolean DataEhFinalDeSemana(LocalDate data){
        DayOfWeek diaSemana = data.getDayOfWeek();

        return diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY;
    }

    protected LocalDateTime StringToLocalDateTime(String dataHora){
        return LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
