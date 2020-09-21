package com.example.vebprojekat.controller;

import com.example.vebprojekat.entity.*;
import com.example.vebprojekat.entity.dto.*;
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
import java.util.Set;

@Controller
public class KorisnikKontroler {
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private GledalacService gledalacService;

    @Autowired
    private KartaService kartaService;

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private BioskopService bioskopService;

    @Autowired
    private SalaService salaService;

    public static Ulogovan ulogovan;

    private Boolean program_poceo=false;

    public static List<Projekcija> moviesForSort = new ArrayList<>();

    @GetMapping("/")
    public String welcome() throws Exception {
        if(!program_poceo){
            ulogovan = new Ulogovan();
            System.out.println("Program počeo!");
            program_poceo = true;
            Admin superAdmin = adminService.findByUsername("SuperAdmin");
            for(Korisnik korisnik: korisnikService.findAll()){
                if(!korisnik.getApproved()) {
                    superAdmin.getApprovalList().add(korisnik);
                    korisnik.setAdmin_approve(superAdmin);
                    korisnikService.update(korisnik);
                    adminService.update(superAdmin);
                }
            }
        }
        System.out.println("Ulogovan uloga ('/') mapping): " + ulogovan.getUloga());
        if(ulogovan.getUlogovan()){
            if(ulogovan.getUloga() == Uloga.GLEDALAC) return "gledalac_index.html";
            else if(ulogovan.getUloga() == Uloga.MENADZER) return "menadzer_index.html";
            else if (ulogovan.getUloga() == Uloga.ADMIN) {
                System.out.println("Usao da vratim admin_index.html");
                return "admin_index.html";
            }
        }
        //return new ResponseEntity<>(ulogovan, HttpStatus.OK);
        return "index.html";
    }

