package com.example.vebprojekat.service.implementation;

import com.example.vebprojekat.entity.OdgledanFilm;
import com.example.vebprojekat.repository.OdgledanFilmRepository;
import com.example.vebprojekat.service.IF.OdgledanFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdgledanFilmServiceImpl implements OdgledanFilmService {
    @Autowired
    private OdgledanFilmRepository odgledanFilmRepository;

    @Override
    public OdgledanFilm create(OdgledanFilm odgledanFilm){
        if(odgledanFilm.getId() != null) return null;

        return this.odgledanFilmRepository.save(odgledanFilm);
    }

    @Override
    public OdgledanFilm findOne(Long id){
        return this.odgledanFilmRepository.getOne(id);
    }

    @Override
    public OdgledanFilm update(OdgledanFilm odgledanFilm) throws Exception {
        OdgledanFilm zaAzurirati = this.odgledanFilmRepository.getOne(odgledanFilm.getId());
        if(zaAzurirati == null) throw new Exception("Traženi korisnik ne postoji!");

        zaAzurirati.setOcenjen(odgledanFilm.getOcenjen());
        zaAzurirati.setOcena(odgledanFilm.getOcena());
        zaAzurirati.setFilm(odgledanFilm.getFilm());
        zaAzurirati.setGledalac(odgledanFilm.getGledalac());

        return odgledanFilmRepository.save(zaAzurirati);
    }

    @Override
    public void delete(Long id) {
        this.odgledanFilmRepository.deleteById(id);
    }

    @Override
    public List<OdgledanFilm> findAll(){
        return this.odgledanFilmRepository.findAll();
    }

}
