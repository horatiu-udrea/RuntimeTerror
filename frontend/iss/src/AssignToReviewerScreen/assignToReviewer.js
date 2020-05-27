import { HOST, PORT } from "../Globuls.js"

const CYAN = "rgb(0, 255, 255)";
const TRANSPARENT = "rgba(0, 0, 0, 0)";
const RED = "rgb(255,0,0)";

function markConflicts() {
    
    $("li").each(function () {
        if($(this).css("border-color") != TRANSPARENT) $(this).css("border-color", CYAN);
    })
    $("#button").prop('disabled', false);

    $("li.pcMember").each(function () {
        if($(this).css("border-color") == TRANSPARENT) return;
        
        let member = this;
        
        $("li.paper").each(function() { 
            if($(this).css("border-color") == TRANSPARENT) return;
            
            let paper = this;

            $.ajax({                  
                type: "POST",
                contentType: "application/json",
                url: HOST + PORT + "/paper/assign",
                data: JSON.stringify({userId: $(member).val(), paperId: $(paper).val()}),
                dataType: "json",

                complete: function(data){
                    if(data.responseJSON.bidResult == "REFUSE_TO_REVIEW") {
                        $(paper).css("border-color", "red");
                        $(member).css("border-color", "red");
                        $("#button").prop('disabled', true);
                    }
                },
                error: function(data){
                    if(data.responseJSON.error == "The bid does not exist!") return;
                }
            })
        });
    })
}

function refreshLists() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/bid",
        dataType: "json",
        complete:function (data) { //getPapers
            let code = "";
            $.each(data.responseJSON, function (indexInArray, valueOfElement) { 
                code += "<li value = '"+valueOfElement.paper.paperId+"' class='paper'>"+"<div>"+valueOfElement.paper.name+"</div>"+"</li>";
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
            $.each(data.responseJSON, function (indexInArray, valueOfElement) { 
                code += "<li value = '"+valueOfElement.userId+"' class='pcMember'>"+"<div>"+valueOfElement.name+"</div>"+"</li>";
                $("#pcMemberList").html(code);
            });
        }
    })
}

$(document).ready(function () {

    refreshLists();

    $("ul").on("click", "li", function (e) { 
        e.preventDefault();
        
        if($(this).css("border-color") == TRANSPARENT){
                $(this).css("border-color","cyan")
        }
        else{
            $(this).css("border-color","transparent")
        }
        markConflicts();
    }); //m-am gandit sa trag linii intre pc-memberii selectati si hartiile selectate la click, ca o animatie interesanta
        //https://stackoverflow.com/questions/19382872/how-to-connect-html-divs-with-lines

    $("#button").click(function (e) { 
        e.preventDefault();

        let exit = false;
        $(".pcMember").each(function (index, element) {
            if ($(this).css("border-color") == "red") {
                exit = true;
                return;
            }
        })
        if(exit) return;
        //TODO If you've got time here's an idea. You might be able to ignore anything that wasn't marked with a red
        //border, and still send valid messages

        $(".pcMember").each(function (index, element) {
            let pcMember = this
            $(".paper").each(function (index, element) {
                if($(this).css("border-color") == CYAN && $(pcMember).css("border-color") == CYAN){
                    let paper = this;
                    
                    $.ajax({
                        type: "PUT",
                        contentType: "application/json",
                        url: HOST + PORT + "/paper/assign",
                        data: JSON.stringify({userId: $(pcMember).val(), paperId: $(paper).val()}),
                        dataType: "json",
            
                        complete: function(){
                            console.log($(pcMember).val() + " " + $(paper).val());
                        },
                        error: function(data){
                            console.log(data.responseJSON.error);
                        }
                    })
                }
            });
        });
    });

});