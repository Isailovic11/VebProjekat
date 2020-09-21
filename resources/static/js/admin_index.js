$(document).ready(function (){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_index",
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Uloga</th>";
            row += "<th>Ime</th>";
            row += "<th>Prezime</th>";
            row += "<th>Korisničko ime</th>";
            row += "<th></th>";
            $('#movies').append(row);
            //alert("Broj ljudi na cekanju za approve: " + data.length);
            for(var i = 0; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['uloga'] + "</td>";
                row1 += "<td>" + data[i]['ime'] + "</td>";
                row1 += "<td>" + data[i]['prezime'] + "</td>";
                row1 += "<td>" + data[i]['korisnickoime'] + "</td>";
                var btn = "<button class='odobri' id = " + data[i]['id'] + ">Odobri registraciju</button>";
                row1 += "<td>" + btn + "</td>";
                $('#movies').append(row1);
            }
        },
        error: function(){
            alert("Greška! (admin_index.js doc ready)");
            window.location.href = "error.html";
        }
    });
});

$(document).on('click', '.odobri', function (event){
    event.preventDefault();
    $('#movies').text(" ");
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/admin_odobri_registraciju/" + this.id,
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            var row = "<tr>";
            row += "<th>Uloga</th>";
            row += "<th>Ime</th>";
            row += "<th>Prezime</th>";
            row += "<th>Korisničko ime</th>";
            row += "<th></th>";
            $('#movies').append(row);
            //alert("Broj ljudi na cekanju za approve: " + data.length);
            for(var i = 0; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['uloga'] + "</td>";
                row1 += "<td>" + data[i]['ime'] + "</td>";
                row1 += "<td>" + data[i]['prezime'] + "</td>";
                row1 += "<td>" + data[i]['korisnickoime'] + "</td>";
                var btn = "<button class='odobri' id = " + data[i]['id'] + ">Odobri registraciju</button>";
                row1 += "<td>" + btn + "</td>";
                $('#movies').append(row1);
            }
        },
        error: function () {
            alert("Greška! admin_index.js/onclick odobri");
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
            if(!data['ulogovan']) window.location.href = "index.html";
            else if(data['uloga'] !== "ADMIN") window.location.href = "no_access.html";
        },
        error: function (){
            alert("Greška! (admin_index.js/provera)");
        }
    });
}