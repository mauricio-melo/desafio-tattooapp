package br.com.newidea.desafiotattooapp.domain;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data //Getters, setters, hashCode, toString
@Builder //automatiza a criação de objeto
@ToString(of = {"id"}) //Pesquisar Hashcode E toString)
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dropBoxAppIdSequence;

    @Column(nullable = true)
    private String dropBoxId;

    @Column(nullable = true)
    private String dropBoxPathLower;

    @Column(nullable = true)
    private String dropBoxPathDisplay;

    @Column(nullable = true)
    private String dropBoxContentHash;

    @Column(nullable = false)
    private String dropBoxSharedUrlOrigin;

    @Column(nullable = false)
    private String dropBoxSharedUrlDirect;

    @Column(nullable = true)
    private String dropBoxSharedId;
}
