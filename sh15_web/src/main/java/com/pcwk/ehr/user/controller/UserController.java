package com.pcwk.ehr.user.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.domain.User;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController implements PLog{
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	public UserController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserController()                         │");
		log.debug("└──────────────────────────────────────────┘");
	}
	
	@RequestMapping(value="/doDelete.do", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(User user) throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserController : doDelete()              │");
		log.debug("└──────────────────────────────────────────┘");
		String jsonString ="";
		log.debug("┌");
		log.debug("│ user : " + user);
		log.debug("└");
		
		int flag = userService.doDelete(user);
		log.debug("┌");
		log.debug("│ flag : " + flag);
		log.debug("└");
		
		String message = "";
		
		if( 1== flag) {
			message = user.getUserId() + "님이 삭제에 성공하셨습니다.";
		}else {
			message = user.getUserId() + "님이 삭제에 실패하셨습니다.";
		}
		
		Message messageObj = new Message(flag, message);
		
		Gson gson = new Gson();
		jsonString = gson.toJson(messageObj);
		log.debug("┌");
		log.debug("│ jsonString : " + jsonString);
		log.debug("└");
		
		return jsonString;
	}
	
	@RequestMapping(value="/doSelectOne.do", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody //본문 (body)에 직접 입력
	public String doSelectOne(User inVO) throws SQLException{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserController : doSelectOne()           │");
		log.debug("└──────────────────────────────────────────┘");
		String jsonString = "";
		log.debug("┌");
		log.debug("│ 1. param : " + inVO);
		log.debug("└");
		
		User outVO = userService.doSelectOne(inVO);
		
		String message = "";
		int flag = 0;
		
		if(null != outVO) {
			message = inVO.getUserId() + "님의 조회가 성공했습니다.";
			flag = 1;
		}else {
			message = inVO.getUserId() + "님의 조회가 실패했습니다.";
		}
		
		jsonString = new Gson().toJson(new Message(flag, message));
		log.debug("┌");
		log.debug("│ jsonString : " + jsonString);
		log.debug("│ flag : " + flag);
		log.debug("│ message : " + message);
		log.debug("└");
		
		return jsonString;
	}
	
	
	@RequestMapping(value="/doSave.do", method= RequestMethod.POST, produces = "text/plain;charset=UTF-8") //produces : 화면으로 다시 보낼 때의 인코딩
	@ResponseBody
	public String doSave(User user)throws SQLException	{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserController : doSave()                │");
		log.debug("└──────────────────────────────────────────┘");
		
		String jsonString = "";
		//1.
		log.debug("┌");
		log.debug("│ user : " + user);
		log.debug("└");
		
		int flag = userService.doSave(user);
		log.debug("┌");
		log.debug("│ flag : " + flag);
		log.debug("└");
		//프론트로 던져줄 메세지
		String message = "";
		
		if( 1== flag) {
			message = user.getUserId() + "님이 등록에 성공하셨습니다.";
		}else {
			message = user.getUserId() + "님이 등록에 실패하셨습니다.";
		}
		
		Message messageObj = new Message(flag, message);
		
		Gson gson = new Gson();
		jsonString = gson.toJson(messageObj);
		log.debug("┌");
		log.debug("│ jsonString : " + jsonString);
		log.debug("└");
		return jsonString;
		
	}
}
