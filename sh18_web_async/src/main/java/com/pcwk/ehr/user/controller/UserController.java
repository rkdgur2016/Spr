package com.pcwk.ehr.user.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.code.service.CodeService;
import com.pcwk.ehr.user.domain.User;
import com.pcwk.ehr.user.service.UserService;

@Controller // RestController 사용시 화면 보낼수 없고 HTML코드로 넘어옴
@RequestMapping("user")
public class UserController implements PLog{
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	CodeService codeService;
	
	public UserController() {
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ UserController()");
		log.debug("└──────────────────────────────────────────────");	
	}
	
	@RequestMapping(
			value = "/idDuplicateCheck.do",
			method = RequestMethod.GET,
			produces = "text/plain;charset=UTF-8"
	)
	@ResponseBody
	public String idDuplicateCheck(User inVO) throws SQLException {
		String jsonString = "";
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ idDuplicateCheck()");
		log.debug("└──────────────────────────────────────────────");
		
		int flag = userService.idDuplicateCheck(inVO);
		
		String message = "";
		if(1==flag) {
			message = inVO.getUserId()+"는 중복된 아이디 입니다.";
		}else {
			message = inVO.getUserId()+"는 사용가능한 아이디 입니다.";
		}
		
		jsonString = new Gson().toJson(new Message(flag, message));
		
		return jsonString;
	}
	
	@RequestMapping(
			value = "/doRetrieveAjax.do",
			method = RequestMethod.GET,
			produces = "text/plain;charset=UTF-8"
	)
	@ResponseBody
	public String doRetrieveAjax(Model model, HttpServletRequest req) throws SQLException{
		String jsonList = "";
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doRetrieveAjax()");
		log.debug("└──────────────────────────────────────────────");
		
		Search search = new Search();
		
		//검색구분
		//searchDiv = "" (기본값)
		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
		search.setSearchDiv(searchDiv);
		//searchWord = "" (기본값)
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");
		search.setSearchWord(searchWord);
		
		//pageSize=10 (기본값)
		String pageSize = StringUtil.nvl(req.getParameter("pageSize"), "10");		
		//pageNo=1 (기본값)
		String pageNo = StringUtil.nvl(req.getParameter("pageNo"), "1");		
		
		search.setPageSize(Integer.parseInt(pageSize));
		search.setPageNo(Integer.parseInt(pageNo));
		
		// 1. 
		log.debug("1. param search : " + search);
		List<User> list = this.userService.doRetrieve(search);
		
		//페이징 totalcnt 값 꺼내기 웹에서 꺼내는것보다 여기가 더 간단
		int totalCnt = 0;
		if(null != list && list.size() > 0) {
			User firstVO = list.get(0);
			totalCnt = firstVO.getTotalCnt();
		}
		model.addAttribute("totalCnt",totalCnt);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		jsonList = gson.toJson(list);
		log.debug("2. jsonList : " + jsonList);
		
		String allJson = "{\"user\":"+jsonList+",\"totalCnt\":"+totalCnt+"}"; 
		return allJson;
	}
	
	
	@RequestMapping(
			value = "/doRetrieve.do",
			method = RequestMethod.GET
	)
	public String doRetrieve(Model model, HttpServletRequest req) throws SQLException{
		// WEB-INF/views + "viewName" + .jsp
		// WEB-INF/views/user/user_list.jsp
		String viewName = "user/user_list";
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doRetrieve()");
		log.debug("└──────────────────────────────────────────────");	
		
		Search search = new Search();
		
		//검색구분
		//searchDiv = "" (기본값)
		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
		search.setSearchDiv(searchDiv);
		//searchWord = "" (기본값)
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");
		search.setSearchWord(searchWord);
		
		//pageSize=10 (기본값)
		String pageSize = StringUtil.nvl(req.getParameter("pageSize"), "10");		
		//pageNo=1 (기본값)
		String pageNo = StringUtil.nvl(req.getParameter("pageNo"), "1");		
		
		search.setPageSize(Integer.parseInt(pageSize));
		search.setPageNo(Integer.parseInt(pageNo));
		
		// 1. 
		log.debug("1. param search : " + search);
		List<User> list = this.userService.doRetrieve(search);
		
		// 2. 화면 전송 데이터
		model.addAttribute("list", list); 		// 조회 데이터
		model.addAttribute("search", search); 	// 검색 조건
		
		//페이징 totalcnt 값 꺼내기 웹에서 꺼내는것보다 여기가 더 간단
		int totalCnt = 0;
		if(null != list && list.size() > 0) {
			User firstVO = list.get(0);
			totalCnt = firstVO.getTotalCnt();
		}
		model.addAttribute("totalCnt",totalCnt);
		
		//----------------------------------------------------------------------
		Code code = new Code();
		code.setMsgCode("MEMBER_SEARCH"); // 검색 조건		
		List<Code> memberSearch = this.codeService.doRetrieve(code);		
		model.addAttribute("MEMBER_SEARCH", memberSearch); // 검색조건
		
		code.setMsgCode("COM_PAGE_SIZE"); // 페이지 사이즈
		List<Code> comPageSize = this.codeService.doRetrieve(code);
		model.addAttribute("COM_PAGE_SIZE", comPageSize); // 검색조건
		
		code.setMsgCode("MEMBER_LEVEL"); // 등급
		List<Code> memberLevel = this.codeService.doRetrieve(code);
		model.addAttribute("MEMBER_LEVEL", memberLevel); // 검색조건
		//----------------------------------------------------------------------
		
		return viewName;		
	}
	
