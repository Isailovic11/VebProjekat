package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Korisnik;
import com.example.vebprojekat.entity.Uloga;

public class LoginDTO {
    private String korisnickoime;
    private String lozinka;
    private Uloga uloga;
    private Boolean login;
    private Boolean approved;
    private Boolean aktivan;

    public LoginDTO(){
        this.login = false;
    }

    public LoginDTO(String korisnickoime, String lozinka, Uloga uloga, Boolean approved, Boolean aktivan) {
        this.korisnickoime = korisnickoime;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.login = false;
        this.approved = approved;
        this.aktivan = aktivan;
    }

    public LoginDTO(Korisnik korisnik){
        this.korisnickoime = korisnik.getKorisnickoime();
        this.lozinka = korisnik.getLozinka();
        this.uloga = korisnik.getUloga();
        this.login = true;
        this.approved = korisnik.getApproved();
        this.aktivan = korisnik.getAktivan();
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }
}
