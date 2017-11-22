package br.com.newidea.desafiotattooapp.service.transactional;

import br.com.newidea.desafiotattooapp.domain.TattooEntity;
import br.com.newidea.desafiotattooapp.repository.TattooRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TattooTransactionalService {

    @Autowired
    private TattooRepository tattooRepository;

    public TattooEntity save(@NonNull final TattooEntity entity) {
        return tattooRepository.save(entity);
    }

}
