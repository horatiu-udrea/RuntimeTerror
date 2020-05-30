import { HOST, PORT } from "../Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

const CYAN = "rgb(0, 255, 255)";
const RED = "rgb(255,0,0)";

let paperClicked = {};
let memberClicked = {};

function drawLine(svg, listItem1, listItem2, color){
    let code = svg.html();
    let pos1 = $(listItem1).offset();
    let pos2 = $(listItem2).offset();
    let width1 = parseInt($(listItem1).css("width"));
    let height1 = parseInt($(listItem1).css("height")) / 2;
    let height2 = parseInt($(listItem2).css("height")) / 2;
    
    code += "<line x1='"+(pos1.left + width1 + 2)+"' y1='"+(pos1.top + height1)+"' x2='"+(pos2.left + 1)+"' y2='"+(pos2.top + height2)+"' style='stroke:"+color+";stroke-width:2' />";
    svg.html(code);
}

function markConflicts() {
    
    $("#svg").html("");
    $("li.pcMember").each(function () {
        if(memberClicked[$(this).val()] == false) return;
        $(this).css("border-color", CYAN);
        let member = this;
        
        $("li.paper").each(function () {
            if(paperClicked[$(this).val()]== false) return;
            $(this).css("border-color", CYAN);
            drawLine($("#svg"), this, member, CYAN);
        })
    })
    $("#button").prop('disabled', false);

    $("li.pcMember").each(function () {
        if(memberClicked[$(this).val()] == false) return;
        
        let member = this;
        
        $("li.paper").each(function() { 
            if(paperClicked[$(this).val()]== false) return;
            
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
                        drawLine($("#svg"), paper, member, RED);
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
                paperClicked[valueOfElement.paper.paperId] = false;
            });
            $("#paperList").html(code);
            
            var body = document.body,
                html = document.documentElement;

            var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
            $(".strip").css("height", height);
            $("#button").css("top", height / 2 - 35);
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
                memberClicked[valueOfElement.userId] = false;
            });
            $("#pcMemberList").html(code);
        }
    })
}

$(document).ready(function () {

    refreshLists();

    $("#pcMemberList").on("click", "li", function (e) { 
        e.preventDefault();
        
        if(memberClicked[$(this).val()] != true){
                $(this).css("border-color","cyan")
                memberClicked[$(this).val()] = true;
        }
        else{
            $(this).css("border-color","transparent")
            memberClicked[$(this).val()] = false;
        }
        markConflicts();
    }); 

    $("#paperList").on("click", "li", function (e) { 
        e.preventDefault();
        
        if(paperClicked[$(this).val()] != true){
                $(this).css("border-color","cyan")
                paperClicked[$(this).val()] = true;
        }
        else{
            $(this).css("border-color","transparent")
            paperClicked[$(this).val()] = false;
        }
        markConflicts();
    }); 

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
                        dataType: "json"
                    })
                }
            });
        });
    });

});