package com.example.vebprojekat.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Korisnik {


    @OneToMany(mappedBy = "admin_approve", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Korisnik> approvalList;

    public Admin(){}

    public Admin(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTel, String email, Date datum, Uloga uloga, Boolean aktivan, Admin admin_approve, Boolean approved) {
        super(id, korisnickoIme, lozinka, ime, prezime, kontaktTel, email, datum, uloga, aktivan, admin_approve, approved);
    }

    public List<Korisnik> getApprovalList() {
        return approvalList;
    }

    public void setApprovalList(List<Korisnik> approvalList) {
        this.approvalList = approvalList;
    }
}
