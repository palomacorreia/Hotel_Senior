package projetohotel.rest.api.Hotel.domain.Entities;

import javax.persistence.*;

@Entity
@Table(name = "persons")
public class Hospede {

    public Hospede() {
    }

    public Hospede(Integer id, String nome, String numero, String documento) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.documento = documento;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone", nullable = false)
    private String numero;

    @Column(name = "documento", nullable = false)
    private String documento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
