$(document).ready(rdy());

$(document).on('click', '.detalji', function (){
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    //$('#novaProjekcija').text(" ");
    var bioskop_id = this.id.split(" ")[1];
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/menadzer_index_detalji/" + bioskop_id,
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Naziv</th>";
            row += "<th>Adresa</th>";
            row += "<th>Broj telefona</th>";
            row += "<th>Email</th>";
            $('#movies').append(row);
            var row1 = "<tr>";
            row1 += "<td>" + data[0]['naziv'] + "</td>";
            row1 += "<td>" + data[0]['adresa'] + "</td>";
            row1 += "<td>" + data[0]['br_telefona'] + "</td>";
            row1 += "<td>" + data[0]['email'] + "</td>";
            $('#movies').append(row1);

            var row = "<tr>";
            row += "<th>Id</th>";
            row += "<th>Naziv</th>";
            row += "<th>Kapacitet</th>";
            row += "<th>Naziv bioskopa</th>";
            row += "<th></th>";
            row += "<th></th>";
            row += "<th></th>";
            $('#moviesTable').append(row);
            for(var i = 1; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['id'] + "</td>";
                row1 += "<td>" + data[i]['naziv'] + "</td>";
                row1 += "<td>" + data[i]['kapacitet'] + "</td>";
                row1 += "<td>" + data[i]['bioskop_naziv'] + "</td>";
                var btnProjekcije = "<button class='projekcije' id = 'projekcije " + data[i]['id'] + "'>Projekcije</button>";
                row1 += "<td>" + btnProjekcije + "</td>";
                var btnIzmeni = "<button class='izmeni' id = 'izmeni " + data[i]['id'] + "'>Izmeni</button>";
                row1 += "<td>" + btnIzmeni + "</td>";
                var btnObrisi = "<button class='obrisi' id = 'obrisi " + data[i]['id'] + "'>Obriši</button>";
                row1 += "<td>" + btnObrisi + "</td>";
                $('#moviesTable').append(row1);
            }
            novaSalaButton(bioskop_id);
            $('#nazad').show();

        },
        error: function () {
            alert("Greška! (menadzer_index.js/onclick.detalji)");
        }
    });
});


$(document).on('click', '.nazad', function (){
    rdy();
});


$(document).on('click', '.projekcije', function (){
    $('#naslov').hide();
    var sala_id = this.id.split(" ")[1];
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    $('#novaSala').text(" ");
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/menadzer_index_detalji_sala_projekcije/" + sala_id,
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Id</th>";
            row += "<th>Naziv</th>";
            row += "<th>Kapacitet</th>";
            row += "<th>Naziv bioskopa</th>";
            $('#movies').append(row);
            var row1 = "<tr>";
            row1 += "<td>" + data[0]['id'] + "</td>";
            row1 += "<td>" + data[0]['naziv'] + "</td>";
            row1 += "<td>" + data[0]['kapacitet'] + "</td>";
            row1 += "<td>" + data[0]['bioskop_naziv'] + "</td>";
            $('#movies').append(row1);

            var row = "<tr>";
            row += "<th>Id</th>";
            row += "<th>Naziv</th>";
            row += "<th>Cena</th>";
            row += "<th>Datum i vreme</th>";
            row += "<th>Broj preostalih mesta</th>";
            row += "<th></th>";
            row += "<th></th>";
            $('#moviesTable').append(row);
            for(var i = 1; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['id'] + "</td>";
                row1 += "<td>" + data[i]['naziv'] + "</td>";
                row1 += "<td>" + data[i]['cena'] + "</td>";
                row1 += "<td>" + data[i]['datumvreme'] + "</td>";
                row1 += "<td>" + data[i]['broj_preostalih_mesta'] + "</td>";
                var btnIzmeni = "<button class='izmeniProjekciju' id = 'izmeniProjekciju " + data[i]['id'] + "'>Izmeni</button>";
                row1 += "<td>" + btnIzmeni + "</td>";
                var btnObrisi = "<button class='obrisiProjekciju' id = 'obrisiProjekciju " + data[i]['id'] + "'>Obriši</button>";
                row1 += "<td>" + btnObrisi + "</td>";
                $('#moviesTable').append(row1);
            }

            novaProjekcijaButton(data[0]['id']);
            $('#nazad').show();

        },
        error: function () {
            alert("Greška! (menadzer_index.js/onclick.detalji)");
        }
    });


});


