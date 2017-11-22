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
@Table(name = "tattoo")
public class TattooEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String descricao;

    @NotNull
    private String localCorpo;

    @NotNull
    private String estilo;

    @NotNull
    private int numeroSessoes;

    @NotNull
    private BigDecimal valor;
}
