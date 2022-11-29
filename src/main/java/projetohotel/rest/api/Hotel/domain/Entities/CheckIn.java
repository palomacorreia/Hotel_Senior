package projetohotel.rest.api.Hotel.domain.Entities;
import javax.persistence.*;

@Entity
@Table(name = "checkin")
public class CheckIn {
    public CheckIn(){
    }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "hospede_id", nullable = false)
    private Integer hospede_id;

    @Column(name = "dt_entrada", nullable = false)
    private String dt_entrada;

    @Column(name = "dt_saida", nullable = false)
    private String dt_saida;

    @Column(nullable = false)
    private boolean adicionalveiculo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHospede_id() {
        return hospede_id;
    }

    public void setHospede_id(Integer hospede_id) {
        this.hospede_id = hospede_id;
    }

    public String getDt_entrada() {
        return dt_entrada;
    }

    public void setDt_entrada(String dt_entrada) {
        this.dt_entrada = dt_entrada;
    }

    public String getDt_saida() {
        return dt_saida;
    }

    public void setDt_saida(String dt_saida) {
        this.dt_saida = dt_saida;
    }

    public boolean isAdicionalveiculo() {
        return adicionalveiculo;
    }

    public void setAdicionalveiculo(boolean adicionalveiculo) {
        this.adicionalveiculo = adicionalveiculo;
    }
}
