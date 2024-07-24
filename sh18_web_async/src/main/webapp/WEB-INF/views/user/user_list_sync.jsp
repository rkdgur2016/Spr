<%--
/**
    Class Name: user_list.jsp
    Description:
    Author : acorn
    Modification information
    
    수정일                        수정자      수정내용
    -----         -----  -------------------------------------------
    2024. 7. 18          최초작성 
    
    author eclass 개발팀
    since 2020.11.23
    Copyright (C) by KandJang All right reserved.
*/
 --%>
<%@page import="com.pcwk.ehr.cmn.Search"%>
<%@page import="com.pcwk.ehr.code.domain.Code"%>
<%@page import="java.util.List"%>
<%@page import="com.pcwk.ehr.cmn.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/png" href="${CP}/resources/img/favicon.ico">

<!-- bootstrap start -->
<link rel="stylesheet" href="${CP}/resources/css/bootstrap.css">
<!-- bootstrap end -->

<!-- jquery start -->
<script src="${CP}/resources/js/jquery_3_7_1.js"></script> 

<!-- jquery end -->
<script src="${CP}/resources/js/common.js"></script> 

<!-- user.js -->
<script src="${CP}/resources/js/user/user.js"></script>
<title>감염된 장민수</title>
</head>
<body>
<!-- container -->
<div class="container">
  <!-- 제목 -->
  <div class="page-header  mb-4">
    <h2>회원관리</h2>
  </div>
  <!--// 제목 end ------------------------------------------------------------->
  <!-- 검색 -->
    <form action="#" class="row g-2 align-items-center" id="userForm" method="get">
        <input type="hidden" id="pageNo" name="pageNo">
        <div class="col-sm-2">
        </div>
        <div class="col-sm-2 text-end g-2">
            <label for="searchDiv" class="form-label ">검색조건</label>
        </div>
        <div class="col-sm-3">
            <select name="searchDiv" class="form-select" id="searchDiv">
	            <option value="">전체</option>
	            <c:forEach var = "item" items="${MEMBER_SEARCH}">
		            <option value="${item.detCode}" <c:if test="${item.detCode == search.searchDiv }">selected</c:if> >${item.detNm}</option>
	            </c:forEach>
            </select>
        </div>
        <div class="col-sm-3">
            <input type="search" name="searchWord" class="form-control" id="searchWord"
             placeholder="검색어" value='<c:out value="${search.searchWord }"></c:out>'>
        </div>
        <div class="col-sm-2">
            <select name="pageSize" id="pageSize" class="form-select">
                <c:forEach var = "item" items="${COM_PAGE_SIZE}">
                    <option value="${item.detCode}" <c:if test="${item.detCode == search.pageSize }">selected</c:if> >${item.detNm}</option>
                </c:forEach>
            </select>
        </div> 
    </form>
  <!--// 검색 end ------------------------------------------------------------->
  <!-- 버튼 -->
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="button" value="조회" id="doRetrieve" class="btn btn-primary">
  </div>
  <!--// 버튼 ----------------------------------------------------------------->
  
  <!-- table -->
    <table id="userTable" class="table table-striped table-hover table-bordered">
      <thead >
        <tr class="table-success">
          <th class="text-center col-sm-1">NO.</th>
          <th class="text-center col-sm-2">아이디</th>
          <th class="text-center col-sm-2">이름</th>
          <th class="text-center col-sm-2">비밀번호</th>
          <th class="text-center col-sm-1">생년월일</th>
          <th class="text-center col-sm-3">이메일</th>
          <th class="text-center col-sm-1">등록일</th>
        </tr>
      </thead>  
      <tbody>
          <c:choose>
	          <%-- <c:when test="${not empty list}"> not empty로 하는 방법 --%>
	          <c:when test="${list.size()>0}">
                 <c:forEach var="item" items="${list}">                    
                      <tr>
			            <%-- c:out을 사용하지 않아도 ${}문만으로도 출력이 가능하나 오류가 발생하지않도록 모두 문자열로 출력하기 위해 c:out을 사용한다. --%>
                        <td class="text-center"><c:out value="${item.no}"/></td>
                        <td class="text-center"><c:out value="${item.userId}"/></td>
                        <td class="text-center"><c:out value="${item.name}"/></td>
                        <td class="text-center"><c:out value="${item.password}"/></td>
                        <td class="text-center"><c:out value="${item.birthday}"/></td>
                        <td class="text-center"><c:out value="${item.email}"/></td>
                        <td class="text-center"><c:out value="${item.regDt}"/></td>
                      </tr>                    
                  </c:forEach> 
		      </c:when>
	          <c:otherwise>
	            <tr>
	              <td colspan="99" class="text-center">조회된 데이터가 없습니다.</td>
	            </tr>
	          </c:otherwise>
          </c:choose>	      
      </tbody>
     </table> 
  <!--// table end ------------------------------------------------------------->
  
  <!-- pagenation -->
  <div class="text-center">
    <div id="page-selection" class="text-center page">
  <%
    //총글수 :
    int maxNum = (int) request.getAttribute("totalCnt");
  
    Search search = (Search) request.getAttribute("search");
    //페이지 번호:
    int currentPageNo = search.getPageNo();
    //페이지 사이즈:
    int rowPerPage = search.getPageSize();
    //바닥글 :
    int bottomCount = search.BOTTOM_COUNT;
    
    String url = "/ehr/user/doRetrieve.do";
    String scriptName = "pageRetrieve";
    
    out.print(StringUtil.renderingPaging(maxNum, currentPageNo, rowPerPage, bottomCount, url, scriptName));
  %>
  
    </div>
  </div> 
  <!--// pagenation -->
  
  <!-- 관리 button -->
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="button" value="초기화" id="initBtn" class="btn btn-primary">
      <input type="button" value="등록" id="doSave" class="btn btn-primary">
      <input type="button" value="수정" id="doUpdate" class="btn btn-primary">
      <input type="button" value="삭제" id="doDelete" class="btn btn-primary">
  </div>
  <!--// 관리 button -->
 
  <!-- form -->
  <form action="#" id="userForm" class="form-horizontal">
    <div class="row mb-2">
	    <label for="userId" class="col-sm-2 col-form-label">아이디</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" name="userId" id="userId"  maxlength="20" required="required">
	    </div> 
	    <div class="col-sm-2">
          <input type="button" value="ID중복체크" id="idDuplicateCheck" class="btn btn-info">
        </div> 
    </div>
    
    <div class="row mb-2">
	    <label for="name" class="col-sm-2 col-form-label">이름</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" name="name" id="name"  maxlength="10" required="required">
	    </div> 
    </div>
    
    <div class="row mb-2">
	    <label for="password" class="col-sm-2 col-form-label">비밀번호</label>
	    <div class="col-sm-10">
	      <input type="password" class="form-control" name="password" id="password"  maxlength="100" required="required">
	    </div>  
	</div>  
    
    <div class="row mb-2">
	    <label for="birthday" class="col-sm-2 col-form-label">생년월일</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" name="birthday" id="birthday"  maxlength="10" required="required">
	    </div> 
    </div>   
    
    <div class="row mb-2">
        <label for="level" class="col-sm-2 col-form-label">등급</label>
        <div class="col-sm-3">
        <select name="level" class="form-select" id="level">
            <c:forEach var = "item" items="${MEMBER_LEVEL}">
                <option value="${item.detCode}" <c:if test="${item.detCode == search.searchDiv }">selected</c:if> >${item.detNm}</option>
            </c:forEach>
        </select>
        </div> 
    </div>   
    
    <div class="row mb-2">
	    <label for="login" class="col-sm-2 col-form-label">로그인횟수</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" name="login" id="login"  maxlength="8" required="required">
	    </div>    
    </div> 
    
    <div class="row mb-2">
	    <label for="recommend" class="col-sm-2 col-form-label">추천</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" name="recommend" id="recommend"  maxlength="8" required="required">
	    </div> 
    </div>    
    
    <div class="row mb-2">
	    <label for="email" class="col-sm-2 col-form-label">이메일</label>
	    <div class="col-sm-10">
	      <input type="email" class="form-control" name="email" id="email"  maxlength="320" required="required">
	    </div> 
    </div> 
    
    <div class="row mb-2">
	    <label for="regDt" class="col-sm-2 col-form-label">생성일</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" name="regDt" id="regDt"  maxlength="20" required="required" >
	    </div>         
    </div>
   </form>
  <!--// form -->
  
</div>
<!--// container end ---------------------------------------------------------->
<script src="${CP}/resources/js/bootstrap.bundle.min.js"></script> 
</body>
</html>