	@RequestMapping(
			value = "/doSelectOne.do",
			method = RequestMethod.GET,
			produces = "text/plain;charset=UTF-8"
	)
	@ResponseBody
	public String doSelectOne(User inVO) throws SQLException{
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doSelectOne()");
		log.debug("└──────────────────────────────────────────────");	
		
		String jsonString = "";
		
		log.debug("1. param : " + inVO);
		User outVO = userService.doSelectOne(inVO);
		
		String message = "";
		int flag = 0;
		if (null != outVO) {
			message = inVO.getUserId() + " 님이 조회되었습니다.";
			flag = 1;
		}else {
			message = inVO.getUserId() + " 조회 실패!";
		}
		
		//message
		jsonString = new Gson().toJson(new Message(flag, message));
		log.debug("3. jsonString : " + jsonString);
		
		//user
		String jsonUser = new Gson().toJson(outVO);
		
		String allMessage = "{\"user\" : "+jsonUser + ",\"message\": "+jsonString+"}";
		
		
		return allMessage;
	}
	
	@RequestMapping(
			value = "/doUpdate.do",
			method = RequestMethod.GET,
			produces = "text/plain;charset=UTF-8"
	)
	@ResponseBody
	public String doUpdate(User inVO) throws SQLException{		
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doUpdate()");
		log.debug("└──────────────────────────────────────────────");	
		
		String jsonString = "";
		
		// 1. 
		log.debug("param user : " + inVO);
		
		int flag = userService.doUpdate(inVO);
		
		// 2.
		log.debug("flag : " + flag);
		
		String message = "";
		
		if(1==flag) {
			message = inVO.getUserId() + " 님 정보 수정이 완료되었습니다.";
		}else {
			message = inVO.getUserId() + " 실패다 그냥 나가라";			
		}
		
		Message messageObj = new Message(flag, message);
		
		Gson gson = new Gson();
		jsonString = gson.toJson(messageObj);
		
		// 3.
		log.debug("3. jsonString : " + jsonString);
		
		return jsonString;
	}
	
	/*
	 * 단건등록
	 * @param user
	 * @return
	 * @throws SQLException
	 * */
	@RequestMapping(
			value = "/doSave.do",
			method = RequestMethod.POST,
			produces = "text/plain;charset=UTF-8"
	)		// produces : 화면으로 전송할 때 encoding
	
	@ResponseBody
	public String doSave(User user) throws SQLException {
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doSave()");
		log.debug("└──────────────────────────────────────────────");	
		String jsonString = "";
		
		// 1. 
		log.debug("param user : " + user);
		
		int flag = userService.doSave(user);
		
		// 2.
		log.debug("flag : " + flag);
		
		String message = "";
		
		if(1==flag) {
			message = user.getUserId() + " 님이 등록되었습니다.";
		}else {
			message = user.getUserId() + " 실패다 그냥 나가라";			
		}
		
		Message messageObj = new Message(flag, message);
		
		Gson gson = new Gson();
		jsonString = gson.toJson(messageObj);
		
		log.debug("3. jsonString : " + jsonString);
		
		return jsonString;
	}
	
	/*
	 * 단건 삭제
	 * @param user
	 * @return 1(성공) / 0(실패)
	 * */
	@RequestMapping(
			value = "/doDelete.do",
			method = RequestMethod.GET,
			produces = "text/plain;charset=UTF-8"
			)		// produces : 화면으로 전송할 때 encoding	
	@ResponseBody
	public String doDelete(User inVO) throws SQLException {
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doDelete()");
		log.debug("└──────────────────────────────────────────────");	
		String jsonString = "";
		
		// 1. 
		log.debug("param user : " + inVO);		
		int flag = userService.doDelete(inVO);
		
		// 2.
		log.debug("flag : " + flag);
		
		String message = "";
		
		if(1==flag) {
			message = inVO.getUserId() + " 님이 삭제되었습니다.";
		}else {
			message = inVO.getUserId() + " 실패다 그냥 너가 나가라";			
		}	
		
		jsonString = new Gson().toJson(new Message(flag, message));		
		log.debug("3. jsonString : " + jsonString);
		
		return jsonString;
	}
	
}
