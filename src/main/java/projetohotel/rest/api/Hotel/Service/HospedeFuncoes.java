package projetohotel.rest.api.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetohotel.rest.api.Hotel.Repository.HospedeRepository;
import projetohotel.rest.api.Hotel.domain.dto.FiltroHospede;
import projetohotel.rest.api.Hotel.domain.dto.FiltroHospedeAtualizar;
import projetohotel.rest.api.Hotel.domain.Entities.Hospede;

import java.util.List;
import java.util.Optional;

@Service
public class HospedeFuncoes {
    @Autowired
    HospedeRepository repository;

    public void Grava(Hospede hospede){
        repository.save(hospede);
    }

    public List<Hospede> BuscaTodosHospedes(){
        return repository.findAll();
    }

    public Hospede BuscaHospede(FiltroHospede filtro){
        for (Hospede hospede : BuscaTodosHospedes())
        {
            if (ComparaFiltroHospede(filtro, hospede))
                return hospede;
        }

        return null;
    }

    public void AtualizaHospede(FiltroHospedeAtualizar filtroAtualizar)
    {
        Hospede registroAntigo = BuscaHospede(new FiltroHospede(filtroAtualizar.getNomeFiltro(),
                filtroAtualizar.getNumeroFiltro(),
                filtroAtualizar.getDocumentoFiltro()));

        if (repository.existsById(registroAntigo.getId()))
            repository.save(new Hospede(registroAntigo.getId(),
                    filtroAtualizar.getNomeHospede(),
                    filtroAtualizar.getNumeroHospede(),
                    filtroAtualizar.getDocumentoHospede()));
    }

    public void DeletarHospede(FiltroHospede filtro){
        Hospede hospede = BuscaHospede(filtro);

        if (hospede == null)
            return;

        repository.delete(hospede);
    }

    public Optional<Hospede> BuscaHospedePeloCodigo(Integer codigo){
        return repository.findById(codigo);
    }

    private boolean ComparaFiltroHospede(FiltroHospede filtro, Hospede hospede)
    {
        return filtro.getNome().equals(hospede.getNome()) ||
                filtro.getDocumento().equals(hospede.getDocumento()) ||
                filtro.getNumero().equals(hospede.getNumero());
    }
}
