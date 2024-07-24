<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>async_index</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
	console.log('DOMContentLoaded');
	
	// button 객체 생성
	const sendBtn = document.querySelector("#sendButton");
	const jsSendBtn = document.querySelector("#jsSendButton");
	console.log(sendBtn, jsSendBtn);
	
	// name 객체 생성
	const nameInput = document.querySelector("#name");
	console.log(nameInput);
	
	const resultDiv = document.querySelector("#result");
	
	// 이벤트 등록 마우스
	sendBtn.addEventListener("click", function(event){
		event.stopPropagation();
		
		aysncSend();
	});
	jsSendBtn.addEventListener("click", function(event){
		event.stopPropagation();
		
		aysncJsSend();
		//asyncSendCallAjax();
	});
	// 이벤트 등록 키보드
    document.getElementById('name').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            const searchTerm = this.value; // 검색어 가져오기
            console.log('검색어:', searchTerm);
            event.preventDefault();
            
            aysncSend();
        }
    });
    
    // 공통 ajax : url, type(POST/GET), async = true, data
    function ajaxCall(url, param, dataType="html", type = "GET", async = true, _callback){
   	   //code   
       console.log("┌───────────────────────");
       console.log("│ ajaxCall()");
       console.log("└───────────────────────");
              
       console.log("1. url" + url);
       console.log("2. dataType" + dataType);
       console.log("3. type" + type);
       console.log("4. async" + async);
       
       param.url = url;
       
       let paramArray = Object.keys(param);
       if(paramArray.length > 0){
    	   for (let i = 0; i<paramArray.length; i++) {
    		   console.log(paramArray[i] + ": " + param[paramArray[i]]);
   		   } 
    	   console.log("param end-----");  
       }
       
       return $.ajax({
		           type:type, 
		           url:url,
		           asyn:async,
		           dataType:dataType,
		           data:param,
		           success:function(response){//통신 성공
		               console.log("success response:"+response);

		               // resultDiv.innerHTML = response;
		               _callback(response)
		           },
		           error:function(response){//실패시 처리
		               console.error("error:"+response);
		           }
		       });
    }
    
	function asyncSendCallAjax(){
		let type = "POST";
		let url = "/ehr/async/async.do";
		let async = "true";
		let dataType = "html";
		
		let params = {
	            name :  nameInput.value,
	            id :    nameInput.value	
		};
		
        let response=ajaxCall(url,params,dataType,type,async,function(data){
            console.log('response data:'+data);   
            resultDiv.innerHTML = data;
        });
	}
	
	function resultFunction(data){
		console.log('resultFunction : ' + data);
	}
	
	function aysncSend(){
        console.log('asyncSend');
        console.log('nameInput' + nameInput.value);
		//XMLhttpRequest
        
		$.ajax({
            type: "POST", 
            url:"/ehr/async/async.do",
            asyn:"true",
            dataType:"html",
            data:{
                "name": nameInput.value  
            },
            success:function(response){//통신 성공
                console.log("success response:"+response);
                resultDiv.innerHTML = response;
            },
            error:function(response){//실패시 처리
                console.log("error:"+response);
            }
        }); // ajax 끝
        
	} // asyncSend 끝
	
	function aysncJsSend(){
		console.log('aysncJsSend');
		console.log('name : ' + nameInput.value);
		
		let xhr = new XMLHttpRequest();
		
		// url
		let name = nameInput.value;
		let url = "/ehr/async/asyncJs.do";		
		
		// 비동기 방식으로  POST 전송
		xhr.open('POST', url, true); // false라고 값을 설정하면 동기 방식의 전송이 진행됩니다.
		
		// 요청 헤더를 설정하여 전송할 데이터의 형식을 지정합니다 .:POST
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
		
		// 요청 상태가 변경될 때마다 호출되는 이벤트 핸들러를 정의합니다
		xhr.onreadystatechange = function(){
			// 요청이 완료된 상태인 경우
			if(xhr.readyState === XMLHttpRequest.DONE){
				if(xhr.status === 200){ // 요청 성공
				    resultDiv.innerHTML = xhr.responseText;
					console.log("┌─────────────────────────────────────────────────────────");
					console.log("│ asyncJsSend()");
					console.log("└─────────────────────────────────────────────────────────");
				} // inner if
				else{ // 요청 실패
					console.error("요청 실패 : ", xhr.status);
				}
			} // outer if
		};
		
		// 서버로 전송
		xhr.send('name='+name);
	} // function aysncJsSend end
});
</script>
</head>
<body>
    <h3>나다 오태식 시즌2</h3>
    
    <form id = "asyncForm">
        <label for = "name">이름 : 오태식2</label>
        <input type = "text" id = "name" name = "name">
        <input type = "button" id = "sendButton" value = "전송(jQuery)">
        <input type = "button" id = "jsSendButton" value = "전송(XMLHttpRequest)">
    </form>
    
    <div id = "result"></div>
</body>
</html>