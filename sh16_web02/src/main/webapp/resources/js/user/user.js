/**
 * 
 */
document.addEventListener("DOMContentLoaded", function(){
	console.log("┌──────DOMContentLoaded────┐");
	console.log("└──────────────────┘");
	
	const doRetrieveBtn = document.querySelector("#doRetrieve");
	console.log("doRetrieveBtn", doRetrieveBtn);
	
	const frm = document.querySelector("#userForm");
	console.log("frm", frm);
	
	doRetrieveBtn.addEventListener("click", function(event){
		event.stopPropagation();
		console.log("doRetrieveBtn clicked");
		doRetrieve();
	});
	
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