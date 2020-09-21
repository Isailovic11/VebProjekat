package com.example.vebprojekat.controller;

import com.example.vebprojekat.entity.*;
import com.example.vebprojekat.entity.dto.BioskopDTO;
import com.example.vebprojekat.entity.dto.FilmDTO;
import com.example.vebprojekat.entity.dto.ProjekcijaDTO;
import com.example.vebprojekat.entity.dto.SalaDTO;
import com.example.vebprojekat.service.IF.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenadzerKontroler {
    @Autowired
    MenadzerService menadzerService;

    @Autowired
    BioskopService bioskopService;

    @Autowired
    SalaService salaService;

    @Autowired
    ProjekcijaService projekcijaService;

    @Autowired
    KartaService kartaService;

    @Autowired
    FilmService filmService;

    @GetMapping(value = "/menadzer_index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BioskopDTO>> menadzer_index(){
        System.out.println("\nPogodio /menadzer_index -------------------------------------\n");
        Menadzer menadzer = menadzerService.findByUsername(KorisnikKontroler.ulogovan.getKorisnicko_ime());
        List<BioskopDTO> bioskopiDTO = new ArrayList<>();
        for(Bioskop b: menadzer.getBioskopi()){
            bioskopiDTO.add(new BioskopDTO(b));
        }

        return new ResponseEntity<>(bioskopiDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/menadzer_index_detalji/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> menadzer_index_detalji(@PathVariable(name="id") Long bioskop_id){
        System.out.println("\nPogodio /menadzer_index_detalji/" + bioskop_id + " -------------------------------------\n");
        Bioskop bioskop = bioskopService.findOne(bioskop_id);
        List<Object> retVal = new ArrayList<>();
        retVal.add(new BioskopDTO(bioskop));

        for(Sala s: bioskop.getSale()){
            retVal.add(new SalaDTO(s));
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/menadzer_index_detalji_sala_projekcije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> menadzer_index_detalji_sala_projekcije(@PathVariable(name="id") Long sala_id){
        System.out.println("\nPogodio /menadzer_index_detalji_sala_projekcije/" + sala_id + " -------------------------------------\n");

        Sala sala = salaService.findOne(sala_id);

        List<Object> retVal = new ArrayList<>();

        retVal.add(new SalaDTO(sala));

        for(Projekcija p: sala.getLista_projekcija()){
            retVal.add(new ProjekcijaDTO(p));
        }
        System.out.println("Izlazim iz sala_projekcije/" + sala_id);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/menadzer_index_detalji_sala_izmeni/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalaDTO> menadzer_index_detalji_sala_izmeni(@PathVariable(name="id") Long sala_id, @RequestBody SalaDTO salaDTO) throws Exception {
        System.out.println("\nPogodio /menadzer_index_detalji_sala_izmeni/" + sala_id + " -------------------------------------\n");

        Sala sala = salaService.findOne(sala_id);
        Boolean changed = false;

        if(salaDTO.getKapacitet() != null) {
            sala.setKapacitet(salaDTO.getKapacitet());
            changed = true;
        }
        if(salaDTO.getNaziv().length() != 0) {
            sala.setNaziv(salaDTO.getNaziv());
            changed = true;
        }
        if(changed) return new ResponseEntity<>(new SalaDTO(salaService.update(sala)), HttpStatus.OK);
        else return new ResponseEntity<>(salaDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/menadzer_index_detalji_sala_obrisi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> menadzer_index_detalji_sala_obrisi(@PathVariable(name = "id") Long sala_id){
        System.out.println("\nPogodio /menadzer_index_detalji_sala_obrisi/" + sala_id + " -------------------------------------\n");

        Sala sala = salaService.findOne(sala_id);
        List<Object> retVal = new ArrayList<>();

        retVal.add(new SalaDTO(sala));
        Bioskop bioskop = sala.getBioskop();
        retVal.add(new BioskopDTO(bioskop));

        bioskop.getSale().remove(sala);
        sala.setBioskop(null);
        for(Projekcija p: sala.getLista_projekcija()){
            for(Karta k: p.getKarte()){
                k.setProjekcija(null);
                kartaService.delete(k.getId());
            }
            p.setSala(null);
            sala.getLista_projekcija().remove(p);
            projekcijaService.delete(p.getId());
        }

        salaService.delete(sala_id);


        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/menadzer_index_nova_sala/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> menadzer_index_nova_sala(@RequestBody SalaDTO salaDTO, @PathVariable(name="id") Long bioskop_id) throws Exception {
        System.out.println("\nPogodio /menadzer_index_nova_sala/" + bioskop_id + " -------------------------------------\n");

        Bioskop bioskop = bioskopService.findOne(bioskop_id);
        Sala nova_sala = new Sala();

        nova_sala.setBioskop(bioskop);
        nova_sala.setNaziv(salaDTO.getNaziv());
        nova_sala.setKapacitet(salaDTO.getKapacitet());

        Sala sala_u_bazi = salaService.create(nova_sala);

        bioskop.getSale().add(sala_u_bazi);
        bioskopService.update(bioskop);

        List<Object> retVal = new ArrayList<>();

        retVal.add(new BioskopDTO(bioskop));
        retVal.add(new SalaDTO(sala_u_bazi));

        System.out.println("Broj objekata koji će biti prosleđen: " + retVal.size());

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/menadzer_index_nova_projekcija/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> menadzer_index_nova_projekcija(@RequestBody ProjekcijaDTO projekcijaDTO, @PathVariable(name="id") Long sala_id) throws Exception {
        System.out.println("\nPogodio /menadzer_index_nova_projekcija/" + sala_id + " -------------------------------------\n");

        Sala sala = salaService.findOne(sala_id);
        Film film = filmService.findByNaziv(projekcijaDTO.getNaziv());

        Projekcija nova_projekcija = new Projekcija();

        nova_projekcija.setSala(sala);
        nova_projekcija.setCena(projekcijaDTO.getCena());
        nova_projekcija.setDatumvreme(projekcijaDTO.getDatumvreme_format());
        nova_projekcija.setFilm(film);

        Projekcija projekcija_u_bazi = projekcijaService.create(nova_projekcija);

        film.getTerminske_liste().add(projekcija_u_bazi);
        sala.getLista_projekcija().add(projekcija_u_bazi);

        List<Object> retVal = new ArrayList<>();

        retVal.add(new SalaDTO(salaService.update(sala)));
        retVal.add(new FilmDTO(filmService.update(film)));

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/menadzer_index_detalji_projekcija_izmeni", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjekcijaDTO> menadzer_index_detalji_projekcija_izmeni(@RequestBody ProjekcijaDTO projekcijaDTO) throws Exception {
        System.out.println("\nPogodio /menadzer_index_detalji_projekcija_izmeni" + " -------------------------------------\n");

        Projekcija projekcija = projekcijaService.findOne(projekcijaDTO.getId());

        Boolean changed = false;

        if(projekcijaDTO.getCena() != null){
            projekcija.setCena(projekcijaDTO.getCena());
            changed = true;
        }
        if(projekcijaDTO.getDatumvreme_format() != null){
            projekcija.setDatumvreme(projekcijaDTO.getDatumvreme_format());
            changed = true;
        }


        if(changed) {
            Projekcija nova = projekcijaService.update(projekcija);
            System.out.println("Izlazim iz izmene projekcije sa promenjenim sadrzajem");
            return new ResponseEntity<>(new ProjekcijaDTO(nova), HttpStatus.OK);
        } else {
            System.out.println("Izlazim iz izmene projekcije");
            return new ResponseEntity<>(projekcijaDTO, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/menadzer_index_detalji_projekcija_obrisi/{id}")
    public ResponseEntity<List<Object>> menadzer_index_detalji_projekcija_obrisi(@PathVariable(name="id") Long id){
        System.out.println("\nPogodio /menadzer_index_detalji_projekcija_obrisi/" + id + " -------------------------------------\n");

        Projekcija projekcija = projekcijaService.findOne(id);


        for(Karta k: projekcija.getKarte()){
            k.setProjekcija(null);
            kartaService.delete(k.getId());
        }

        projekcija.setKarte(null);

        List<Object> retVal = new ArrayList<>();

        retVal.add(new FilmDTO(projekcija.getFilm()));
        retVal.add(new SalaDTO(projekcija.getSala()));

        projekcija.getSala().getLista_projekcija().remove(projekcija);
        projekcija.setSala(null);

        projekcija.getFilm().getTerminske_liste().remove(projekcija);
        projekcijaService.delete(projekcija.getId());

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
