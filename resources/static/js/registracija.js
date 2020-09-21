$(document).on("submit", "form", function (event){
    event.preventDefault();

    var ime = $('#ime').val();
    var prezime = $('#prezime').val();
    var lozinka = $('#lozinka').val();
    var korisnickoime = $('#korisnickoime').val();
    var email = $('#email').val();
    var konktakt_tel = $('#kontakt_tel').val();
    var uloga = $('#uloga').val();

    var noviKorisnik = formToJSON(ime, prezime, lozinka, korisnickoime, email, konktakt_tel, uloga);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/registracija",
        dataType: "json",
        contentType: "application/json",
        data: noviKorisnik,
        success: function (data) {
            console.log("SUCCESS: ", data);
            if(data['uloga']==1 || data['uloga']==2) window.location.href = "ceka_odobrenje.html";
            else {
                alert(ime + " " + prezime + " je uspešno kreiran kao gledalac.");
                window.location.href = "index.html";
            }
        },
        error: function () {
            alert("Greška!");
        }
    });
});

function formToJSON(ime, prezime, lozinka, korisnickoime, email, kontakt_tel, uloga) {
    return JSON.stringify({
        "ime": ime,
        "prezime": prezime,
        "lozinka": lozinka,
        "korisnickoime": korisnickoime,
        "email": email,
        "kontakt_tel": kontakt_tel,
        "uloga": uloga
    });
}