$(document).on('click', '.izmeni', function (){
    var sala_id = parseInt(this.id.split(" ")[1]);
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    $('#nazad').hide();
    $('#naslov').hide();
    $('#novaSala').text(" ");

    var row = "<label for=\"id\">ID:</label>";
    row += "<input id=\"id\" value=" + sala_id + " readonly />";
    $('#izmenaSale').append(row);


    var row1 = "<input type=\"submit\" value=\"Izmeni salu\"/>"
    $('#izmenaSale').append(row1);

    $('#forma').show();

});

$(document).on('submit', '#izmenaSale', function (){
    var sala_id = $('#id').val();
    var naziv = $('#naziv').val();
    var kapacitet = $('#kapacitet').val();

    var salaDTO = formToJSON(sala_id, naziv, kapacitet);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/menadzer_index_detalji_sala_izmeni/" + sala_id,
        dataType: "json",
        contentType: "application/json",
        data: salaDTO,
        success: function (data){
            console.log("SUCCESS: ", data);
            window.location.reload(true);

        },
        error: function (){
            alert("Greška! (menadzer_index/detalji_sala_izmeni/" + sala_id);
        }
    });
});

$(document).on('click', '.obrisi', function (){
    var sala_id = this.id.split(" ")[1];
    if(confirm("Da li ste sigurni?")){
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/menadzer_index_detalji_sala_obrisi/" + sala_id,
            dataType: "json",
            success: function (data) {
                $('#movies').text(" ");
                $('#moviesTable').text(" ");
                $('#novaSala').text(" ");
                var bioskop_id = data[1]['id'];
                alert("Obrisao salu '" + data[0]['naziv'] + "' iz bioskopa " + data[1]['naziv'] + " sa ID: " + bioskop_id);
                $.ajax({
                    type: "GET",
                    url: "http://127.0.0.1:8080/menadzer_index_detalji/" + bioskop_id,
                    dataType: "json",
                    success: function (data) {
                        console.log("SUCCESS: ", data);

                        var row = "<tr>";
                        row += "<th>Naziv</th>";
                        row += "<th>Adresa</th>";
                        row += "<th>Broj telefona</th>";
                        row += "<th>Email</th>";
                        $('#movies').append(row);
                        var row1 = "<tr>";
                        row1 += "<td>" + data[0]['naziv'] + "</td>";
                        row1 += "<td>" + data[0]['adresa'] + "</td>";
                        row1 += "<td>" + data[0]['br_telefona'] + "</td>";
                        row1 += "<td>" + data[0]['email'] + "</td>";
                        $('#movies').append(row1);

                        var row = "<tr>";
                        row += "<th>Id</th>";
                        row += "<th>Naziv</th>";
                        row += "<th>Kapacitet</th>";
                        row += "<th>Naziv bioskopa</th>";
                        row += "<th></th>";
                        row += "<th></th>";
                        row += "<th></th>";
                        $('#moviesTable').append(row);
                        for (var i = 1; i < data.length; i++) {
                            var row1 = "<tr>";
                            row1 += "<td>" + data[i]['id'] + "</td>";
                            row1 += "<td>" + data[i]['naziv'] + "</td>";
                            row1 += "<td>" + data[i]['kapacitet'] + "</td>";
                            row1 += "<td>" + data[i]['bioskop_naziv'] + "</td>";
                            var btnProjekcije = "<button class='projekcije' id = 'projekcije " + data[i]['id'] + "'>Projekcije</button>";
                            row1 += "<td>" + btnProjekcije + "</td>";
                            var btnIzmeni = "<button class='izmeni' id = 'izmeni " + data[i]['id'] + "'>Izmeni</button>";
                            row1 += "<td>" + btnIzmeni + "</td>";
                            var btnObrisi = "<button class='obrisi' id = 'obrisi " + data[i]['id'] + "'>Obriši</button>";
                            row1 += "<td>" + btnObrisi + "</td>";
                            $('#moviesTable').append(row1);
                        }

                        novaSalaButton(data[0]['id']);
                        $('#nazad').show();

                    },
                    error: function () {
                        alert("Nikad me neces naci");
                    }
                });
            },
            error: function (){
                alert("Greška! (menadzer_index/menadzer_index_detalji_sala_obrisi/" + sala_id + ")");
            }
        });
    }
});

