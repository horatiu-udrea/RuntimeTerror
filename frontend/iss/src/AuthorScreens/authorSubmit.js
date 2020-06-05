import { HOST, PORT } from "../Globuls.js"
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});
// change screens and put enabeling buttons
// phase1 : submit, improve
// phase2 : ... diagrama http trello
$(document).ready(function () {
    let authors = []
    $("#logout").click(function () {
        $.ajax({
            type: "POST",
            url: HOST + PORT + "/authentication/logout",
            contentType: "application/json",
           
            complete: function (data) {
                if (data.statusText == "OK") {
                    localStorage.clear();
                    window.location = "../../dist/index.html";
                } else {
                    alert("fail");
                }
            }
        })
    });
    $("#addMoreAuthors").click(function () {
        let name = document.getElementById("proposalAuthorName").value;
        let email = document.getElementById("proposalAuthorEmail").value;
        if (name == "" || email == "") {
            alert("can not add an empty author");
        }
        else {
            let cannot = false;
            authors.forEach(element => {
                if (element.name == name && element.email == email) {
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
        $("#authorCount").text("Current number of authors added: "+authors.length);
        console.log(authors);
    });


    $("#submitProposal").click(function () { //TODO Validate input
        console.log({
            name: $("#proposalName").val(),
            field: $("#proposalFiels").val(),
            keywords: $("#proposalKeywords").val(),
            topics: $("#proposalTopics").val(),
            authors: authors,
            abstract: $('#proposalDescription').val()
        });

        var notEmptyArguments = true;
        if ($("#proposalName").val() === "" && 
            $("#proposalFiels").val() === "" && 
            $("#proposalKeywords").val() === "" && 
            $("#proposalTopics").val() === "" && 
            authors.length === 0 && 
            $('#proposalDescription').val() === "") {
            notEmptyArguments = false;
        } 
        if (notEmptyArguments) {

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
        }
        else{
            alert("Fill all fields.")
        }
    });
});