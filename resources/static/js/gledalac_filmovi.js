$(document).ready(function() {
    $('#oneFilmDiv').hide();
    $('#searchResDiv').hide();
    $('#sortirajDiv').hide();
    $('#pretragaFilmovaDiv').show();
    $('#moviesDiv').show();

    var row1 = "<tr>";
    row1 += "<th>Naziv</th>";
    row1 += "<th>Opis</th>";
    row1 += "<th>Žanr</th>";
    row1 += "<th>Trajanje</th>";
    row1 += "<th>Prosečna ocena</th>";
    row1 += "<th></th>";
    row1 += "</tr>";
    $('#moviesTable > thead').append(row1);

    moviesTable();

});

// PROJEKCIJE ZA FILM
$(document).on('click', '.projekcije', function (){
    var id = this.id.split(" ")[1];

    $('#pretragaFilmovaDiv').hide();
    $('#moviesDiv').hide();
    $('#searchResDiv').hide();
    $('#sortirajDiv').show();
    $('#btnNazadDiv').show()
    $('#oneFilmDiv').show();

    movieProjections(parseInt(id));

});

// POVRATAK NA POČETAK
$(document).on('click', '.btnBack', function (){
    $('#oneFilmDiv').hide();
    $('#searchResDiv').hide();
    $('#sortirajDiv').hide();
    $('#pretragaFilmovaDiv').show();
    $('#moviesDiv').show();
});

// PREDAJA FORME PRETRAGE
$(document).on('submit', '#pretragaFilmova', function (event){
    event.preventDefault();

    var naziv = $('#naziv').val();
    $('#naziv').val("");
    var opis = $('#opis').val();
    $('#opis').val("");
    var zanr = $('#zanr').val();
    $('#zanr').val("");
    var cena = $('#cena').val();
    $('#cena').val("");
    var prosecnaocena = $('#prosecnaocena').val();
    $('#prosecnaocena').val("");
    var datumvreme_format = $('#datumvreme_format').val();
    $('#datumvreme_format').val("");
    var filmDTO = formToJSON(naziv, opis, zanr, cena, prosecnaocena, datumvreme_format);

    submitSearchForm(filmDTO);

    $('#pretragaFilmovaDiv').hide();
    $('#moviesDiv').hide();
    $('#sortirajDiv').show();
    $('#searchResDiv').show();

});

// PREDAJA FORME SORTIRANJA
$(document).on('submit', '#sortirajForma', function (event){
    event.preventDefault();
    var sort = $('#sortSelect :selected').val();
    sortCall(sort);

});

// REZERVACIJA KARATA
$(document).on('click', '.rezervisi', function (){
    var projection_id = this.id.split(" ")[1];
    var broj_preostalih = this.id.split(" ")[2];
    var broj_karata = prompt("Koliko karata želite da rezervišete?", "0");
    if(broj_karata !== "0"){
        if(broj_preostalih < parseInt(broj_karata)){
            alert("Nažalost, nemamo toliko slobodnih karata za datu projekciju.");
        } else {
            reserveProjection(projection_id, broj_karata);
        }
    } else {
        alert("Molimo Vas unesite broj različit od 0.");
    }
});

// POZIV SVIH FILMOVA IZ BAZE
function moviesTable(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/get_filmovi",
        dataType: "json",
        success: function (data) {
            for(var i = 0; i < data.length; i++){
                var row2 = "<tr>";
                row2 += "<td>" + data[i]['naziv'] + "</td>";
                row2 += "<td>" + data[i]['opis'] + "</td>";
                row2 += "<td>" + data[i]['zanr'] + "</td>";
                row2 += "<td>" + data[i]['trajanje'] + "</td>";
                row2 += "<td>" + data[i]['prosecnaocena'] + "</td>";
                var btnProjekcije = "<button class=\"projekcije\" id=\"projekcije " + data[i]['id'] + "\">Projekcije</button>";
                row2 += "<td>" + btnProjekcije + "</td>";
                row2 += "</tr>";

                $('#moviesTable > tbody').append(row2);
            }
        },
        error: function(){
            alert("Greška! (filmovi.js/doc_ready)");
        }
    });
}

