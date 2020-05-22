import { HOST, PORT } from "../Globuls.js"

{
    let list = []

    function fillList(){
         $.get(HOST + PORT + "/member", function (data) {  
            //list = $.extend( true, {}, data );
            list = data;
            htmlCode = "";

            for(i = 0; i < list.length; ++ i)
                htmlCode += "<li> <input id = 'checkbox"+i+"'type = 'checkbox'>" + list[i].userid + "</li>";

            $("#listOfPapers").html(htmlCode);
         })
    }

    function checkBoxes(){
        let validated = document.getElementById("validCheckbox").checked;
        let type = document.getElementById("roleSelector").value;

        for (i = 0; i < list.length; ++ i){
            if (document.getElementById("checkbox"+i).checked){
                let userId = list[i].userId;
                
                $.put(HOST + PORT + "LINK -Check Globals-", {userId: userId, type: type, validated: validated}, function(){
                    $("#message").text("it worked.");
                })
            }
        }
    }
}

$(document).ready(function () {
    fillList();
    $("#submitButton").click(function (e) { 
        e.preventDefault();
        
        checkBoxes();
    });
});