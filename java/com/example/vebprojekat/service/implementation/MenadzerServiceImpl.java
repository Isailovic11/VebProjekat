package com.example.vebprojekat.service.implementation;

import com.example.vebprojekat.entity.Korisnik;
import com.example.vebprojekat.entity.Menadzer;
import com.example.vebprojekat.entity.Uloga;
import com.example.vebprojekat.repository.MenadzerRepository;
import com.example.vebprojekat.service.IF.MenadzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenadzerServiceImpl implements MenadzerService {

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Override
    public Menadzer create(Korisnik korisnik) throws Exception{
        if(korisnik == null) throw new Exception("Niste uneli korisnika");

        Menadzer novi = new Menadzer();
        novi.setKorisnickoime(korisnik.getKorisnickoime());
        novi.setLozinka(korisnik.getLozinka());
        novi.setIme(korisnik.getIme());
        novi.setPrezime(korisnik.getPrezime());
        novi.setKontakt_telefon(korisnik.getKontakt_telefon());
        novi.setEmail(korisnik.getEmail());
        novi.setDatum_rodjenja(korisnik.getDatum_rodjenja());
        novi.setUloga(Uloga.MENADZER);
        novi.setAktivan(korisnik.getAktivan());

        return menadzerRepository.save(novi);
    }

    @Override
    public Menadzer findOne(Long id){
        Menadzer novi = this.menadzerRepository.getOne(id);
        return novi;
    }

    @Override
    public Menadzer update(Menadzer menadzer) throws Exception{
        Menadzer zaAzurirati = menadzerRepository.getOne(menadzer.getId());
        if(zaAzurirati == null) throw new Exception("Traženi menadžer ne postoji!");

        System.out.println("Broj bioskopa za koje je prosledjeni menadzer zaduzen: " + menadzer.getBioskopi().size());

        zaAzurirati.setIme(menadzer.getIme());
        zaAzurirati.setPrezime(menadzer.getPrezime());
        zaAzurirati.setAktivan(menadzer.getAktivan());
        zaAzurirati.setDatum_rodjenja(menadzer.getDatum_rodjenja());
        zaAzurirati.setEmail(menadzer.getEmail());
        zaAzurirati.setKorisnickoime(menadzer.getKorisnickoime());
        zaAzurirati.setLozinka(menadzer.getLozinka());
        zaAzurirati.setUloga(menadzer.getUloga());
        zaAzurirati.setKontakt_telefon(menadzer.getKontakt_telefon());
        zaAzurirati.setBioskopi(menadzer.getBioskopi());

        return this.menadzerRepository.save(zaAzurirati);
    }

    @Override
    public void delete(Long id){
        this.menadzerRepository.deleteById(id);
    }

    @Override
    public List<Menadzer> findAll(){
        List<Menadzer> menadzeri = this.menadzerRepository.findAll();
        return menadzeri;
    }

    @Override
    public Menadzer findByUsername(String username){
        return menadzerRepository.findByKorisnickoime(username);
    }

    @Override
    public Menadzer save(Menadzer menadzer){
        return menadzerRepository.save(menadzer);
    }

}