$(document).on('click', '.novaSala', function (){
    $('#naslov').hide();
    $('#novaSala').text(" ");
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    $('#nazad').hide();

    var bioskop_id = parseInt(this.id.split(" ")[1]);
    var row = "<label for=\"id\">ID:</label>";
    row += "<input id=\"id\" value=" + bioskop_id + " readonly />";
    $('#formaNovaSala').append(row);


    var row1 = "<input type=\"submit\" value=\"Unesi salu\"/>"
    $('#formaNovaSala').append(row1);

    $('#novaSalaDiv').show();

});

$(document).on('click', '.izmeniProjekciju', function (){
    var projekcija_id = parseInt(this.id.split(" ")[1]);
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    $('#nazad').hide();
    $('#naslov').hide();
    $('#novaSala').text(" ");


    var row = "<label for=\"idProjekcije\">ID:</label>";
    row += "<input id=\"idProjekcije\" value=" + projekcija_id + " readonly />";
    $('#formaIzmenaProjekcije').append(row);


    var row1 = "<input type=\"submit\" value=\"Izmeni projekciju\"/>"
    $('#formaIzmenaProjekcije').append(row1);

    $('#izmenaProjekcijeDiv').show();
});

$(document).on('submit', '#formaIzmenaProjekcije', function (){
    var projekcija_id = parseInt($('#idProjekcije').val());
    var cena = $('#novaCena').val();
    var datumvreme_format = $('#noviDatumvreme_format').val();

    var projekcijaDTO = projekcijaIzmenaFormToJSON(projekcija_id, cena, datumvreme_format);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/menadzer_index_detalji_projekcija_izmeni/",
        dataType: "json",
        contentType: "application/json",
        data: projekcijaDTO,
        success: function (data){
            console.log("SUCCESS: ", data);
            window.location.reload(true);

        },
        error: function (){
            alert("Greška! (menadzer_index/detalji_projekcija_izmeni/" + projekcija_id);
        }
    });
});

$(document).on('click', '.novaProjekcija', function (){
    $('#naslov').hide();
    $('#novaSala').text(" ");
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    $('#nazad').hide();

    var sala_id = parseInt(this.id.split(" ")[1]);
    var row = "<label for=\"id\">ID:</label>";
    row += "<input id=\"id\" value=" + sala_id + " readonly />";
    $('#formaNovaProjekcija').append(row);


    var row1 = "<input type=\"submit\" value=\"Unesi projekciju\"/>"
    $('#formaNovaProjekcija').append(row1);

    $('#novaProjekcijaDiv').show();
});

$(document).on('submit', '#formaNovaSala', function (){
    var naziv = $('#nazivSala').val();
    var kapacitet = $('#kapacitetSala').val();
    var bioskop_id = $('#id').val();
    var salaDTO = salaFormToJSON(naziv, kapacitet);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/menadzer_index_nova_sala/" + bioskop_id,
        dataType: "json",
        contentType: "application/json",
        data: salaDTO,
        success: function (data){
            console.log("SUCCESS: ", data);
            alert("Uspešno ste bioskopu " + data[0]['naziv'] + " dodali salu " + data[1]['naziv'] + " sa kapacitetom " + data[1]['kapacitet'] + ".");
            window.location.reload(true);
        },
        error: function (){
            alert("Greška! (menadzer_index/nova_sala/" );
        }
    });

});

