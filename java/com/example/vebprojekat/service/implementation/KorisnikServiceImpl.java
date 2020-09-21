package com.example.vebprojekat.service.implementation;

import com.example.vebprojekat.entity.Korisnik;
import com.example.vebprojekat.repository.*;
import com.example.vebprojekat.service.IF.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public Korisnik create(Korisnik korisnik) {
        if(korisnik.getId() != null) return null;

        return this.korisnikRepository.save(korisnik);
    }

    @Override
    public Korisnik findOne(Long id){
        Korisnik novi = this.korisnikRepository.getOne(id);
        return novi;

    }

    @Override
    public Korisnik findByKorisnickoime(String username){
        return korisnikRepository.findByKorisnickoime(username);
    }

    @Override
    public Korisnik update(Korisnik korisnik) throws  Exception{
        Korisnik zaAzurirati = this.korisnikRepository.getOne(korisnik.getId());
        if(zaAzurirati == null) throw new Exception("Traženi korisnik ne postoji!");

        zaAzurirati.setIme(korisnik.getIme());
        zaAzurirati.setPrezime(korisnik.getPrezime());
        zaAzurirati.setAktivan(korisnik.getAktivan());
        zaAzurirati.setDatum_rodjenja(korisnik.getDatum_rodjenja());
        zaAzurirati.setEmail(korisnik.getEmail());
        zaAzurirati.setKorisnickoime(korisnik.getKorisnickoime());
        zaAzurirati.setLozinka(korisnik.getLozinka());
        zaAzurirati.setUloga(korisnik.getUloga());
        zaAzurirati.setKontakt_telefon(korisnik.getKontakt_telefon());
        zaAzurirati.setApproved(korisnik.getApproved());

        return korisnikRepository.save(zaAzurirati);
    }

    @Override
    public void delete(Long id){
        this.korisnikRepository.deleteById(id);
    }

    /*@Override
    public void deleteIfMoreThanOne(Korisnik korisnik){
        Integer count=-1;
        for(Korisnik kor : korisnikRepository.findAll()){
            if(kor.getKorisnicko_ime().equals(korisnik.getKorisnicko_ime())) count++;
        }

        for(int i = 0; i < count; i++){
            korisnik.setAdmin_approve(null);
            korisnik.setApproved(true);
            delete(findByUsername(korisnik.getKorisnicko_ime()).getId());
        }

    }*/

    @Override
    public List<Korisnik> findAll(){
        return this.korisnikRepository.findAll();
    }
}
