package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {

        return rep.findAll().stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());

    }

    public Optional<CarroDTO> getCarrosById(Long id) {
        return rep.findById(id).map(c -> CarroDTO.create(c));
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {

        return rep.findByTipo(tipo).stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());

    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(),"Não foi possível inserir o registro");

        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            // Atualiza o carro
            rep.save(db);

            return CarroDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Long id) {

        if (getCarrosById(id).isPresent()) {
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
