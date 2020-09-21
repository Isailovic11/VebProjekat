package com.example.vebprojekat.service.implementation;

import com.example.vebprojekat.entity.Sala;
import com.example.vebprojekat.repository.SalaRepository;
import com.example.vebprojekat.service.IF.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaServiceImpl implements SalaService {
    @Autowired
    private SalaRepository salaRepository;

    @Override
    public Sala create(Sala sala) throws Exception{
        if(sala.getId() != null) throw new Exception("ID mora biti null!");

        Sala novi = this.salaRepository.save(sala);
        return novi;

    }

    @Override
    public Sala findOne(Long id){
        Sala novi = this.salaRepository.getOne(id);
        return novi;
    }

    @Override
    public Sala update(Sala sala) throws Exception{
        Sala zaAzurirati = this.salaRepository.getOne(sala.getId());
        if(zaAzurirati == null) throw new Exception("Tražena sala ne postoji!");

        zaAzurirati.setKapacitet(sala.getKapacitet());
        zaAzurirati.setNaziv(sala.getNaziv());
        zaAzurirati.setLista_projekcija(sala.getLista_projekcija());

        //delete(sala.getId());
        Sala novi = this.salaRepository.save(zaAzurirati);

        return zaAzurirati;
    }

    @Override
    public void delete(Long id){
        this.salaRepository.deleteById(id);
    }

    @Override
    public List<Sala> findAll(){
        List<Sala> sale = this.salaRepository.findAll();
        return sale;
    }
}
