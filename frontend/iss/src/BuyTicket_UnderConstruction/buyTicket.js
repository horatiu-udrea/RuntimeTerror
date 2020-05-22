import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",
        complete: function (data) {
            if (data.statusText == "OK") {
                $("#conferenceInfo").text(data.responseJSON.startDate + "\n" + data.responseJSON.endDate + "\n" + data.responseJSON.name)
            }
        }
    });


    $("#paypalButton").click(function (e) {
        e.preventDefault();

        $(this).parent().parent().css("display", "none")

        e.stopPropagation();
    });
});

