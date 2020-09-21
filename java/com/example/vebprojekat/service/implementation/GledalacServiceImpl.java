package com.example.vebprojekat.service.implementation;

import com.example.vebprojekat.entity.Gledalac;
import com.example.vebprojekat.entity.Korisnik;
import com.example.vebprojekat.entity.Uloga;
import com.example.vebprojekat.repository.GledalacRepository;
import com.example.vebprojekat.service.IF.GledalacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GledalacServiceImpl implements GledalacService {
    @Autowired
    private GledalacRepository gledalacRepository;

    @Override
    public Gledalac create(Korisnik korisnik) throws Exception {

        if(korisnik == null) throw new Exception("Niste uneli korisnika");

        Gledalac novi = new Gledalac();
        novi.setKorisnickoime(korisnik.getKorisnickoime());
        novi.setLozinka(korisnik.getLozinka());
        novi.setIme(korisnik.getIme());
        novi.setPrezime(korisnik.getPrezime());
        novi.setKontakt_telefon(korisnik.getKontakt_telefon());
        novi.setEmail(korisnik.getEmail());
        novi.setDatum_rodjenja(korisnik.getDatum_rodjenja());
        novi.setUloga(Uloga.GLEDALAC);
        novi.setAktivan(korisnik.getAktivan());

        return gledalacRepository.save(novi);
    }

    @Override
    public Gledalac findOne(Long id){
        Gledalac novi = this.gledalacRepository.getOne(id);
        return novi;
    }

    @Override
    public Gledalac update(Gledalac gledalac) throws Exception{
        Gledalac zaAzurirati = this.gledalacRepository.getOne(gledalac.getId());
        if(zaAzurirati == null) throw new Exception("Traženi gledalac ne postoji!");

        zaAzurirati.setIme(gledalac.getIme());
        zaAzurirati.setPrezime(gledalac.getPrezime());
        zaAzurirati.setAktivan(gledalac.getAktivan());
        zaAzurirati.setDatum_rodjenja(gledalac.getDatum_rodjenja());
        zaAzurirati.setEmail(gledalac.getEmail());
        zaAzurirati.setKorisnickoime(gledalac.getKorisnickoime());
        zaAzurirati.setLozinka(gledalac.getLozinka());
        zaAzurirati.setUloga(gledalac.getUloga());
        zaAzurirati.setKontakt_telefon(gledalac.getKontakt_telefon());
        zaAzurirati.setOcenjeni_filmovi(gledalac.getOcenjeni_filmovi());
        zaAzurirati.setOdgledani_filmovi(gledalac.getOdgledani_filmovi());
        zaAzurirati.setRezervisane_karte(gledalac.getRezervisane_karte());

        return this.gledalacRepository.save(zaAzurirati);
    }

    @Override
    public void delete(Long id){
        this.gledalacRepository.deleteById(id);
    }

    @Override
    public List<Gledalac> findAll(){
        List<Gledalac> gledaoci = this.gledalacRepository.findAll();
        return gledaoci;
    }

    @Override
    public Gledalac findByUsername(String username){
        List<Gledalac> gledaoci = findAll();
        for(Gledalac gledalac : gledaoci){
            if(gledalac.getKorisnickoime().equals(username)){
                return gledalac ;
            }
        }
        return null;
    }

}
