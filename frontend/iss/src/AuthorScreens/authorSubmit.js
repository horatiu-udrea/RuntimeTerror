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
        $("#authorCount").text("Current number of authors added: "+authors.length);
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
});