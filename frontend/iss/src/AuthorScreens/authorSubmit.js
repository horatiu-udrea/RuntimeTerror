<<<<<<< HEAD
import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {
    let authors = []
    $("#addMoreAuthors").click(function () {
        let name = document.getElementById("proposalAuthorName").value;
        let email = document.getElementById("proposalAuthorEmail").value;
        if (name == "" || email == "") {
            alert("can not add an empty author");
        }
        else {
            let cannot = false;
            authors.forEach(element => {
                if(element.name == name && element.email == email){
                    cannot = true;
                }
            });
            if (cannot) {
                alert("you can  not include the same author twice");
            } else {
                authors.push({ name: name, email: email });
            }
            document.getElementById("proposalAuthorName").value = "";
            document.getElementById("proposalAuthorEmail").value = "";
        }
        console.log(authors);
    });


    $("#submitProposal").click(function () {

        $.ajax({
            type: "POST",
            url: HOST + PORT + "/paper",
            contentType: "application/json",
            data: JSON.stringify({
                name: $("#proposalName").val(),
                field: $("#proposalFiels").val(),
                keywords: $("#proposalKeywords").val(),
                topics: $("#proposalTopics").val(),
                authors: authors,
                abstract: $('#proposalDescription').val()
            }),
            complete: function (data) {
                if (data.statusText == "OK") {
                    alert("submission was successfully introduced");

                } else {
                    alert("fail");
                }
            }
        })

    });
=======
import {PORT,HOST} from "../Globuls.js"

$(document).ready(function () {   
    $("#submitProposal").click(function(){
        
        $.post(HOST + PORT + "/paper", {
            name: $("#proposalName").val(),
            field: $("#proposalFiels").val(),
            keywords: $("#proposalKeywords").val(),
            topics: $("#proposalTopics").val(),
            authors: $("#proposalAuthors").val(),
            description: $('#proposalDescription').val()
        })
        .done(function(){
            //Nu stiu exact aici cum vine faza, dar for now...
            window.location = "ADD HOME LINK HERE";
        })
        .fail(function(error){
            $("input[type=text]").val = error.responseText;
        });
    }); 
>>>>>>> a78d02739fa30805ac679969d0c848743aa9ec56
});