$(document).on('submit', '#formaNovaProjekcija', function (){
    var naziv = $('#filmNaziv').val();
    var cena = $('#cena').val();
    var datumvreme_format = $('#datumvreme_format').val();
    var sala_id = $('#id').val();
    var projekcijaDTO = projekcijaFormToJSON(naziv, cena, datumvreme_format);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/menadzer_index_nova_projekcija/" + sala_id,
        dataType: "json",
        contentType: "application/json",
        data: projekcijaDTO,
        success: function (data){
            console.log("SUCCESS: ", data);
            alert("Uspešno ste sali " + data[0]['naziv'] + " dodali projekciju filma " + data[1]['naziv'] + " u " + data[1]['datumvreme'] + " po ceni od " + data[1]['cena'] + " dinara.");
            window.location.reload(true);
        },
        error: function (){
            alert("Greška! (menadzer_index/nova_sala/" );
        }
    });
});

$(document).on('click', '.obrisiProjekciju', function (){
    var projekcija_id = parseInt(this.id.split(" ")[1]);

    if(confirm("Da li ste sigurni?")) {
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/menadzer_index_detalji_projekcija_obrisi/" + projekcija_id,
            dataType: "json",
            success: function (data){
                console.log("SUCCESS: ", data);
                alert("Uspešno ste obrisali projekciju filma " + data[0]['naziv'] + " iz sale " + data[1]['naziv'] + ".");
                window.location.reload(true);
            }
        });
    }
});

function rdy(){
    $('#movies').text(" ");
    $('#moviesTable').text(" ");
    $('#nazad').hide();
    $('#naslov').show();
    $('#novaSala').text(" ");
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/menadzer_index",
        dataType: "json",
        success: function (data){
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Naziv</th>";
            row += "<th>Adresa</th>";
            row += "<th>Broj telefona</th>";
            row += "<th>Email</th>";
            row += "<th></th>";
            $('#movies').append(row);
            for(var i = 0; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['naziv'] + "</td>";
                row1 += "<td>" + data[i]['adresa'] + "</td>";
                row1 += "<td>" + data[i]['br_telefona'] + "</td>";
                row1 += "<td>" + data[i]['email'] + "</td>";
                var btnDetalji = "<button class='detalji' id = 'detalji " + data[i]['id'] + "'>Detalji</button>";
                row1 += "<td>" + btnDetalji + "</td>";
                $('#movies').append(row1);
            }
        },
        error: function (){
            alert("Greška! (menadzer_index.js/document.ready)");
        }
    });
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
            alert("Greška! (menadzer_index.js/odjava)");
        }
    });
}

function provera(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/index_provera",
        dataType: "json",
        success: function (data) {
            if(!data['ulogovan']) window.location.href = "index.html";
            else if(data['uloga'] !== "MENADZER") window.location.href = "no_access.html";
        },
        error: function (){
            alert("Greška! (menadzer_index.js/provera)");
        }
    });
}

function formToJSON(id, naziv, kapacitet) {
    return JSON.stringify({
        "id": id,
        "naziv": naziv,
        "kapacitet": kapacitet
    });
}

function salaFormToJSON(naziv, kapacitet) {
    return JSON.stringify({
        "naziv": naziv,
        "kapacitet": kapacitet
    });
}

function projekcijaFormToJSON(naziv, cena, datumvreme_format) {
    return JSON.stringify({
        "naziv": naziv,
        "cena": cena,
        "datumvreme_format": datumvreme_format
    });
}

function projekcijaIzmenaFormToJSON(id, cena, datumvreme_format){
    return JSON.stringify({
       "id": id,
       "cena": cena,
       "datumvreme_format": datumvreme_format
    });
}

function novaProjekcijaButton(sala_id){
    var row = "<br><br><button type=\"button\" id=\"salaID " + sala_id +"\" class=\"novaProjekcija\">Nova projekcija</button>";
    $('#novaSala').append(row);
}

function novaSalaButton(bioskop_id){
    var row = "<br><br><button type=\"button\" id=\"bioskopID " + bioskop_id +"\" class=\"novaSala\">Nova sala</button>";
    $('#novaSala').append(row);
}
