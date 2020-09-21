package com.example.vebprojekat.controller;

import com.example.vebprojekat.entity.*;
import com.example.vebprojekat.entity.dto.FilmDTO;
import com.example.vebprojekat.entity.dto.KartaDTO;
import com.example.vebprojekat.entity.dto.OdgledanFilmDTO;
import com.example.vebprojekat.entity.dto.ProjekcijaDTO;
import com.example.vebprojekat.service.IF.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class GledalacKontroler {
    @Autowired
    FilmService filmService;

    @Autowired
    GledalacService gledalacService;

    @Autowired
    KartaService kartaService;

    @Autowired
    ProjekcijaService projekcijaService;

    @Autowired
    OdgledanFilmService odgledanFilmService;


    @GetMapping(value = "/oceni/{movie_id}/{grade}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> oceni(@PathVariable(name="movie_id") Long movie_id, @PathVariable(name="grade") Integer grade) throws Exception {
        Gledalac gledalac = gledalacService.findOne(KorisnikKontroler.ulogovan.getId());

        OdgledanFilm odgledanFilm = null;

        for(OdgledanFilm of: gledalac.getOdgledani_filmovi()){
            if(movie_id == of.getId()){
                odgledanFilm = of;
                odgledanFilm.setOcena(grade);
                odgledanFilm.setOcenjen(true);

                odgledanFilmService.update(odgledanFilm);
                break;
            }
        }

        Film film = filmService.findOne(odgledanFilm.getFilm().getId());

        film.ocenjivaciIncrement();
        film.noviZbir(grade);
        film.racunajProsek();

        filmService.update(film);

        if(odgledanFilm == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Object> retVal = new ArrayList<>();
        retVal.add(new OdgledanFilmDTO(odgledanFilm));
        retVal.add(grade);

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/gledalac_rezervisi/{projection_id}/{broj_karata}")
    public ResponseEntity<List<Object>> gledalac_rezervisi(@PathVariable(name="projection_id") Long projection_id, @PathVariable(name="broj_karata") Integer broj_karata) throws Exception {
        Projekcija projekcija = projekcijaService.findOne(projection_id);
        Gledalac gledalac = gledalacService.findOne(KorisnikKontroler.ulogovan.getId());
        Karta karta = new Karta();

        karta.setProjekcija(projekcija);
        karta.setGledalac(gledalac);
        karta.setBroj_mesta(broj_karata);

        Karta karta_iz_baze = kartaService.create(karta);

        projekcija.getKarte().add(karta_iz_baze);
        gledalac.getRezervisane_karte().add(karta_iz_baze);

        Projekcija projekcija_iz_baze = projekcijaService.update(projekcija);
        gledalacService.update(gledalac);

        List<Object> retVal = new ArrayList<>();

        retVal.add(new ProjekcijaDTO(projekcija_iz_baze));
        retVal.add(broj_karata);

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/gledalac_get_rezervacije", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KartaDTO>> gledalac_get_rezervacije(){
        Set<Karta> karte = gledalacService.findOne(KorisnikKontroler.ulogovan.getId()).getRezervisane_karte();
        List<KartaDTO> retVal = new ArrayList<>();

        for(Karta k: karte) retVal.add(new KartaDTO(k));

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/gledalac_otkazi_rezervaciju/{karta_id}")
    public ResponseEntity<KartaDTO> gledalac_otkazi_rezervaciju(@PathVariable(name="karta_id") Long karta_id) throws Exception {
        Karta karta = kartaService.findOne(karta_id);

        Gledalac gledalac = gledalacService.findOne(KorisnikKontroler.ulogovan.getId());
        gledalac.getRezervisane_karte().remove(karta);
        gledalacService.update(gledalac);

        Projekcija projekcija = projekcijaService.findOne(karta.getProjekcija().getId());
        projekcija.getKarte().remove(karta);
        projekcijaService.update(projekcija);

        KartaDTO retVal = new KartaDTO(karta);

        karta.setGledalac(null);
        karta.setProjekcija(null);
        kartaService.delete(karta.getId());

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/gledalac_kupi_kartu/{karta_id}")
    public ResponseEntity<KartaDTO> gledalac_kupi_kartu(@PathVariable(name="karta_id") Long karta_id) throws Exception {
        Karta karta = kartaService.findOne(karta_id);
        KartaDTO retVal = new KartaDTO(karta);
        Film film = filmService.findByNaziv(karta.getProjekcija().getFilm().getNaziv());

        Gledalac gledalac = gledalacService.findOne(KorisnikKontroler.ulogovan.getId());

        Boolean odgledan = false;

        // PROVERA DA LI JE FILM VEĆ ODGLEDAN
        for(OdgledanFilm of: gledalac.getOdgledani_filmovi()){
            if(of.getFilm().getNaziv().equals(film.getNaziv())) {
                odgledan = true;
                System.out.println("Film je bio odgledan");
            }
        }

        Projekcija projekcija = projekcijaService.findOne(karta.getProjekcija().getId());
        projekcija.getKarte().remove(karta);
        projekcijaService.update(projekcija);

        gledalac.getRezervisane_karte().remove(karta);

        if(!odgledan){
            OdgledanFilm of = new OdgledanFilm(null, film, gledalac, false, null);

            OdgledanFilm odgledanFilm = odgledanFilmService.create(of);

            film.getOdgledanifilmovi().add(odgledanFilm);
            filmService.update(film);

            gledalac.getOdgledani_filmovi().add(odgledanFilm);
        }

        gledalacService.update(gledalac);

        karta.setGledalac(null);
        karta.setProjekcija(null);
        kartaService.delete(karta.getId());

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

}
