package br.com.newidea.desafiotattooapp.repository;

import br.com.newidea.desafiotattooapp.domain.TattooEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TattooRepository extends JpaRepository<TattooEntity, Long> {

}
