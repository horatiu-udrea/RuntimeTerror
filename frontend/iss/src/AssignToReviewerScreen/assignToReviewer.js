import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {
    $.get(HOST + PORT + "paper/assign", function (data) { //getPapers
        code = ""
        $.each(data, function (indexInArray, valueOfElement) { 
            code += "<li value = '"+valueOfElement.paperId+"'>"+"<div class='paper'>"+valueOfElement.name+"</div>"+"</li>";
            $("#paperList").html(code);
        });
    });

    $.get(HOST + PORT + "member", function (data) { //getPC Members
        code = ""
        $.each(data, function (indexInArray, valueOfElement) { 
            code += "<li value = '"+valueOfElement.userId+"'>"+"<div class='pcMember'>"+valueOfElement.name+"</div>"+"</li>";
            $("#pcMemberList").html(code);
        });
    });

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
                    $.put(HOST + PORT + "paper/assign", {userId = $(pcMember).val(), paperId = $(this).val()}, function(){
                        $(this).css("background-color","green");
                    });
                }
            });
        });
    });

});