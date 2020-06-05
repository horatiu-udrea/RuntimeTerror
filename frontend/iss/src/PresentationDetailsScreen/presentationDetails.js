import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: HOST + PORT + "/section/details",
        dataType: "json",
        success: function (data) {
            $(".title").text(data.name);
            console.log(data.startTime);
            $("#startTime").text("Start Time: "+ data.startTime.toString());
            $("#endTime").text("End Time: "+ data.endTime);
            $("#roomName").text("Room Name: "+ data.roomName);
        }
    });

    $('input[type=submit]').click(function (e) { 
        e.preventDefault();
        var form = $("#Upload")[0];
        var files = new FormData(form);
        $.ajax({
            type: "PUT",
            enctype: 'multipart/form-data',
            processData: false,  // Important!
            contentType: false,
            cache: false,
            url: HOST + PORT + "/section/presentation" +  window.localStorage.getItem("selectedProposal"),
            data: files,
            complete: function (dataPapers, statusText) {
                if(dataPapers.statusText == "OK"){
                    alert("the files was uploaded");
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
        
        window.location = "../../dist/index.html";

    });
});