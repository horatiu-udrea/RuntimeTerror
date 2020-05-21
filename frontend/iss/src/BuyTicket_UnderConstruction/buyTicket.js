import { HOST,PORT } from "../Globuls.js"

$(document).ready(function () {
    
    $.getJSON(HOST + PORT + "/conference", data,
        function (data) {
            $("#conferenceInfo").text(data.startDate +"<br>"+data.endDate+"<br>"+data.description)
        }
    );

    $("#paypalButton").click(function (e) { 
        e.preventDefault();
        
        $(this).parent().parent().css("display", "none")

        e.stopPropagation();
    });
});

