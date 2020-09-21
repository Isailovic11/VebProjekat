package com.example.vebprojekat.service;

import com.example.vebprojekat.entity.Admin;
import com.example.vebprojekat.entity.Korisnik;

import java.util.List;

public interface AdminService {
    Admin create(Korisnik korisnik) throws Exception;

    Admin findOne(Long id);

    Admin update(Admin admin) throws Exception;

    void delete(Long id);

    List<Admin> findAll();

    Admin findByUsername(String username);

    void addToApprovalList(Korisnik korisnik) throws Exception;

    Long approveRegistration(Korisnik korisnik) throws Exception;

    // void deleteIfMoreThanOne(Admin korisnik);
}
