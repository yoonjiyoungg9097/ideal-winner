/**
 * 게시글 상세조회의 댓글 처리 
 */

 function showResponse(resp, statusText, xhr, $form)  {
        	if(resp.error){
        		alert(resp.message);
        		return;
        	}else if("valid"==resp.code){
        		alert("검증오류....");
        		return false;
        	}
           
           var html=""
            if(resp.dataList){
               $.each(resp.dataList,function(idx,reply){
                  html+="<tr id='TR_"+reply.rep_no+"'>";
                  html+="<td>"+reply.rep_no+"</td>";
                  html+="<td>"+reply.rep_writer+"</td>";
                  html+="<td>"+reply.rep_ip+"</td>";
                  html+="<td>"+reply.rep_content+"</td>";
                  html+="<td>"+reply.rep_date +"<span data-toggle='modal' class='replyDelBtn'>[삭제]</span></td>    ";
//                  html+="<td><span data-toggle='modal' class=''>[수정]</span></td>    ";
                  html+="</tr>";
               });
            }else{
               html+="<tr><td colspan='4'> 데이터없즘 .</td></tr>";
            }
            pagingArea.html(resp.pagingHTML);
            listBody.html(html);
//         	$('#insertReply').reset();
        	replyForm[0].reset();
         }
	function pagingReply(page, bo_no){
		$.ajax({
			url:$.getContextPath()+"/reply/replyList.do",
			data:{
				bo_no:bo_no,
				page:page
			},
			dataType:"json",
			success:showResponse,
			error:function(resp){
				console.log(resp.status);
			}
		});
	}
	
	$(function() {
		
        // wait for the DOM to be loaded 
        $(document).ready(function() {  
           var options={
                 dataType:   'JSON',
                   success:       showResponse,  // post-submit callback 
           };
            $('#insertReply').ajaxForm(options); 
        });
        
         
        // post-submit callback 
       
   });
		
	
	$(function(){
		replyForm = $("#insertReply");
		pagingArea = $("#pagingArea");
		listBody = $("#listBody");
		
		
		var delModal = $("#replyDeleteModal");
		
		listBody.on("click", ".replyDelBtn", function(){
			var trId=$(this).closest("tr").prop("id");
			var rep_no = trId.substring(trId.indexOf("_")+1);
			
			delModal.find("#rep_no").val(rep_no);
			delModal.modal("show");
		});
		
		$("#modalBtn").on("click", function(){
			var action = replyForm.attr("action");
			replyForm.attr("action", $.getContextPath()+"/reply/replyDelete.do");
			var rep_no = delModal.find("#rep_no").val();
			var rep_pass = delModal.find("#rep_pass").val();
			replyForm.find("[name='rep_no']").val(rep_no);
			replyForm.find("[name='rep_pass']").val(rep_pass);
			replyForm.submit();
			replyForm.attr("action", action);
			$("#insertReply")[0].reset();
			$("#modalForm")[0].reset();
			delModal.modal("hide");
		});
		
	}); 