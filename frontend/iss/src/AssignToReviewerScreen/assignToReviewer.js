import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/assign",
        dataType: "json",
        complete:function (data) { //getPapers
            let code = "";
            $.each(data, function (indexInArray, valueOfElement) { 
                code += "<li value = '"+valueOfElement.paperId+"'>"+"<div class='paper'>"+valueOfElement.name+"</div>"+"</li>";
                $("#paperList").html(code);
            });
        }
    })

    $.ajax({
        type: "GET",
        contentType: "/application/json",
        url: HOST + PORT + "/member",
        dataType: "json",

        complete:function (data) { //getPcMemebers
            let code = "";
            $.each(data, function (indexInArray, valueOfElement) { 
                code += "<li value = '"+valueOfElement.userId+"'>"+"<div class='pcMember'>"+valueOfElement.name+"</div>"+"</li>";
                $("#pcMemberList").html(code);
            });
        }
    })

    $(".paper, .pcMember").click(function (e) { 
        e.preventDefault();
        
        if($(this).css("border-color","transparent")){
            $(this).css("border-color","cyan")
        }
        else{
            $(this).css("border-color","transparent")
        }
    }); //m-am gandit sa trag linii intre pc-memberii selectati si hartiile selectate la click, ca o animatie interesanta
        //https://stackoverflow.com/questions/19382872/how-to-connect-html-divs-with-lines

    $("#button").click(function (e) { 
        e.preventDefault();
        
        $(".pcMember").each(function (index, element) {
            let pcMember = this
            $(".paper").each(function (index, element) {
                if($(this).css("border-color","cyan") && $(pcMember).css("border-color","cyan")){
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: HOST + PORT + "paper/assign",
                        data: JSON.stringify({userId: $(pcMember).val(), paperId: $(this).val()}),
                        dataType: "json",
            
                        complete: function(){
                            $(this).css("background-color","green");
                        }
                    })
                }
            });
        });
    });

});