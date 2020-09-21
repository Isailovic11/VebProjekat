$(document).on("submit", "form", function(event) {
    event.preventDefault();

    var korisnickoime = $('#korisnickoime').val();
    var lozinka = $('#lozinka').val();

    var loginDTO = loginToJSON(korisnickoime, lozinka);

    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/prijava",
        dataType: "json",
        contentType: "application/json",
        data: loginDTO,
        success: function (data) {
            console.log("SUCCESS: ", data);
            if(data['login'] === false){
                var msg = "<p>Pogrešno korisničko ime ili lozinka.</p>";
                $('#usn_pass').text(" ");
                $('#usn_pass').append(msg);
                $('#lozinka').text("");
            } else {
                if(!data['aktivan']) {
                    window.location.href = "neaktivan_profil.html";
                }
                else if(!data['approved']) {
                    window.location.href = "ceka_odobrenje.html";
                }
                else if(data['uloga'] === "GLEDALAC"){
                    window.location.href = "gledalac_index.html";
                }
                else if(data['uloga'] === "MENADZER") {
                    window.location.href = "menadzer_index.html";
                }
                else if(data['uloga'] === "ADMIN") {
                    window.location.href = "admin_index.html";
                }
                else {
                    alert("Greška u prijavi!");
                    window.location.href = "greska_u_prijavi.html";
                }

            }

        },
        error: function(){
            alert("Greška!");
        }
    });
});

function loginToJSON(korisnickoime, lozinka){
    return JSON.stringify({
       "korisnickoime": korisnickoime,
       "lozinka": lozinka,
        "login": false
    });
}