    @GetMapping(value = "/index_provera", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ulogovan> index_provera() throws Exception {
        pocetak();
        if(ulogovan == null){
            new Ulogovan();
        } else {
            System.out.println("Ulogovan uloga ('/index_provera' mapping): " + ulogovan.getUloga());
            System.out.println("Ulogovan korisnicko ime ('/index_provera' mapping): " + ulogovan.getKorisnicko_ime());
        }

        return new ResponseEntity<>(ulogovan, HttpStatus.OK);
    }


    // GLEDALAC


    @GetMapping(value = "/gledalac_index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> gledalac_index() throws Exception {
        pocetak();

        Gledalac gledalac = gledalacService.findOne(ulogovan.getId());

        List<Object> retVal = new ArrayList<>();
        retVal.add(new GledalacDTO(gledalac));

        for(OdgledanFilm of: gledalac.getOdgledani_filmovi()){
            retVal.add(new OdgledanFilmDTO(of));
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }


    // ADMIN


    @GetMapping(value = "/admin_index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KorisnikDTO>> admin_index() throws Exception {
        System.out.println("Pogodio /admin_index");

        pocetak();

        if(ulogovan == null || ulogovan.getUloga() != Uloga.ADMIN || !ulogovan.getUlogovan()){
            System.out.println("Šaljem null kao odgovor iz /admin_indexa");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<Korisnik> approveList = adminService.findByUsername(ulogovan.getKorisnicko_ime()).getApprovalList();
        List<KorisnikDTO> approveListDTO = new ArrayList<>();
        for(Korisnik k: approveList) {
            System.out.println("To be approved: " + k.getKorisnickoime());
            approveListDTO.add(new KorisnikDTO(k));
        }

        return new ResponseEntity<>(approveListDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin_odobri_registraciju/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KorisnikDTO>> admin_odobri_registraciju(@PathVariable(name = "id") Long id) throws Exception {
        System.out.println("Pogodio /admin_odobri_registraciju/" + id);

        adminService.approveRegistration(korisnikService.findOne(id));

        List<Korisnik> approvalList = adminService.findByUsername(ulogovan.getKorisnicko_ime()).getApprovalList();
        List<KorisnikDTO> approvalListDTO = new ArrayList<>();

        for(Korisnik k: approvalList){
            System.out.println("To be approved: " + k.getKorisnickoime());
            approvalListDTO.add(new KorisnikDTO(k));
        }

        return new ResponseEntity<>(approvalListDTO, HttpStatus.OK);
    }


    // ADMIN BIOSKOPI


    @GetMapping(value="/admin_bioskopi", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BioskopDTO>> admin_bioskopi(){
        System.out.println("Pogodio /admin_bioskopi");
        List<BioskopDTO> bioskopiDTO = new ArrayList<>();
        for(Bioskop b: bioskopService.findAll()){
            bioskopiDTO.add(new BioskopDTO(b));
        }

        return new ResponseEntity<>(bioskopiDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin_bioskop_obrisi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BioskopDTO> admin_bioskop_obrisi(@PathVariable(name = "id") Long id){
        System.out.println("Pogodio /admin_bioskopi_obrisi/" + id);
        Bioskop bioskop = bioskopService.findOne(id);
        for(Menadzer m: menadzerService.findAll()){
            if(m.getBioskopi().contains(bioskop)){
                m.getBioskopi().remove(bioskop);
            }
        }
        Integer i = 0;

        for(Sala s: bioskop.getSale()){
            for(Projekcija p: s.getLista_projekcija()) {
                p.setSala(null);
                p.getFilm().getTerminske_liste().remove(p);
                for (Karta k : p.getKarte()) {
                    k.setProjekcija(null);
                    k.getGledalac().getRezervisane_karte().remove(k);
                    k.setGledalac(null);
                    kartaService.delete(k.getId());
                }
                p.setKarte(null);
                System.out.println("Brisem projekciju p");
                projekcijaService.delete(p.getId());
            }
            s.setLista_projekcija(null);

            salaService.delete(s.getId());

        }
        bioskop.setSale(null);
        bioskopService.delete(id);

        return new ResponseEntity<>(new BioskopDTO(bioskop), HttpStatus.OK);
    }

    @GetMapping(value = "/admin_bioskop_izmeni_priprema", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenadzerDTO>> admin_bioskop_izmeni_priprema(){
        System.out.println("Pogodio /admin_bioskopi_izmeni_priprema");
        List<MenadzerDTO> menadzeriDTO = new ArrayList<>();

        for(Menadzer m: menadzerService.findAll()){
            if(m.getApproved() && m.getAktivan()) menadzeriDTO.add(new MenadzerDTO(m));
        }

        return new ResponseEntity(menadzeriDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/admin_bioskop_izmeni", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BioskopDTO> admin_bioskop_izmeni(@RequestBody BioskopDTO noviBioskopDTO) throws Exception {
        System.out.println("Pogodio /admin_bioskopi_izmeni");
        System.out.println("BioskopDTO ID: " + noviBioskopDTO.getId());
        Bioskop bioskop = bioskopService.findOne(noviBioskopDTO.getId());

        System.out.println("Prosledjen naziv: " + noviBioskopDTO.getNaziv());
        System.out.println("Prosledjena adresa: " + noviBioskopDTO.getAdresa());
        System.out.println("Duzina prosledjene adrese: " + noviBioskopDTO.getAdresa().length());

        if(noviBioskopDTO.getNaziv().length() != 0) bioskop.setNaziv(noviBioskopDTO.getNaziv());
        if(noviBioskopDTO.getEmail().length() != 0) bioskop.setEmail(noviBioskopDTO.getEmail());
        if(noviBioskopDTO.getAdresa().length() != 0) bioskop.setAdresa(noviBioskopDTO.getAdresa());
        if(noviBioskopDTO.getBr_telefona().length() != 0) bioskop.setBr_telefona(noviBioskopDTO.getBr_telefona());

        bioskopService.update(bioskop);

        return new ResponseEntity<>(new BioskopDTO(bioskop), HttpStatus.OK);
    }

    @GetMapping(value = "/admin_bioskop_dodaj_mngr_priprema/{id}")
    public ResponseEntity<List<MenadzerDTO>> admin_bioskop_dodaj_mngr_priprema(@PathVariable(name = "id") Long id){
        System.out.println("Pogodio /admin_bioskopi_dodaj_mngr_priprema/" + id);

        List<MenadzerDTO> menadzeriDTO = new ArrayList<>();
        Set<Menadzer> menadzeri_u_bioskopu  = bioskopService.findOne(id).getMenadzeri();
        for(Menadzer m: menadzerService.findAll()){
            if(!menadzeri_u_bioskopu.contains(m) && m.getApproved() && m.getAktivan()) {
                System.out.println("Menadzer koji nije vezan za dati bioskop: " + m.getKorisnickoime());
                menadzeriDTO.add(new MenadzerDTO(m));
            }
        }
        return new ResponseEntity<>(menadzeriDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin_bioskop_dodaj_mngr/{bioskop_id}/{mngr_id}")
    public ResponseEntity<List<BioskopDTO>> admin_bioskop_dodaj_mngr(@PathVariable(name = "bioskop_id") Long bioskop_id, @PathVariable(name = "mngr_id") Long mngr_id) throws Exception{
        System.out.println("Pogodio /admin_bioskop_dodaj_mngr/" + bioskop_id + "/" + mngr_id);

        Menadzer menadzer = menadzerService.findOne(mngr_id);
        Bioskop bioskop = bioskopService.findOne(bioskop_id);
        bioskop.getMenadzeri().add(menadzer);
        menadzer.getBioskopi().add(bioskop);
        Bioskop novi = bioskopService.update(bioskop);
        menadzerService.update(menadzer);


        List<BioskopDTO> b = new ArrayList<>();
        b.add(new BioskopDTO(novi));

        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PostMapping(value = "/admin_bioskop_novi", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BioskopDTO> admin_bioskop_novi(@RequestBody BioskopDTO bioskopDTO) throws  Exception{
        Menadzer inicijalni_menadzer = menadzerService.findByUsername(bioskopDTO.getInicijalni_menadzer());

        Bioskop bioskop = new Bioskop(bioskopDTO);
        bioskop.getMenadzeri().add(inicijalni_menadzer);
        Bioskop novi = bioskopService.create(bioskop);
        inicijalni_menadzer.getBioskopi().add(novi);
        menadzerService.update(inicijalni_menadzer);

        return new ResponseEntity<>(new BioskopDTO(novi), HttpStatus.OK);
    }


    // ADMIN MENADZERI


    @GetMapping(value = "/admin_menadzeri", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenadzerDTO>> admin_menadzeri(){
        List<MenadzerDTO> menadzeriDTO = new ArrayList<>();

        for(Menadzer m: menadzerService.findAll()){
            if(m.getApproved() && m.getAktivan()){
                menadzeriDTO.add(new MenadzerDTO(m));
            }
        }

        return new ResponseEntity<>(menadzeriDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin_menadzeri_obrisi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenadzerDTO> admin_menadzeri_obrisi(@PathVariable(name="id") Long id){
        Menadzer menadzer = menadzerService.findOne(id);
        for(Bioskop b: bioskopService.findAll()){
            if(b.getMenadzeri().contains(menadzer)) {
                System.out.println("Menadzer: " + menadzer.getKorisnickoime());
                System.out.println("Bioskop: " + b.getNaziv());
                if(!(b.getMenadzeri().size() == 1)){
                    b.getMenadzeri().remove(menadzer);
                } else {
                    return new ResponseEntity<>(new MenadzerDTO(menadzer), HttpStatus.OK);
                }
            }
        }
        menadzerService.delete(id);
        MenadzerDTO retVal = new MenadzerDTO(menadzer);
        retVal.setObrisan(true);

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/admin_menadzeri_detalji/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> admin_menadzeri_detalji(@PathVariable(name = "id") Long id){
        Menadzer menadzer = menadzerService.findOne(id);
        List<Object> retVal = new ArrayList<>();
        retVal.add(new MenadzerDTO(menadzer));

        for(Bioskop b: menadzer.getBioskopi()){
            retVal.add(new BioskopDTO(b));
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/admin_bioskop_ukloni_mngr/{bioskop_id}/{mngr_id}")
    public ResponseEntity<List<Object>> admin_bioskop_ukloni_mngr(@PathVariable(name="bioskop_id") Long bioskop_id, @PathVariable(name="mngr_id") Long mngr_id) throws Exception {
        Menadzer menadzer = menadzerService.findOne(mngr_id);
        Bioskop bioskop = bioskopService.findOne(bioskop_id);
        List<Object> retVal = new ArrayList<>();

        if(bioskop.getMenadzeri().size() == 1){
            MenadzerDTO novi = new MenadzerDTO(menadzer);
            novi.setObrisan(true);
            retVal.add(novi);
            retVal.add(new BioskopDTO(bioskop));
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        }

        menadzer.getBioskopi().remove(bioskop);
        bioskop.getMenadzeri().remove(menadzer);
        Menadzer novi_mngr = menadzerService.update(menadzer);
        Bioskop novi_bioskop = bioskopService.update(bioskop);

        retVal.add(new MenadzerDTO(novi_mngr));
        retVal.add(new BioskopDTO(novi_bioskop));

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }


    // REGISTRACIJA


    @PostMapping(value = "/registracija", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Korisnik> registracija(@RequestBody Korisnik korisnik) throws Exception {
        if(korisnik == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        //Korisnik noviKorisnik = korisnikService.create(korisnik);

        if(korisnik.getUloga() == Uloga.GLEDALAC) gledalacService.create(korisnik);
        else if (korisnik.getUloga() == Uloga.MENADZER) {
            Menadzer novi = menadzerService.create(korisnik);
            adminService.addToApprovalList(novi);
        }
        else {
            Admin novi = adminService.create(korisnik);
            adminService.addToApprovalList(novi);
        }

        return new ResponseEntity<>(korisnik, HttpStatus.OK);
    }


    // PRIJAVA I ODJAVA


    @PostMapping(value = "/prijava", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginDTO> prijava(@RequestBody LoginDTO loginDTO) throws Exception{
        if(loginDTO == null) return new ResponseEntity<>(null, HttpStatus.OK);

        Korisnik korisnik = korisnikService.findByKorisnickoime(loginDTO.getKorisnickoime());

        if(korisnik == null){
            System.out.println("Pogrešno korisničko ime");
            return new ResponseEntity<>(new LoginDTO(), HttpStatus.OK);
        }

        if(!(korisnik.getLozinka().equals(loginDTO.getLozinka()))) {
            System.out.println("Pogrešna lozinka");
            LoginDTO novi = new LoginDTO(korisnik);
            novi.setLogin(false);
            return new ResponseEntity<>(novi, HttpStatus.OK);
        }
        else {
            if(!korisnik.getApproved()){
                System.out.println("Korisnik nije odobren.");
                return new ResponseEntity<>(new LoginDTO(korisnik), HttpStatus.OK);
            }

            if(!korisnik.getAktivan()){
                System.out.println("Korisnik nije aktivan.");
                return new ResponseEntity<>(new LoginDTO(korisnik), HttpStatus.OK);
            }

            System.out.println("Ispravno korisničko ime i lozinka. Prijava korisnika: " + korisnik.toString());

            ulogovan.setId(korisnik.getId());
            ulogovan.setKorisnicko_ime(korisnik.getKorisnickoime());
            ulogovan.setLozinka(korisnik.getLozinka());
            ulogovan.setUloga(korisnik.getUloga());
            ulogovan.setUlogovan(true);

            return new ResponseEntity<>(new LoginDTO(korisnik), HttpStatus.OK);
        }

    }

    @GetMapping("/odjava")
    public ResponseEntity<Ulogovan> odjava(){
        ulogovan = new Ulogovan();
        return new ResponseEntity<>(ulogovan, HttpStatus.OK);
    }


    // ISPIS FILMOVA I PROJEKCIJA (FILMOVI.HTML)


    @GetMapping("/get_filmovi")
    public ResponseEntity<List<FilmDTO>> get_filmovi() throws Exception {
        pocetak();

        List<Film> filmovi = filmService.findAll();
        List<FilmDTO> filmoviDTO = new ArrayList<>();

        for(Film film : filmovi){
            FilmDTO temp = new FilmDTO(film);
            filmoviDTO.add(temp);
        }

        return new ResponseEntity<>(filmoviDTO, HttpStatus.OK);
    }

    @GetMapping("/get_projekcije/{id}")
    public ResponseEntity<List<ProjekcijaDTO>> get_projekcije(@PathVariable(name = "id") Long id){
        String naziv_film = filmService.findOne(id).getNaziv();
        List<ProjekcijaDTO> slanje = new ArrayList<>();
        moviesForSort.clear();

        for(Projekcija p: projekcijaService.findAll()){
            if(p.getFilm().getNaziv().equals(naziv_film)){
                ProjekcijaDTO temp = new ProjekcijaDTO(p);
                slanje.add(temp);
                moviesForSort.add(p);
            }
        }

       return new ResponseEntity<>(slanje, HttpStatus.OK);
    }

    @GetMapping("/error")
    public String error(){
        return "error.html";
    }

    public void pocetak() throws Exception {
        if(!program_poceo) welcome();
    }

    @PostMapping(value = "/sortiranje", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjekcijaDTO>> sortiranje(@RequestBody String sort_type){
        System.out.println("Pogodio /sortiranje");
        System.out.println("-----------------------------");

        List<ProjekcijaDTO> retVal = new ArrayList<>();
        System.out.println("SORT: " + sort_type);
        System.out.println("-----------------------------");
        if(sort_type.equals("\"cena_opadajuce\"")){
            List<Projekcija> projekcije = projekcijaService.findByOrderByCenaDesc();
            for(Projekcija p: projekcije){
                for(Projekcija p_u_listi: moviesForSort){
                    if(p.getId()==p_u_listi.getId()){
                        retVal.add(new ProjekcijaDTO(p));
                    }
                }
            }
        } else if(sort_type.equals("\"cena_rastuce\"")) {
            List<Projekcija> projekcije = projekcijaService.findByOrderByCenaAsc();
            for(Projekcija p: projekcije){
                for(Projekcija p_u_listi: moviesForSort){
                    if(p.getId()==p_u_listi.getId()){
                        System.out.println("Usao gde treba");
                        retVal.add(new ProjekcijaDTO(p));
                    }
                }
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/pretraga", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjekcijaDTO>> pretraga(@RequestBody ProjekcijaDTO projekcijaDTO) {
        System.out.println("Pogodio /pretraga");


        List<Projekcija> sve_projekcije = projekcijaService.findAll();
        List<Projekcija> projekcije = new ArrayList<>();

        ProjekcijaDTO ps = projekcijaDTO;

        /*for(Projekcija p: sve_projekcije){
            System.out.println("Unet naziv: " + p.getFilm().getNaziv());
            System.out.println("Unet opis: " + p.getFilm().getOpis());
            System.out.println("Unet zanr: " + p.getFilm().getZanr());
            System.out.println("Uneta cena: " + p.getCena());
            System.out.println("Uneta ocena: " + p.getFilm().getProsecnaocena());
            System.out.println("Uneto vreme: " + p.getDatumvreme());
            System.out.println("-----------------------------");
        }*/

        /*System.out.println("Unet naziv: " + ps.getNaziv());
        System.out.println("Unet opis: " + ps.getOpis());
        System.out.println("Unet zanr: " + ps.getZanr());
        System.out.println("Uneta cena: " + ps.getCena());
        System.out.println("Uneta ocena: " + ps.getProsecnaocena());
        System.out.println("Uneto vreme: " + ps.getDatumvreme_format());*/


        if(ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
            projekcije.addAll(sve_projekcije);
        } else {
            for(Projekcija projekcija : sve_projekcije){
                // PO NAZIVU
                if(!ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaNaziv(projekcija, ps)){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                //PO OPISU
                if(ps.getNaziv().equals("") && !ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaOpis(projekcija, ps)){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                //PO ZANRU
                if(ps.getNaziv().equals("") && ps.getOpis().equals("") && !ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaZanr(projekcija, ps)){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                //PO CENI
                if(ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() != null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(projekcija.getCena() < ps.getCena()){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                //PO OCENI
                if(ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()!=null){
                    if(projekcija.getFilm().getProsecnaocena() >= ps.getProsecnaocena()){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                //PO DANU
                if(ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()!=null && ps.getProsecnaocena()==null){
                    projekcije = projekcijaService.findByDatumvreme(ps.getDatumvreme_format());
                }
                // PO NAZIVU I OPISU
                if(!ps.getNaziv().equals("") && !ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaNaziv(projekcija, ps) && pretragaOpis(projekcija, ps)){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO NAZIVU I ZANRU
                if(!ps.getNaziv().equals("") && ps.getOpis().equals("") && !ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaNaziv(projekcija, ps) && pretragaZanr(projekcija, ps)){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO NAZIVU I CENI
                if(!ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() != null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()!=null){
                    if(pretragaNaziv(projekcija, ps) && projekcija.getCena() < ps.getCena()){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO NAZIVU I OCENI
                if(!ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()!=null){
                    if(pretragaNaziv(projekcija, ps) && projekcija.getFilm().getProsecnaocena() >= ps.getProsecnaocena()){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO NAZIVU I DATUMU
                if(!ps.getNaziv().equals("") && ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()!=null && ps.getProsecnaocena()==null){
                    List<Projekcija> temp = projekcijaService.findByDatumvreme(ps.getDatumvreme_format());
                    for(Projekcija t : temp){
                        if(pretragaNaziv(t, ps)){
                            if(!projekcije.contains(t)){
                                projekcije.add(t);
                            }
                        }
                    }
                }
                // PO OPISU I ZANRU
                if(ps.getNaziv().equals("") && !ps.getOpis().equals("") && !ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaOpis(projekcija, ps) && pretragaZanr(projekcija, ps)){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO OPISU I CENI
                if(ps.getNaziv().equals("") && !ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() != null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()==null){
                    if(pretragaOpis(projekcija, ps) && projekcija.getCena() < ps.getCena() ){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO OPISU I OCENI
                if(ps.getNaziv().equals("") && !ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()==null && ps.getProsecnaocena()!=null){
                    if(pretragaOpis(projekcija, ps) && projekcija.getCena() < ps.getCena() ){
                        if(!projekcije.contains(projekcija)){
                            projekcije.add(projekcija);
                        }
                    }
                }
                // PO OPISU I DATUMU
                if(ps.getNaziv().equals("") && !ps.getOpis().equals("") && ps.getZanr().equals("") && ps.getCena() == null && ps.getDatumvreme_format()!=null && ps.getProsecnaocena()==null){
                    List<Projekcija> temp = projekcijaService.findByDatumvreme(ps.getDatumvreme_format());
                    for(Projekcija t : temp){
                        if(pretragaOpis(t, ps)){
                            if(!projekcije.contains(t)){
                                projekcije.add(t);
                            }
                        }
                    }
                }
                //SVE ZAJEDNO
                if(!ps.getNaziv().equals("") && !ps.getOpis().equals("") && !ps.getZanr().equals("") && ps.getCena() != null && ps.getDatumvreme_format()!=null && ps.getProsecnaocena()!=null){
                    List<Projekcija> temp = projekcijaService.findByDatumvreme(ps.getDatumvreme_format());
                    for(Projekcija t : temp){
                        if(pretragaNaziv(t, ps) && pretragaOpis(t, ps) && pretragaZanr(t, ps) && pretragaCena(t, ps) && pretragaOcena(t, ps)){
                            if(!projekcije.contains(t)){
                                projekcije.add(t);
                            }
                        }
                    }
                }
            }
        }

        List<ProjekcijaDTO> retVal = new ArrayList<>();
        moviesForSort.clear();

        for(Projekcija p: projekcije){
            retVal.add(new ProjekcijaDTO(p));
            moviesForSort.add(p);
        }

        /*for(ProjekcijaDTO p: retVal){
            System.out.println("----------------------------");
            System.out.println("Nadjen naziv: " + p.getNaziv());
            System.out.println("Nadjena cena: " + p.getCena());
            System.out.println("Nadjeno vreme: " + p.getDatumvreme());
            System.out.println("----------------------------");

        }
        System.out.println("----------------------------");
        System.out.println("Šaljem pronađene projekcije na front.");
        System.out.println("----------------------------");*/

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }


    public Boolean pretragaNaziv(Projekcija projekcija, ProjekcijaDTO ps){
        if(projekcija.getFilm().getNaziv().toLowerCase().contains(ps.getNaziv().toLowerCase())){
            return true;
        }
        return false;
    }
    public Boolean pretragaOpis(Projekcija projekcija, ProjekcijaDTO ps) {
        if (projekcija.getFilm().getOpis().toLowerCase().contains(ps.getOpis().toLowerCase())) {
            return true;
        }
        return false;
    }
    public Boolean pretragaZanr(Projekcija projekcija, ProjekcijaDTO ps){
        if(projekcija.getFilm().getZanr().toLowerCase().contains(ps.getZanr().toLowerCase())) {
            return true;
        }
        return false;
    }
    public Boolean pretragaCena(Projekcija projekcija, ProjekcijaDTO ps){
        if(projekcija.getCena() < ps.getCena()) {
            return true;
        }
        return false;
    }
    public Boolean pretragaOcena(Projekcija projekcija, ProjekcijaDTO ps){
        if(projekcija.getFilm().getProsecnaocena() > ps.getProsecnaocena()){
            return true;
        }
        return false;
    }

}
