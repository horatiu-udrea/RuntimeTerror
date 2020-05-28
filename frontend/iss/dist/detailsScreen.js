import {HOST, PORT} from "../src/Globuls.js"

$(document).ready(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",
        complete: function (data) {
            if (data.statusText == "OK") {
                $("#dates").text(data.responseJSON.startDate + "-" + data.responseJSON.endDate)
                $("#name").text(data.responseJSON.name)
            }
        }
    });
});