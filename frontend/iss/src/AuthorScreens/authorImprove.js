import { HOST, PORT } from "../Globuls.js"
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

$(document).ready(function () {

    let data = []
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper",
        dataType: "json",

        complete: function (dataPapers, statusText) {
            if (dataPapers.statusText == "OK") {
                dataPapers.responseJSON.forEach(element => {
                    if (element.paperId == window.localStorage.getItem("selectedProposal")) {
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

                    recommendations.push({from: element.name, rec: element.recommendation, mark: element.qualifier });

                });
            } else {
                alert("can not get reviews for this author");
            }

            addRecommendations();
        }
    });
    function addRecommendations() {
        let formedRecomm = "<dl>";
        recommendations.forEach(element => {
            let recommendat = "";
            if(element.rec == "") {
                recommendat = "No recommandation "
            }else {
                recommendat = element.rec
            }
            formedRecomm += "<dt>" + recommendat + "</dt><dd>@From " + element.from + "-> " + element.mark + "</dt>";
        });

        formedRecomm += "</dl>";
        document.getElementById("PaperReviews").innerHTML = formedRecomm;
    }

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
            url: HOST + PORT + "/paper/full/" + window.localStorage.getItem("selectedProposal"),
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

});

    // console.log(window.localStorage.getItem("selectedProposal"));