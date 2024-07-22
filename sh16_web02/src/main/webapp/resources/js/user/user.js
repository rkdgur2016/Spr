
document.addEventListener("DOMContentLoaded", function(){
	console.log("┌──────DOMContentLoaded────┐");
	console.log("└──────────────────┘");
	
	const doRetrieveBtn = document.querySelector("#doRetrieve");
	console.log("doRetrieveBtn", doRetrieveBtn);
	
    const searchWordInput = document.querySelector("#searchWord");
    console.log("searchWordInput", searchWordInput);

    const searchDivSelect = document.querySelector("#searchDiv");
    console.log("searchDiv", searchDiv);

    const pageSizeSelect = document.querySelector("#pageSize");
    console.log("pageSizeSelect", pageSizeSelect);

	const frm = document.querySelector("#userForm");
	console.log("frm", frm);
	
    const userIdInput = document.querySelector("#userId");
    const nameInput = document.querySelector("#name");
    const passwordInput = document.querySelector("#password");
    const birthdayInput = document.querySelector("#birthday");
    const levelSelect = document.querySelector("#level");
    const loginInput = document.querySelector("#login");
    const recommendInput = document.querySelector("#recommend");
    const emailInput = document.querySelector("#email");
    const regDtInput = document.querySelector("#regDt");

    const rows = document.querySelectorAll("#userTable tbody tr");

    const initClearBtn = document.querySelector("#initClearBtn");

    const doSaveBtn = document.querySelector("#doSave");
    console.log("doSaveBtn", doSaveBtn);

    rows.forEach(function(row){
        row.addEventListener('dblclick',function(event){
            console.log('row clicked');

            let userId = this.querySelector('td:nth-child(2)').textContent.trim();
            console.log('두번째 td 값 : ' + userId);
            doSelectOne(userId);
        });
    });

    doSaveBtn.addEventListener("click", function(event){
        console.log("doSaveBtn clicked");
        event.stopPropagation();

        doSave();
    });

    initClearBtn.addEventListener("click", function(event){
        console.log("initClearBtn clicked");

        const initString ="";
        userIdInput.value = initString;
        nameInput.value = initString;
        passwordInput.value = initString;
        birthdayInput.value = initString;
        levelSelect.value = initString;
        loginInput.value = initString;
        recommendInput.value = initString;
        emailInput.value = initString;
        regDtInput.value = initString;
        levelSelect.value = 1;

        userIdInput.disabled = false;
        regDtInput.disabled = false;
    });

    searchDivSelect.addEventListener("change", function(event){
        console.log("searchDivSelect change");
        console.log("searchDivSelect.value : " + searchDivSelect.value);

        if(""=== searchDivSelect.value){
            event.stopPropagation();
            searchWordInput.value= ""; //검색어 초기화
            pageSizeSelect.value = 10; //페이지 사이즈 초기화
        }
    });

	doRetrieveBtn.addEventListener("click", function(event){
		event.stopPropagation();
		console.log("doRetrieveBtn clicked");
		doRetrieve();
	});

    //검색어 enter 이벤트
    searchWordInput.addEventListener("keydown", function(event){
        event.stopPropagation();

        const inputValue = searchWordInput.value;
        console.log("inputValue : " + inputValue);
        console.log("key pressed : ", event.key, event.keyCode);

        if(event.key === 'Enter' && event.keyCode ===13){
            console.log("searchWordInput keydown");

            if(confirm("조회 하시겠습니까?")===false)return;

            doRetrieve();
        }
    });

    function doSave() {
        console.log("doSave()");

        let type="POST";
        let url ="/ehr/user/doSave.do";
        let async = "true";
        let dataType = "html";

        let levelValue = "";
        if(levelSelect.value == "1"){
            levelValue = "BASIC";
        }else if(levelSelect.value == "2"){
            levelValue = "SILVER";
        }else if(levelSelect.value == "3"){
            levelValue ="GOLD";   
        }

        let params = {
            "userId" : userIdInput.value,
            "name" : nameInput.value,
            "password" : passwordInput.value,
            "birthday" : birthdayInput.value,
            "level" : levelValue,
            "login" : loginInput.value,
            "recommend" : recommendInput.value,
            "email" : emailInput.value
        };

        if(confirm("등록 하시겠습니까?") === false)return;

        PClass.pAjax(url,params,dataType,type,async,function(data){
            if(data){
                try{
                    //문자열 Json Object로 변환
                    const message = JSON.parse(data);
                    console.log("message.messageId : " + message.messageId);
                    console.log("message.messageContents : " + message.messageContents);
                    console.log("isEmpty(message) : " + isEmpty(message.messageId));
                    if(isEmpty(message) === false && 1== message.messageId){
                        alert(message.messageContents);
                        doRetrieve();
                    }else{
                        alert(message.messageContents);
                    }
                }catch(e){
                    console.error("data가 null 혹은, undefined 입니다");
                    alert("data가 null 혹은, undefined 입니다.");
                }
            }else{
                console.log("else");
                alert("data가 null 혹은, undefined 입니다.");
            }
        });

    }
	
    function doSelectOne(userId) {
        console.log("doSelectOne(userId) : " + userId);

        let type="GET";
        let url ="/ehr/user/doSelectOne.do";
        let async = "true";
        let dataType = "html";

        let params = {
            "userId" : userId
        };

        PClass.pAjax(url,params,dataType,type,async,function(data){
            if(data){
                try{
                    //문자열 Json Object로 변환
                    const jsonObj = JSON.parse(data);

                    const message = jsonObj.message;
                    console.log("message.messageId : " + message.messageId);
                    console.log("message.messageContents : " + message.messageContents);
                    console.log("isEmpty(message) : " + isEmpty(message.messageId));
                    if(isEmpty(message) === false && 1== message.messageId){
                        alert(message.messageContents);
                        const user = jsonObj.user;
                        userIdInput.value = user.userId;
                        console.log("user.userId : " + user.userId);
                        
                        let levelValue = 0;
                        if(user.level == "BASIC"){
                            levelValue = 1;
                        }else if(user.level == "SILVER"){
                            levelValue = 2;
                        }else if(user.level == "GOLD"){
                            levelValue = 3;   
                        }

                        nameInput.value = user.name;
                        passwordInput.value = user.password;
                        birthdayInput.value = user.birthday;
                        levelSelect.value = user.level;
                        loginInput.value = user.login;
                        recommendInput.value = user.recommend;
                        emailInput.value = user.email;
                        regDtInput.value = user.regDt;
                        levelSelect.value = levelValue;

                        userIdInput.disabled = true;
                        regDtInput.disabled = true;
                    }
                }catch(e){
                    console.error("data가 null 혹은, undefined 입니다");
                    alert("data가 null 혹은, undefined 입니다.");
                }
            }else{
                console.log("else");
            }

        });
    }

	function doRetrieve(){
		console.log("doRetrieve()");
		
		let searchDiv = frm.searchDiv.value;
		console.log("searchDiv : " + searchDiv);
		
		let searchWord = frm.searchWord.value;
		console.log("searchWord : " + searchWord);
		
		let pageSize = frm.pageSize.value;
		console.log("pageSize : " + pageSize);
		
		//시작 페이지 번호는 무조건 1번
		frm.pageNo.value = 1;
		
		let url = "/ehr/user/doRetrieve.do";
		
		frm.action = url;
		frm.submit();
	}
});