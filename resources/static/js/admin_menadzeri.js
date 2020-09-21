$(document).ready(function (){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_menadzeri",
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Ime</th>";
            row += "<th>Prezime</th>";
            row += "<th>Korisničko ime</th>";
            row += "<th>Broj bioskopa</th>";
            row += "<th></th>";
            row += "<th></th>";
            $('#movies').append(row);
            //alert("Broj ljudi na cekanju za approve: " + data.length);
            for(var i = 0; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['ime'] + "</td>";
                row1 += "<td>" + data[i]['prezime'] + "</td>";
                row1 += "<td>" + data[i]['korisnickoime'] + "</td>";
                row1 += "<td>" + data[i]['br_bioskopa'] + "</td>";
                var btnObrisi = "<button class='obrisi' id = 'obrisi " + data[i]['id'] + "'>Obriši</button>";
                row1 += "<td>" + btnObrisi + "</td>";
                var btnDetalji = "<button class='detalji' id = 'detalji " + data[i]['id'] + "'>Detalji</button>";
                row1 += "<td>" + btnDetalji + "</td>";
                $('#movies').append(row1);
            }
        },
        error: function(){
            alert("Greška! (admin_menadzer.js doc ready)");

        }
    });
});

$(document).on('click', '.obrisi', function (){
    if(confirm("Da li ste sigurni?")){
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/admin_menadzeri_obrisi/" + this.id.split(" ")[1],
            dataType: "json",
            success: function (data){
                console.log("SUCCESS: ", data);
                if(data['obrisan']){
                    alert("Uspešno obrisan menadžer " + data['korisnickoime']);
                    window.location.reload(true);
                } else alert("Izabrani menadžer je poslednji u nekom od bioskopa i ne možete ga obrisati.");
            },
            error: function (){
                alert("Greška! (admin_bioskopi/obrisi)");
            }
        })
    }
});

$(document).on('click', '.detalji', function (){
    var id = this.id.split(" ")[1];
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_menadzeri_detalji/" + id,
        dataType: "json",
        success: function (data){
            console.log("SUCCESS: ", data);
            $('#movies').hide();
            $('#mngr').hide();
            var row = "<tr>";
            row += "<th>Ime</th>";
            row += "<th>Prezime</th>";
            row += "<th>Korisničko ime</th>";
            row += "<th>Broj bioskopa</th>";
            $('#mngrTable').append(row);
            var row1 = "<tr>";
            row1 += "<td>" + data[0]['ime'] + "</td>";
            row1 += "<td>" + data[0]['prezime'] + "</td>";
            row1 += "<td>" + data[0]['korisnickoime'] + "</td>";
            row1 += "<td>" + data[0]['br_bioskopa'] + "</td>";
            $('#mngrTable').append(row1);

            var row2 = "<tr>";
            row2 += "<th>Naziv</th>";
            row2 += "<th>Adresa</th>";
            row2 += "<th>Broj telefona</th>";
            row2 += "<th>Email</th>";
            row2 += "<th></th>";
            $('#moviesTable').append(row2);

            for(var i = 1; i < data.length; i++){
                var row3 = "<tr>";
                row3 += "<td>" + data[i]['naziv'] + "</td>";
                row3 += "<td>" + data[i]['adresa'] + "</td>";
                row3 += "<td>" + data[i]['br_telefona'] + "</td>";
                row3 += "<td>" + data[i]['email'] + "</td>";
                var btnUkloni = "<button class='ukloni' id = 'ukloni " + data[i]['id'] + " " + id + "'>Ukloni</button>";
                row3 += "<td>" + btnUkloni + "</td>";
                $('#moviesTable').append(row3);
            }


        },
        error: function (){
            alert("Greška! (admin_bioskopi/obrisi)");
        }
    })
});


$(document).on('click', '.ukloni', function (){
    var bioskop_id = this.id.split(" ")[1];
    var mngr_id = this.id.split(" ")[2];
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_bioskop_ukloni_mngr/" + bioskop_id + "/" + mngr_id,
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            if(data[0]['obrisan']){
                alert("Izabrani menadžer je poslednji u bioskopu " + data[1]['naziv'] + " i ne možete ga obrisati.")
            } else {
                alert("Uklonjen menadžer " + data[0]['korisnickoime'] + " iz bioskopa " + data[1]['naziv']);
                window.location.reload(true);
            }
        },
        error: function (data){
            alert("Greška! (admin_bioskopi/admin_bioskop_ukloni_mngr)")
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
        success: function (data) {a
            alert(data['uloga']);
            if(!data['ulogovan']) window.location.href = "index.html";
            else if(data['uloga'] !== "ADMIN") window.location.href = "no_access.html";
        },
        error: function (){
            alert("Greška! (admin_index.js/provera)");
        }
    });
}