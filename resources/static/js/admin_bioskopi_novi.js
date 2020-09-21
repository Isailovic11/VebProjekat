$(document).ready(function (){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_bioskop_izmeni_priprema",
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            for(var i = 0; i < data.length; i++){
                //alert("MenadzerID: " + data[i]['id']);
                var row = '<option value="' + data[i]['id'] + '">' + data[i]['korisnickoime'] + '</option>';
                $('#menadzer').append(row);
            }
        },
        error: function(){
            alert("Greška! (admin_index.js/admin_bioskop_novi_priprema)");
        }
    });
});

$(document).on('submit', 'form', function (){
    var naziv = $('#naziv').val();
    var adresa = $('#adresa').val();
    var br_telefona = $('#br_telefona').val();
    var email = $('#email').val();
    var inicijalni_menadzer = $('#menadzer option:selected').text();
    var bioskopDTO = formToJSON(naziv, adresa, br_telefona, email, inicijalni_menadzer);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/admin_bioskop_novi",
        dataType: "json",
        contentType: "application/json",
        data: bioskopDTO,
        success: function(data){
            alert("Usao u success");
            console.log("SUCCESS: ", data);
            alert("Uspešno dodat bioskop: " + data['naziv'] + ". Njegov ID: " + data['id']);
            window.location.href = "admin_bioskopi.html";
        },
        error: function (){
            alert("Greška! (admin_index.js/admin_bioskop_novi)");
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

function formToJSON( naziv, adresa, br_telefona, email, inicijalni_menadzer) {
    return JSON.stringify({
        "naziv": naziv,
        "adresa": adresa,
        "br_telefona": br_telefona,
        "email": email,
        "inicijalni_menadzer": inicijalni_menadzer
    });
}