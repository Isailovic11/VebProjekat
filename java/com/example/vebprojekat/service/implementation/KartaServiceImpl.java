package com.example.vebprojekat.service.implementation;

import com.example.vebprojekat.entity.Karta;
import com.example.vebprojekat.repository.KartaRepository;
import com.example.vebprojekat.service.IF.KartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KartaServiceImpl implements KartaService {
    @Autowired
    private KartaRepository kartaRepository;

    @Override
    public Karta create(Karta karta) throws Exception{
        if(karta.getId() != null) throw new Exception("ID mora biti null!");

        Karta novi = this.kartaRepository.save(karta);
        return novi;

    }

    @Override
    public Karta findOne(Long id){
        Karta novi = this.kartaRepository.getOne(id);
        return novi;
    }

    @Override
    public Karta update(Karta karta) throws Exception{
        Karta zaAzurirati = this.kartaRepository.getOne(karta.getId());
        if(zaAzurirati == null) throw new Exception("Tražena sala ne postoji!");

        zaAzurirati.setBroj_mesta(karta.getBroj_mesta());
        zaAzurirati.setProjekcija(karta.getProjekcija());

        //delete(sala.getId());
        Karta novi = this.kartaRepository.save(zaAzurirati);

        return zaAzurirati;
    }

    @Override
    public void delete(Long id){
        this.kartaRepository.deleteById(id);
    }

    @Override
    public List<Karta> findAll(){
        List<Karta> karte = this.kartaRepository.findAll();
        return karte;
    }

}
