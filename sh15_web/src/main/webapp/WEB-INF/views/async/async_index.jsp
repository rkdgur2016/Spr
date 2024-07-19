<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<meta charset="UTF-8">
<title>Async_index</title>
	<script>
		document.addEventListener('DOMContentLoaded', function(){
			console.log('DOMContentLoaded')
			
			//변수 창고
			const nameInput = document.querySelector("#name");
			const resultDiv = document.querySelector("#result");
			
			//button 객체 생성
			const sendBtn = document.querySelector("#sendButton");
			console.log('sendBtn : ', sendBtn);
			
			const jsSendBtn = document.querySelector('#jsSendButton');
			console.log('jsSendButton : ', jsSendBtn);
			
			//이벤트 등록 
			sendBtn.addEventListener("click", function(event){
				//이벤트 버블링 방지
				// 이벤트 버블링이란 ? 
				/* 버블링(bubbling)의 원리는 간단합니다.
					한 요소에 이벤트가 발생하면, 이 요소에 할당된 핸들러가 동작하고, 이어서 부모 요소의 핸들러가 동작합니다. 
					가장 최상단의 조상 요소를 만날 때까지 이 과정이 반복되면서 요소 각각에 할당된 핸들러가 동작합니다.*/
				event.stopPropagation();
				console.log('sendBtn clicked ', event);	
				//asyncSend();
				//asyncJsSend();
				
				asyncSendCallAjax();
			});
			
			jsSendBtn.addEventListener("click", function(event){
				event.stopPropagation();
				console.log('jsSendBtn clicked', event);
				
				asyncJsSend();
				
			});
			
			//공통ajax : url, type(POST/GET), 
			function ajaxCall(url, params,dataType="html",type="GET",async = true, _callback){
				//code
		        console.log("┌──────────────────┐"); 
		        console.log("│ ajaxCall()       │");   
		        console.log("└──────────────────┘");		
				
		        console.log("1. url:"+url);
		        console.log("2. dataType:"+dataType);
		        console.log("3. type:"+type);  
		        
		        params.url = url;
		        
		        let paramArray = Object.keys(params);
		        if(paramArray.length > 0){
		        	console.log("4. param -----");  
		        	for(let i =0; i<paramArray.length;i++){
		        		console.log(paramArray[i]+": "+params[paramArray[i]]);
		        	}
		        	console.log("param end-----");  
		        }
		        
		        
		        return  $.ajax({
				            type: type, 
				            url:url,
				            asyn:async,
				            dataType:dataType,
				            data: params,
				            success:function(response){//통신 성공
				                console.log("success response:"+response);
				                _callback(response)
				                
				            },
				            error:function(response){//실패시 처리
				                console.error("error:"+response);
				            }
				        });
				
			}
			
			function asyncSendCallAjax(){
				let type= "POST";
				let url = "/ehr/async/async.do";
				let async = "true";
				let dataType = "html";
				
				let params = {
					name : 	nameInput.value,
					id :    nameInput.value
				};
				
				let response=ajaxCall(url,params,dataType,type,async,function(data){
					console.log('response99 data:'+data);	
					resultDiv.innerHTML = data;
				});
				  
				
				
			}
			
			function asyncJsSend(){
				console.log("비동기 통신");
				console.log("nameInput : " + nameInput.value);
				
				let xhr = new XMLHttpRequest();
				
				//url
				let url = "/ehr/async/asyncJs.do";
				
				let name = nameInput.value;
				
				//비동기 방식으로 POST 전송
				xhr.open('POST', url, true);
				
				//POST인 경우 요청 헤더를 설정하여 전송할 데이터의 형식을 지정한다.
				xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
				
				//요청 상태가 변경될 때마다 호출되는 이벤트 핸들러를 정의한다.
				xhr.onreadystatechange = function(){
					if(xhr.readyState === XMLHttpRequest.DONE){
						if(xhr.status === 200){
							resultDiv.innerHTML = xhr.responseText;
							console.log("┌──────────────────────────────────────────┐");
							console.log("│ asyncJsSend()                        	│");
							console.log("└──────────────────────────────────────────┘");
						}else{
							console.error("요청 실패 : " + xhr.status)
						}
					}
				};
				xhr.send('name=' + name);
			}	
			
			function asyncSend() {
				console.log("비동기 통신");
				console.log("nameInput : " + nameInput.value);

				//XMLHttpRequest
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
				});
			}
			
		});

	</script>
</head>
<body>
	<h2>async</h2>
	<form id="asyncForm">
		<label for="name">이름 : </label>
		<input type="text" id="name" name="name">
		<input type="button" id="sendButton" value="전송(jQeury)">
		<input type="button" id="jsSendButton" value="전송(XMLHttpRequest)">
	</form>
	
	<div id="result"></div>
</body>
</html>