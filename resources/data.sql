INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Gledalac', 1, 'ass', 'Marko', 'Markovic', '244444', 'markovic@gmail.com', '2020-05-07', 0, true, 'qwer', true);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Gledalac', 2, 'pass', 'Ivana', 'Ivanovic', '232714', 'ivanovic@gmail.com', '2020-06-17', 0, false, 'ivana', true);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Menadzer', 3, 'ass', 'Jovan', 'Jovanovic', '555333', 'veliki@gmail.com', '2020-06-17', 1, true, 've', true);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Gledalac', 4, 'pedja', 'Predrag', 'Despotovic', '245564', 'despotovicpedja@gmail.com', '2020-06-18', 0, true, 'Pedja', true);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Admin', 5, 'adminpass', 'Admin', 'Adminovic', 'XXXXXX', 'admin@bioskopi.rs', '0001-01-01', 2, true, 'SuperAdmin', true);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Menadzer', 6, 'pass', 'Nikola', 'Nikolic', '564212', 'nikola@bioskopi.rs', '2020-06-19', 1, true, 'nikola_mngr', false);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Menadzer', 7, 'pass', 'Darko', 'Darkovic', '564212', 'darko@bioskopi.rs', '2020-06-19', 1, true, 'darko_mngr', true);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Menadzer', 8, 'pass', 'Zivko', 'Zivkovic', '564212', 'zivko@bioskopi.rs', '2020-06-19', 1, true, 'zivko_mngr', false);
INSERT INTO Korisnik(type, id, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, korisnickoime, approved) VALUES ('Gledalac', 9, 'pass', 'Milan', 'Obradovic', '513223', 'obradovic@gmail.com', '2020-06-18', 0, true, 'milance', true);


INSERT INTO Bioskop(id, naziv, adresa, br_telefona, email) VALUES (1, 'Bioskop1', 'Mihajla Pupina 20', '455000', 'bioskop1@bioskopi.rs');
INSERT INTO Bioskop(id, naziv, adresa, br_telefona, email) VALUES (2, 'Bioskop2', 'Radnicka 12', '4785211', 'bioskop2@bioskopi.rs');
INSERT INTO Bioskop(id, naziv, adresa, br_telefona, email) VALUES (3, 'Bioskop3', 'Jovana Subotica 7', '685524', 'bioskop3@bioskopi.rs');


INSERT INTO Sala(id, naziv, kapacitet, bioskop_id) VALUES (1, 'mala sala', 60, 1);
INSERT INTO Sala(id, naziv, kapacitet, bioskop_id) VALUES (2, 'velika sala', 250, 1);
INSERT INTO Sala(id, naziv, kapacitet, bioskop_id) VALUES (3, 'srednja sala', 150, 2);
INSERT INTO Sala(id, naziv, kapacitet, bioskop_id) VALUES (4, 'mala sala', 60, 2);
INSERT INTO Sala(id, naziv, kapacitet, bioskop_id) VALUES (5, 'srednja sala', 150, 3);
INSERT INTO Sala(id, naziv, kapacitet, bioskop_id) VALUES (6, 'velika sala', 250, 3);


INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (1, 'Lajanje na zvezde', 'oppis', 'komedija', 95, 8.5, 10, 85);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (2, 'Pljačka trećeg rajha', '2 ludaka', 'komedija', 105, 9, 8, 72);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (3, 'Kad porastem biću kengur', 'kengur', 'komedija', 99, 8.6, 10, 86);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (4, 'Balkanska međa', 'kengur', 'triler', 151, 8.1, 10, 81);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (5, 'Balkanski špijun', 'opis', 'drama', 99, 9, 11, 99);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (6, 'Ko to tamo peva', 'opis', 'komedija', 97, 8.9, 10, 89);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (7, 'Varljivo leto 68.', 'opis', 'komedija', 91, 8.6, 10, 86);
INSERT INTO Film(id, naziv, opis, zanr, trajanje, prosecnaocena, brojocenjivaca, zbirocena) VALUES (8, 'Mi nismo anđeli', 'opis', 'komedija', 98, 8.4, 10, 84);

INSERT INTO Odgledan_Film(id, film_id, gledalac_id, ocenjen) VALUES (1, 1, 1, false);
INSERT INTO Odgledan_Film(id, film_id, gledalac_id, ocenjen) VALUES (2, 2, 1, false);
INSERT INTO Odgledan_Film(id, film_id, gledalac_id, ocenjen, ocena) VALUES (3, 3, 1, true, 8);
INSERT INTO Odgledan_Film(id, film_id, gledalac_id, ocenjen, ocena) VALUES (4, 4, 1, true, 9);
INSERT INTO Odgledan_Film(id, film_id, gledalac_id, ocenjen, ocena) VALUES (5, 5, 1, true, 9);

INSERT INTO korisnik_bioskopi(menadzeri_id, bioskopi_id) VALUES (3, 1);

INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (1, 250, '2020-7-25 15:00', 1);
INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (1, 250, '2020-7-25 16:00', 2);
INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (3, 350, '2020-7-26 15:00', 3);
INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (4, 450, '2020-7-26 17:00', 4);
INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (5, 250, '2020-7-26 15:00', 5);
INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (6, 250, '2020-7-25 16:00', 6);
INSERT INTO Projekcija(film_id, cena, datumvreme, sala_id) VALUES (7, 350, '2020-7-25 12:00', 6);

INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (1, 7, 1, 1);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (2, 3, 2, 2);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (3, 4, 2, 4);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (4, 2, 3, 2);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (5, 5, 4, 1);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (6, 2, 5, 9);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (7, 6, 6, 2);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (8, 1, 6, 4);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (9, 3, 7, 4);
INSERT INTO Karta(id, broj_mesta, projekcija_id, gledalac_id) VALUES (10, 4, 7, 1);

