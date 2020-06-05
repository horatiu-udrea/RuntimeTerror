import { HOST, PORT } from "../Globuls.js"
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

$(document).ready(function () {
    $.ajax({
        type: "get",
        url: HOST + PORT + "/authentication",
        contentType: "application/json",
       
        complete: function (data) {
            $("#username").text(data.responseJSON.name)
        }
    })
    
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

    $("#back").click(function () {
        
        window.location = "./authorImproveAndUpdate.html";

    });

    let data = []
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper",
        dataType: "json",

        complete: function (dataPapers, statusText) {
            if (dataPapers.statusText == "OK") {
                dataPapers.responseJSON.forEach(element => {
                    if (element.paperId == window.localStorage.getItem("selectedProposalId")) {
                        addItem(element);
                    }
                });
            } else {
                alert("can not get papers for this author");
            }
        }
    });

    function addItem(paper) {
        let name = paper.name;
        let field = paper.field;
        let keywords = paper.keywords;
        let topics = paper.topics;
        let abstract = paper.abstract;
        let authors = paper.authors;
        let formatedAuthors = "";

        authors.forEach(element => {
            formatedAuthors += "Author " + element.name + " (" + element.email + ") \n";
        });
        let toBePrinted = "Name of the proposal : " + name + "<br>" +
            "Fileds of interest: " + field +
            "<br>Keywords of the presentation: " + keywords +
            "<br>Topics on this proposal : " + topics +
            "<br>Description of the paper: " + abstract +
            "<br> Authors: " + formatedAuthors + "<br><br>";

        document.getElementById("PaperDetails").innerHTML = toBePrinted;

    }
    console.log(localStorage.getItem("phase"));
    if (localStorage.getItem("phase") == 3) {
        console.log("some stuff")
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: HOST + PORT + "/section/details",
            dataType: "json",

            complete: function (dataPapers, statusText) {
                console.log(dataPapers);
                if (dataPapers.statusText == "OK") {
                    let toWrite = " Name of the Section : " + dataPapers.responseJSON.name + "<br>" +
                        "Start time: " + dataPapers.responseJSON.startTime +
                        "<br>End time: " + dataPapers.responseJSON.endTime +
                        "<br>Room name : " + dataPapers.responseJSON.roomName + "<br><br>";

                    document.getElementById("SectionDetails").innerHTML = toWrite;
                } else {
                    alert("Sorry, this proposal was not selected");
                    window.location = "./authorImproveAndUpdate";

                }
            }
        });
    }
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
    $("#uploadPaper").click(function () {
        var form = $("#Upload")[0];
        var files = new FormData(form);
        if (localStorage.getItem("phase") != 3) {


            $.ajax({
                type: "PUT",
                enctype: 'multipart/form-data',
                processData: false,  // Important!
                contentType: false,
                cache: false,
                url: HOST + PORT + "/paper/full/" + window.localStorage.getItem("selectedProposalId"),
                data: files,
                complete: function (dataPapers, statusText) {
                    if (dataPapers.statusText == "OK") {
                        alert("the files was uploaded");
                    } else {
                        alert("an error ocurred when uploading");
                    }
                    console.log(dataPapers);
                }
            });
        } else {

            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                processData: false,  // Important!
                contentType: false,
                cache: false,
                url: HOST + PORT + "/section/presentation",
                data: files,
                complete: function (dataPapers, statusText) {
                    if (dataPapers.statusText == "OK") {
                        alert("the files was uploaded");
                    } else {
                        alert("an error ocurred when uploading");
                    }
                }
            });
        }

    });

});

    // console.log(window.localStorage.getItem("selectedProposal"));