// POZIV PROJEKCIJA ZA DATI FILM IZ BAZE
function movieProjections(id){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/get_projekcije/" + id,
        dataType: "JSON",
        success: function (data){
            $('#oneFilmTable > tbody').text("");

            for(var i = 0; i < data.length; i++){
                var row2 = "<tr>";
                row2 += "<td>" + data[i]['naziv'] + "</td>";
                row2 += "<td>" + data[i]['trajanje'] + "</td>";
                row2 += "<td>" + data[i]['cena'] + "</td>";
                row2 += "<td>" + data[i]['datumvreme'] + "</td>";
                row2 += "<td>" + data[i]['sala'] + "</td>";
                row2 += "<td>" + data[i]['broj_preostalih_mesta'] + "</td>";
                row2 += "<td>" + data[i]['naziv_bioskopa'] + "</td>";
                var btnRezervisi = "<button class='rezervisi' id='rezervisi " + data[i]['id'] + " " + data[i]['broj_preostalih_mesta'] + "'>Rezerviši</button>";
                row2 += "<td>" + btnRezervisi + "</td>";
                row2 += "</tr>";
                $('#oneFilmTable > tbody').append(row2);
            }
        },
        error: function (){
            alert("Greška! (filmovi.js/get_projekcije " + id + ")");
        }
    });

}

// SLANJE FILMDTO ZA PRETRAGU I REZULTATI PRETRAGE
function submitSearchForm(filmDTO){
    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/pretraga",
        dataType: "json",
        contentType: "application/json",
        data: filmDTO,
        success: function (data){
            $('#searchResTable > tbody').empty();
            for(var i = 0; i < data.length; i++){
                var row = "<tr>";
                row += "<td>" + data[i]['naziv'] + "</td>";
                row += "<td>" + data[i]['trajanje'] + "</td>";
                row += "<td>" + data[i]['cena'] + "</td>";
                row += "<td>" + data[i]['datumvreme'] + "</td>";
                row += "<td>" + data[i]['sala'] + "</td>";
                row += "<td>" + data[i]['broj_preostalih_mesta'] + "</td>";
                row += "<td>" + data[i]['naziv_bioskopa'] + "</td>";
                var btnRezervisi = "<button class='rezervisi' id='rezervisi " + data[i]['id'] + " " + data[i]['broj_preostalih_mesta'] + "'>Rezerviši</button>";
                row += "<td>" + btnRezervisi + "</td>";
                $('#searchResTable > tbody').append(row);
            }
        },
        error: function (){
            alert("Greška! (filmovi.js/pretraga)");
        }
    });
}

// SLANJE TIPA SORTIRANJA I ISPIS SORTIRANIH PROJEKCIJA
function sortCall(sort){
    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/sortiranje",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(sort),
        success: function (data){
            $('#searchResTable > tbody').empty();
            for(var i = 0; i < data.length; i++){
                var row = "<tr>";
                row += "<td>" + data[i]['naziv'] + "</td>";
                row += "<td>" + data[i]['trajanje'] + "</td>";
                row += "<td>" + data[i]['prosecnaocena'] + "</td>";
                row += "<td>" + data[i]['cena'] + "</td>";
                row += "<td>" + data[i]['datumvreme'] + "</td>";
                row += "<td>" + data[i]['sala'] + "</td>";
                row += "<td>" + data[i]['broj_preostalih_mesta'] + "</td>";
                row += "<td>" + data[i]['naziv_bioskopa'] + "</td>";
                row += "</tr>";
                $('#searchResTable > tbody').append(row);
            }
        },
        error: function (){
            alert("Greška! (filmovi.js/sortiranje)");
        }
    });
}

// SLANJE REZERVACIJE
function reserveProjection(projection_id, broj_karata){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/gledalac_rezervisi/" + projection_id + "/" + broj_karata,
        dataType: "json",
        success: function (data){
            var broj_karata = data[1];
            if((broj_karata % 10) === 1){
                alert("Uspešno ste rezervisali 1 kartu za projekciju filma " + data[0]['naziv'] + " (" + data[0]['datumvreme'] + ").");
            } else if ((broj_karata % 10) < 5){
                alert("Uspešno ste rezervisali " + broj_karata + " karte za projekciju filma " + data[0]['naziv'] + " (" + data[0]['datumvreme'] + ").");
            } else {
                alert("Uspešno ste rezervisali " + broj_karata + " karata za projekciju filma " + data[0]['naziv'] + " (" + data[0]['datumvreme'] + ").");
            }
            window.location.href = "gledalac_rezervacije.html";
        },
        error: function (){
            alert("Greška! (filmovi.js/gledalac_rezervisi/" + projection_id + "/" + broj_karata);
        }

    });
}

function formToJSON(naziv, opis, zanr, cena, prosecnaocena, datumvreme_format){
    return JSON.stringify({
        "naziv": naziv,
        "opis": opis,
        "zanr": zanr,
        "cena": cena,
        "prosecnaocena": prosecnaocena,
        "datumvreme_format": datumvreme_format
    })
}

function odjava(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/odjava",
        dataType: "json",
        success: function () {
            window.location.href = "index.html";
        },
        error: function(){
            alert("Greška! (admin_index.js/odjava)");
        }
    });
}