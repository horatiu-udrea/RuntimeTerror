// import { HOST, PORT } from "../Globuls.js"
const HOST = "http://localhost:"
const PORT = "8080"

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
                type: "PUT",
                url: HOST + PORT + "/authentication",
                contentType: "application/json",
                data: JSON.stringify({name:$("#nameField").val,
                                      username:$("#usernameField").val,
                                      password:$("#passwordField").val, 
                                      affiliation:$("#affiliationField").val, 
                                      email:$("#emailField").val, 
                                      webpage:$("#webpageField").val}),
                dataType: "json",
                complete: function(data, status) {
                    console.log(data);
                    if(data.statusText == "OK") {
                        alert("wait untill a PC Member validates your account");
                        window.location = "../LoginScreen/loginScreen.html";
                    }else {
                        alert("invalid request");
                    }
                }
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