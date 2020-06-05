import { HOST, PORT } from "../Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

{
    let list = []
    let membersTypes= {0:"listener" , 1:"author", 2:"pc member", 3:"co chair", 4:"chair", 5:"pc member"}

    function fillList() {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: HOST + PORT + "/users",
            dataType: "json",
            complete: function (data, statusText) {
                console.log(data.responseJSON);
                if (data.statusText == "OK") {
                    list = data.responseJSON;
                    let htmlCode = "";
                    for (let i = 0; i < list.length; ++i) {
                        console.log(data.responseJSON[i].type);

                        htmlCode += "<li> <input id = 'checkbox" + i + "'type = 'checkbox'>" + list[i].name + " with the role: "+ membersTypes[list[i].type] + "</li>";

                    }
                    $("#listOfPapers").html(htmlCode);
                }
                else {
                    alert(" can not get list of papers");
                }
            }

        });
    }

    function checkBoxes() {

        for (let i = 0; i < list.length; ++i) {
            if (document.getElementById("checkbox" + i).checked) {
                console.log(list[i]);
                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: HOST + PORT + "/users",
                    dataType: "json",
                    data: JSON.stringify({
                        userId: list[i].id,
                        type: document.getElementById("roleSelector").value,
                        validated: document.getElementById("validCheckbox").checked
                    }),
                    complete: function (dataPut, statusText) {
                        if (dataPut == "OK") {
                            alert("Updated Accounts");
                        }
                    }
                });
            }
        }
    }


    $(document).ready(function () {
        fillList();
        $("#submitButton").click(function (e) {
            e.preventDefault();
            checkBoxes();
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
}