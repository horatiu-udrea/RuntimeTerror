import {HOST, PORT} from "../Globuls.js";
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});


$(document).ready(function () {

    $("#createConference").click(function (e) {
        let bidding = $("#conferenceStartDate").val().replace(/-/g, "/").split("/").reverse();
        let start =  bidding[0] + "/" + bidding[1] + "/" + bidding[2];
        let endformat = $("#conferenceEndDate").val().replace(/-/g, "/").split("/").reverse();
        let end =  endformat[0] + "/" + endformat[1] + "/" + endformat[2];
     
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: HOST + PORT + "/section",
            dataType: "json",
            data: JSON.stringify({
                name: $("#conferenceName").val(),
                startTime: start,
                endTime: end,
               
            }),
            complete: function(data){
                if (data.statusText == "OK"){
                    alert("successfully created Section");
                    window.location = "../../dist/index.html";
                } else {
                    alert("conference was not created");
                }

            }

        });
           
    });
});