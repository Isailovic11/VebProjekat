$(document).ready(function(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_bioskopi",
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Naziv</th>";
            row += "<th>Adresa</th>";
            row += "<th>Broj telefona</th>";
            row += "<th>Email</th>";
            row += "<th></th>";
            row += "<th></th>";
            row += "<th></th>";
            $('#movies').append(row);
            //alert("Broj ljudi na cekanju za approve: " + data.length);
            for(var i = 0; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['naziv'] + "</td>";
                row1 += "<td>" + data[i]['adresa'] + "</td>";
                row1 += "<td>" + data[i]['br_telefona'] + "</td>";
                row1 += "<td>" + data[i]['email'] + "</td>";
                var btnIzmeni = "<button class='izmeni' id = 'izmeni " + data[i]['id'] + "'>Izmeni</button>";
                row1 += "<td>" + btnIzmeni + "</td>";
                var btnObrisi = "<button class='obrisi' id = 'obrisi " + data[i]['id'] + "'>Obriši</button>";
                row1 += "<td>" + btnObrisi + "</td>";
                var btnDodajMngr = "<button class='dodajMngr' id = 'dodajMngr " + data[i]['id'] + "'>Dodaj menadžera</button>";
                row1 += "<td>" + btnDodajMngr + "</td>";
                $('#movies').append(row1);
            }
        },
        error: function(){
            alert("Greška! (admin_bioskopi.js doc ready)");
            window.location.href = "error.html";
        }
    });
});

$(document).on('click', '.obrisi', function (){
    if(confirm("Da li ste sigurni?")){
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/admin_bioskop_obrisi/" + this.id.split(" ")[1],
            dataType: "json",
            success: function (data){
                console.log("SUCCESS: ", data);
                window.location.reload(true);
            },
            error: function (){
                alert("Greška! (admin_bioskopi/obrisi)")
            }
        })
    }
});


$(document).on('click', '.izmeni', function (){
    $('#movies').hide();
    $('#naslov').hide();
    //$('#novi_bioskop').hide();
    //alert("ID pre splita: " + this.id);
    //alert("ID posle splita: " + this.id.split(" ")[1]);
    var id = parseInt(this.id.split(" ")[1]);
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_bioskop_izmeni_priprema/",
        dataType: "json",
        success: function (data){
            console.log("SUCCESS: ", data);
            for(var i = 0; i < data.length; i++){
                //alert("MenadzerID: " + data[i]['id']);
                var row = '<option value="' + data[i]['id'] + '">' + data[i]['korisnickoime'] + '</option>';
                $('#menadzer').append(row);
            }
            var row2 = "<label for=\"id\">ID:</label>";
            row2 += "<input id=\"id\" value=" + id + " readonly />";
            $('#izmeni_bioskop').append(row2);
            var row1 = "<input type=\"submit\" id = " + id + " value=\"Unesi izmenu\">";
            $('#izmeni_bioskop').append(row1);
            $('#izmeni_bioskop_form').show();

        },
        error: function (){
            alert("Greška! (admin_bioskopi/izmeni_priprema)")
        }
    })
});

$(document).on('submit', 'form', function (event){
    event.preventDefault();
    var naziv = $('#naziv').val();
    var adresa = $('#adresa').val();
    var br_telefona = $('#br_telefona').val();
    var email = $('#email').val();
    var id = $('#id').val();

    var noviBioskop = formToJSON(id, naziv, adresa, br_telefona, email);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/admin_bioskop_izmeni/",
        dataType: "json",
        contentType: "application/json",
        data: noviBioskop,
        success: function (data){
            console.log("SUCCESS: ", data);
            window.location.reload(true);

        },
        error: function (){
            alert("Greška! (admin_bioskopi/izmeni)");
        }
    })
});


$(document).on('click', '.dodajMngr', function (){
    var id = parseInt(this.id.split(" ")[1]);
    $('#movies').hide();
    $('#naslov').hide();
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_bioskop_dodaj_mngr_priprema/" + id,
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Ime</th>";
            row += "<th>Prezime</th>";
            row += "<th>Korisničko ime</th>";
            row += "<th></th>";
            $('#moviesTable').append(row);
            var row1 = "<tr>";
            for (var i = 0; i < data.length; i++) {
                row1 += "<td>" + data[i]['ime'] + "</td>";
                row1 += "<td>" + data[i]['prezime'] + "</td>";
                row1 += "<td>" + data[i]['korisnickoime'] + "</td>";
                var btnDodajMngr = "<button class='dodajMngrBioskopu' id = 'dodajMngr " + data[i]['id'] + " " + id + "'>Dodaj</button>";
                row1 += "<td>" + btnDodajMngr + "</td>";
                row1 += "</tr>";
            }
            $('#moviesTable').append(row1);
            $('#moviesTable').show();
        },
        error: function (){
            alert("Greška! (admin_bioskopi/dodaj_mngr_priprema)")
        }
    });
});

$(document).on('click', '.dodajMngrBioskopu', function (){
    var mngr_id = parseInt(this.id.split(" ")[1]);
    var bioskop_id = parseInt(this.id.split(" ")[2]);
    //alert("Menadzer ID: " + mngr_id);
    //alert("Bioskop ID: " + bioskop_id);
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_bioskop_dodaj_mngr/" + bioskop_id + "/" + mngr_id,
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            window.location.reload(true);
        },
        error: function (data){
            alert("Greška! (admin_bioskopi/dodaj_mngr_priprema)")
        }
    });

});

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

function provera(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/index_provera",
        dataType: "json",
        success: function (data) {
            alert(data['uloga']);
            if(!data['ulogovan']) window.location.href = "index.html";
            else if(data['uloga'] !== "ADMIN") window.location.href = "no_access.html";
        },
        error: function (){
            alert("Greška! (admin_index.js/provera)");
        }
    });
}

function formToJSON(id, naziv, adresa, br_telefona, email) {
    return JSON.stringify({
        "id": id,
        "naziv": naziv,
        "adresa": adresa,
        "br_telefona": br_telefona,
        "email": email
    });
}