$(document).ready(function(){
	
	
	window.uploadSuccess = function(r){
		var img = $("#file-item-" + r[0].progressId).find("img");
		img.show();
		img.attr("src","/web/preview/" + r[0].path);
		
	};
	
	
	var uploadEvent = function(box){	
		box.prokey = new Date().getTime();
		box.attr("id","file-item-" + box.prokey);
		
		var f = $("#file-item-" + box.prokey);
		
		$(".error-msg",f).html("");
		$(".progress-bar",f).css({width:"0%"});
        $(".progress-bar span",f).html("");
        
		$("form",box).after('<IFRAME style="display: none" id="upload-result-' + box.prokey + '" name="upload-result-' + box.prokey + '"></IFRAME>');
		
	    $("form",box).attr({"target":"upload-result-" + box.prokey,
	    				  "action": "/web/upload.json?X-Progress-ID=" + box.prokey + 
	    				  "&callback=uploadSuccess&iframe=1"})
	    				  .submit();
		
	    (box.getProgress = function(){
	        $.ajax({
	            type: "GET",
	            url:  "/web/upload/progress.json?callback=?",
	            data: {"X-Progress-ID": box.prokey},
	            dataType: "jsonp",
	            success: function (data) {
//	            	console.log(JSON.stringify(data));
	            	try{
		                if(data.finish == 1){
		                    $(".progress-bar",box).css({width:"100%"});
		                    $(".progress-bar span",box).html("【上传完毕】" );
		                    box.lastreceived = 0;
		                    clearInterval(box.timer);
		                } else {
		                        var prs = [Math.ceil((data.received / data.size) * 100), Math.ceil((data.received-(box.lastreceived||0)) / 1000)];
		                        $(".progress-bar",box).css({width:prs[0] + "%"});
		                        $(".progress-bar span",box).html("【已上传】" + prs[0] +"% " + "上传速度" + prs[1] + "KB/s" );
		                        box.lastreceived = data.received
		                        console.log("data.received && data.size");
		                }
	            	}catch(e){
	            		 clearInterval(box.timer);
	            	}
	          },
	          error:function(){
	        	  clearInterval(box.timer);
	          }
	        });
	    })();
	    box.timer = setInterval(box.getProgress, 1000);
	};
	
	
	$("#uploads").append($("#template").html());
	$(".add-btn").click(function(){
		$("#uploads").append($("#template").html());
	});
	
	
	$(".upload-btn").click(function(){
		$("#uploads .file-item").each(function(){
			uploadEvent($(this));
		});
	});
	
});