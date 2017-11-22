package br.com.newidea.desafiotattooapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data //Getters, setters, hashCode, toString
@Builder //automatiza a criação de objeto
@ToString(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tattooapp")
public class TattooEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tattoo")
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String localCorpo;

    @Column(nullable = false)
    private String estilo;

    @Column(nullable = false)
    private int numeroSessoes;

    @Column(nullable = false)
    private BigDecimal valor;
}
