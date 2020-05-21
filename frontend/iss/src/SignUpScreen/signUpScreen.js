import { HOST, PORT } from "../Globuls.js"

var imageArray = ["#background4","#background1","#background2","#background3","#background4"];
var currentImage = 1;

function nextBackgroundImage(){
    currentImage += 1;

    $(imageArray[currentImage - 1]).fadeOut(1000);
    $(imageArray[currentImage]).fadeIn(1000);

    if(currentImage == imageArray.length -1){
        currentImage = 0;
    }
}

$(document).ready(function () {
    $("#background2").fadeOut(0);
    $("#background3").fadeOut(0);
    $("#background4").fadeOut(0);
    setInterval(nextBackgroundImage, 5000);

    $("#signUpButton").click(function (e) { 
        e.preventDefault();
        let found = false;

        $("input[type=password],input[type=text]").each(function () {
            if($(this).val() == ""){
                found = true;
                return;
            }
        })
        if(found) 
            $("#errorMessage").text("*All fields must be filled.");
        else{
            $("#errorMessage").text("");

            $.ajax({
                type: 'PUT',
                url: HOST + PORT + "/authentication",
                contentType: 'application/json',
                data: JSON.stringify({"name":$("#nameField").val,
                                      "username":$("#usernameField").val,
                                      "password":$("#passwordField").val, 
                                      "affiliation":$("#affiliationField").val, 
                                      "email":$("#emailField").val, 
                                      "webpage":$("#webpageField").val}),
            }).done(function () {
                //Aici va veni... previous link? sau ceva de genul
            }).fail(function (error) {
                $("input[type=text]").val = error.responseText;
            });
        }
    });

    $("input[type=password]").focusout(function (e) { 
        e.preventDefault();
        if($("#passwordField").val() != $("#repeatPasswordField").val())
            $("#errorMessage").text("*Passwords do not match.");
        else if($("#errorMessage").text() == "*Passwords do not match.")
            $("#errorMessage").text("");
    });
});