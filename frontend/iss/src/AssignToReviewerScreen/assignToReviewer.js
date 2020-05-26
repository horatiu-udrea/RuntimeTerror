import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/bid",
        dataType: "json",
        complete:function (data) { //getPapers
            let code = "";
            $.each(data.responseJSON, function (indexInArray, valueOfElement) { 
                if (valueOfElement.bidResult == 1) return; //TODO ask if this filter is needed
                code += "<li value = '"+valueOfElement.paperId+"' class='paper'>"+"<div>"+valueOfElement.paper.name+"</div>"+"</li>";
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

    $("#pcMemberList").on("click", "li.pcMember", function (e) { 
        e.preventDefault();
        let q = $(this).css("border-color");
        if($(this).css("border-color") == "rgba(0, 0, 0, 0)"){
            let good = true;
            let member = this;

            $("li.paper").each(function() {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: HOST + PORT + "/paper/assign",
                    data: JSON.stringify({userId: $(member).parent().val(), paperId: $(this).val()}),
                    dataType: "json",
        
                    complete: function(data){
                        if(data.responseJSON.bidResult < 2) {
                            $(this).css("border-color", "red");
                            $(member).parent().css("border-color", "red");
                            good = false;
                        }
                    }
                })
            });

            if(good == true) //I'm tired please don't judge
                $(this).css("border-color","cyan")
        }
        else{
            $(this).css("border-color","transparent")
        }
    }); 

    $("#paperList").on("click", "li.paper", function (e) { 
        e.preventDefault();
        
        if($(this).css("border-color") == "rgba(0, 0, 0, 0)"){
            let good = true;
            let paper = this;

            $("li.pcMember").each(function() {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: HOST + PORT + "/paper/assign",
                    data: JSON.stringify({userId: $(this).val(), paperId: $(paper).parent().val()}),
                    dataType: "json",
        
                    complete: function(data){
                        if(data.responseJSON.bidResult < 2) {
                            $(this).css("border-color", "red");
                            $(paper).parent().css("border-color", "red");
                            good = false;
                        }
                    }
                })
            });

            if(good == true) //I'm tired please don't judge
                $(this).css("border-color","cyan")
        }
        else{
            $(this).css("border-color","transparent")
        }
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
                if($(this).css("border-color") == "rgb(0, 255, 255)" && $(pcMember).css("border-color") == "rgb(0, 255, 255)"){
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