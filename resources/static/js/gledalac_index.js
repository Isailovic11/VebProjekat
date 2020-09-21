$(document).ready(function (){
    $('#userTableDiv').show();
    $('#watchedMoviesDiv').show();
    $('#gradedMoviesDiv').show();
    tables();
});

$(document).on('submit', '#ocenaForm', function (event){
    event.preventDefault();
    var grade = $('#ocenaForm :selected').val();
    var movie_id = $('#ocenaForm :selected').attr('id').split(" ")[1];
    gradeSend(movie_id, grade);
    tablesUpdate();
});

function tables(){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/gledalac_index",
        dataType: "json",
        success: function (data){
            var row = "<tr>";
            row += "<td>" + data[0]['ime'] + "</td>";
            row += "<td>" + data[0]['prezime'] + "</td>";
            row += "<td>" + data[0]['korisnickoime'] + "</td>";
            row += "<td>" + data[0]['email'] + "</td>";
            row += "<td>" + data[0]['broj_odgledanih'] + "</td>";
            $('#userTableDiv > table > tbody').append(row);

            for(var i = 1; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['naziv_filma'] + "</td>";
                row1 += "<td>" + data[i]['gledalac_korisnicko'] + "</td>";
                if(data[i]['ocenjen']){
                    row1 += "<td>" + data[i]['ocena'] + "</td>";
                    $('#gradedMoviesDiv > table > tbody').append(row1);
                } else {
                    var selectOceni = oceniCreate(data, i);
                    var btnOceni = "<input type='submit' value='Oceni'>";
                    row1 += "<td>" + selectOceni + "</td>";
                    row1 += "<td>" + btnOceni + "</td>";
                    $('#watchedMoviesDiv > form > table > tbody').append(row1);
                }
            }
        },
        error: function (){
            alert("Greška! (gledalac_index/tables)");
        }
    });
}

function gradeSend(movie_id, grade){
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/oceni/" + movie_id + "/" + grade,
        dataType: "json",
        success: function (data){
            alert("Uspešno ste ocenili film \"" + data[0]['naziv_filma'] + "\" ocenom " + data[1] + ".");
            tablesUpdate();
        },
        error: function (){
            alert("Greška! (gledalac_index/oceni/" + movie_id + "/" + grade +")");
        }
    });
}

function tablesUpdate(){
    $('#gradedMoviesDiv > table > tbody').empty();
    $('#watchedMoviesDiv > form > table > tbody').empty();
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/gledalac_index",
        dataType: "json",
        success: function (data){
            for(var i = 1; i < data.length; i++){
                var row1 = "<tr>";
                row1 += "<td>" + data[i]['naziv_filma'] + "</td>";
                row1 += "<td>" + data[i]['gledalac_korisnicko'] + "</td>";
                if(data[i]['ocenjen']){
                    row1 += "<td>" + data[i]['ocena'] + "</td>";
                    $('#gradedMoviesDiv > table > tbody').append(row1);
                } else {
                    var selectOceni = oceniCreate(data, i);
                    var btnOceni = "<input type='submit' value='Oceni' form='ocenaForm'>";
                    row1 += "<td>" + selectOceni + "</td>";
                    row1 += "<td>" + btnOceni + "</td>";
                    $('#watchedMoviesDiv > form > table > tbody').append(row1);
                }
            }
        },
        error: function (){
            alert("Greška! (gledalac_index/tables)");
        }
    });
}

function oceniCreate(data, i){
    var retVal = "<select name='ocena " + data[i]['id'] + "' id='ocena "  + data[i]['id'] + "'>";
    retVal += "<option id='option " + data[i]['id'] + "' value='1'>1</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='2'>2</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='3'>3</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='4'>4</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='5'>5</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='6'>6</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='7'>7</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='8'>8</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='9'>9</option>";
    retVal += "<option id='option " + data[i]['id'] + "' value='10'>10</option>";
    retVal += "</select>";
    return retVal;
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