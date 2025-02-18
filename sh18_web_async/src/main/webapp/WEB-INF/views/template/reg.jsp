<%--
/**
	Class Name: 
	Description: bootstrap template
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
<title>감염된 장민수</title>
</head>
<body>
<!-- container -->
<div class="container">
  
  <!-- 제목 -->
  <div class="page-header">
    <h2>게시-등록</h2>
  </div>
  <!--// 제목 end ------------------------------------------------------------->
  
  <!-- 버튼 -->
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="button" value="목록" class="btn btn-primary">
      <input type="button" value="등록" class="btn btn-primary">
  </div>
  <!--// 버튼 ----------------------------------------------------------------->
  
  <!-- form -->
  <form action="#" class="form-horizontal">
    <div class="row mb-3">
        <label for="title" class="col-sm-2 col-form-label">제목</label>
        <div class="col-sm-10">
          <input type="text" class="form-control" name="title" id="title"  maxlength="75" required="required">
        </div>      
    </div>
    <div class="row mb-3">
        <label for="regId" class="col-sm-2 col-form-label">등록자</label>
        <div class="col-sm-10">
          <input type="text" class="form-control" name="regId" id="regId"  maxlength="20" required="required">        
        </div>      
    </div>    
    <div class="row mb-3"">
        <label for="regId" class="col-sm-2 col-form-label">내용</label>
        <div class="col-sm-10">    
         <textarea style="height: 200px"  class="form-control" id="contents" name="contents"></textarea>
        </div> 
    </div>    
    
  </form>
  
  <!--// form end -->
</div>
<!--// container end ---------------------------------------------------------->
<script src="${CP}/resources/js/bootstrap.bundle.min.js"></script> 
</body>
</html>