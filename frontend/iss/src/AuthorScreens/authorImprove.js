import { HOST, PORT } from "../Globuls.js"
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});
// change to ph3: upload presentation ( new stuff ) iff has section + section details
// change to ph1: abstract(description) + paper(pdf)
// ph2 = ph1 + see reviews ( no reviews = no improve )
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
    let recommendations = []

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/section/review",
        dataType: "json",

        complete: function (dataPapers, statusText) {
            console.log(dataPapers);
            if (dataPapers.statusText == "OK") {
                dataPapers.responseJSON.forEach(element => {

                    recommendations.push({ from: element.name, rec: element.recommendation, mark: element.qualifier });

                });
            } else {
                alert("can not get reviews for this author");
                window.location = "./authorImproveAndUpdate.html";
            }

            addRecommendations();
        }
    });
    $("#upload").click(function () {
        var form = $("#Upload")[0];
        var files = new FormData(form);

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
            }
        });
    });
    function addRecommendations() {
        let formedRecomm = "<dl>";
        recommendations.forEach(element => {
            let recommendat = "";
            if (element.rec == "") {
                recommendat = "No recommandation "
                window.location = "./authorImproveAndUpdate.html";
            } else {
                recommendat = element.rec
            }
            formedRecomm += "<dt>" + recommendat + "</dt><dd>@From " + element.from + "-> " + element.mark + "</dt>";
        });

        formedRecomm += "</dl>";
        document.getElementById("PaperReviews").innerHTML = formedRecomm;
    }
    $("#improve").click(function () {
        $.ajax({
            type: "PUT",
            url: HOST + PORT + "/paper",
            contentType: "application/json",
            data: JSON.stringify({
                paperId: localStorage.getItem("selectedProposalId"),
                abstract: $('#proposalDescription').val()
            }),
            complete: function (data) {
                if (data.statusText == "OK") {
                    alert("improval was successfully done");
                    location.reload();
                } else {
                    alert("fail");
                }
            }
        })
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
    $("#uploadPaper").click(function () {
        var form = $("#Upload")[0];
        var files = new FormData(form);
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
                    location.reload();
                } else {
                    alert("an error ocurred when uploading");
                }
            }
        });
    });
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
});

    // console.log(window.localStorage.getItem("selectedProposal"));