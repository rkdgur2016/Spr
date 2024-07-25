package com.pcwk.ehr.board.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;
import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.code.service.CodeService;

@Controller
@RequestMapping("board")
public class BoardController implements PLog {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	CodeService codeService;
	
	public BoardController() {
		log.debug("┌────────────────────────────────────────────");
		log.debug("│ BoardController()");
		log.debug("└────────────────────────────────────────────");
	}
	
	@RequestMapping(value="/doUpdate.do"
			,method = RequestMethod.POST
			,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(Board inVO) throws SQLException {
		String jsonString = "";

		log.debug("1. param : " + inVO);
		
		int flag = boardService.doUpdate(inVO);
		log.debug("2. flag : " + flag);
		
		String message = "";
		if(1 == flag) {
			message = inVO.getTitle() + "이 수정되었습니다.";
		}else {
			message = "수정에 실패했습니다";
		}
		
		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3. jsonString : " + jsonString);
		
		return jsonString;
	}
	
	
	@RequestMapping(value="/doRetrieve.do"
			,method = RequestMethod.GET)
	public String doRetrieve(Model model, HttpServletRequest req) throws SQLException {
		log.debug("┌──────────────────────────────────────────────");
		log.debug("│ doRetrieve()");
		log.debug("└──────────────────────────────────────────────");	
		String viewName = "board/board_list";
		
		Search search = new Search();
		
		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");
		String pageSize = StringUtil.nvl(req.getParameter("pageSize"), "10");
		String pageNo = StringUtil.nvl(req.getParameter("pageNo"), "1");
		
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);
		search.setPageSize(Integer.parseInt(pageSize));
		search.setPageNo(Integer.parseInt(pageNo));
		
		log.debug("1. param search : " + search);
		List<Board> list = this.boardService.doRetrieve(search);
		
		
		model.addAttribute("list", list); 		// 조회 데이터
		model.addAttribute("search", search); 	// 검색 조건
		
		int totalCnt = 0;
		if(null != list && list.size() > 0) {
			Board firstVO = list.get(0);
			totalCnt = firstVO.getTotalCnt();
		}
		model.addAttribute("totalCnt",totalCnt);
		
		Code code = new Code();
		code.setMsgCode("BOARD_SEARCH"); // 검색 조건		
		List<Code> memberSearch = this.codeService.doRetrieve(code);		
		model.addAttribute("BOARD_SEARCH", memberSearch); // 검색조건
		
		code.setMsgCode("COM_PAGE_SIZE"); // 페이지 사이즈
		List<Code> pageSizeSearch = this.codeService.doRetrieve(code);
		model.addAttribute("COM_PAGE_SIZE", pageSizeSearch); // 검색조건
		
		
		return viewName;
	}
	
	@RequestMapping(value="/doDelete.do"
			,method = RequestMethod.GET
			,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(Board inVO) throws SQLException {
		String jsonString = "";
		
		log.debug("1. param : " + inVO);
		
		int flag = boardService.doDelete(inVO);
		log.debug("2. flag : " + flag);
		
		String message = "";
		if(1 == flag) {
			message = inVO.getSeq() + "이 삭제되었습니다.";
		}else {
			message = inVO.getSeq() + "삭제에 실패했습니다.";
		}
		
		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3. jsonString : " + jsonString);
		
		return jsonString;
	}
	
	//@PostMapping("/doSave.do")
	@RequestMapping(value="/doSave.do"
					,method = RequestMethod.POST
					,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(Board inVO) throws SQLException {
		String jsonString ="";
		
		log.debug("1. param inVO : " +inVO);
		
		int flag = boardService.doSave(inVO);
		log.debug("2. flag : " + flag);
		
		String message = "";
		if(1 == flag) {
			message =inVO.getTitle() + "을 등록했습니다.";
		}else {
			message = "등록에 실패했습니다.";
		}
		
		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3. jsonString : " + jsonString);
		
		return jsonString;
	}
	
	@RequestMapping(value="/doSelectOne.do"
			,method = RequestMethod.GET
			,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSelectOne(Board inVO)throws SQLException {
		
		String jsonString = "";
		
		log.debug("1. param inVO : " + inVO);
		Board outVO = boardService.doSelectOne(inVO);
		
		log.debug("2. outVO : " + outVO);
		
		String message = "";
		int flag = 0;
		
		if(null != outVO) {
			message = outVO.getTitle() + "이 조회되었습니다.";
			flag = 1;
		}else {
			message = "조회에 실패했습니다. 다시 시도하세요.";
		}
		
		//message
		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3. jsonString : " + jsonString);
		
		//board
		String jsonBoard = new GsonBuilder().setPrettyPrinting().create().toJson(outVO);
		log.debug("4. jsonBoard : " + jsonBoard);
		
		String allMessage = "{\"board\" : "+jsonBoard + ",\"message\": "+jsonString+"}";
		
		return allMessage;
	}
	
}
