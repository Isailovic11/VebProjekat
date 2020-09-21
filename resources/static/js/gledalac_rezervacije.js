$(document).ready(function (){
    tables();
});

$(document).on('click', '.otkazi', function (){
    var karta_id = this.id.split(" ")[1];
    if(confirm("Da li ste sigurni?")){
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/gledalac_otkazi_rezervaciju/" + karta_id,
            dataType: "json",
            success: function (data){
                alert("Uspešno ste obrisali kartu za film \"" + data['film_naziv'] + "\" (" + data['projekcija'] + ")");
                tables();
            },
            error: function (){
                alert("Greška! (gledalac_rezervacije.js/otkazi_rezervaciju/" + data['id'] + ")");
            }
        });
    }
});

$(document).on('click', '.kupi', function (){
    var karta_id = this.id.split(" ")[1];
    if(confirm("Da li ste sigurno da želite da kupite izabranu kartu?")){
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/gledalac_kupi_kartu/" + karta_id,
            dataType: "json",
            success: function (data){
                alert("Uspešno ste kupili kartu za film \"" + data['film_naziv'] + "\".");
                tables();
            },
            error: function (){
                alert("Greška! (gledalac_rezervacije.js/kupi_kartu)")
            }
        });
    }
});

function tables(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/gledalac_get_rezervacije",
        dataType: "json",
        success: function (data){
            console.log("SUCCESS: ", data);
            $('#ticketsTableDiv > table > tbody').empty();
            for(var i = 0; i < data.length; i++){
                var row = "<tr>";
                row += "<td>" + data[i]['film_naziv'] + "</td>";
                row += "<td>" + data[i]['ukupna_cena'] + "</td>";
                row += "<td>" + data[i]['broj_mesta'] + "</td>";
                row += "<td>" + data[i]['projekcija'] + "</td>";
                row += "<td>" + data[i]['sala'] + "</td>";
                row += "<td>" + data[i]['bioskop'] + "</td>";
                var btnOtkazi = "<button class='otkazi' id='otkazi " + data[i]['id'] + "'>Otkaži</button>";
                var btnKupi = "<button class='kupi' id='kupi " + data[i]['id'] + "'>Kupi</button>";
                row += "<td>" + btnOtkazi + "</td>";
                row += "<td>" + btnKupi + "</td>";
                row += "</tr>";
                $('#ticketsTableDiv > table > tbody').append(row);
            }

        },
        error: function (){
            alert("Greška! (gledalac_rezervacije/ready)");
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
            alert("Greška! (admin_index.js/odjava)");
        }
    });
}