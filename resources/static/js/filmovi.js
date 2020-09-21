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
                row2 += "<td>" + data[i]['prosecnaocena'] + "</td>";
                row2 += "<td>" + data[i]['cena'] + "</td>";
                row2 += "<td>" + data[i]['datumvreme'] + "</td>";
                row2 += "<td>" + data[i]['sala'] + "</td>";
                row2 += "<td>" + data[i]['broj_preostalih_mesta'] + "</td>";
                row2 += "<td>" + data[i]['naziv_bioskopa'] + "</td>";
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
                row += "<td>" + data[i]['prosecnaocena'] + "</td>";
                row += "<td>" + data[i]['cena'] + "</td>";
                row += "<td>" + data[i]['datumvreme'] + "</td>";
                row += "<td>" + data[i]['sala'] + "</td>";
                row += "<td>" + data[i]['broj_preostalih_mesta'] + "</td>";
                row += "<td>" + data[i]['naziv_bioskopa'] + "</td>";
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

