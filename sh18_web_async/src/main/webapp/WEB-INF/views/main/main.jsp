<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="CP" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-- favicon --%>
<link rel="shortcut icon" href="${CP}/resources/img/favicon.ico" type="image/x-icon">

<%-- bootstrap css --%>
<link rel="stylesheet" href="${CP}/resources/css/bootstrap.css">

<%-- jquery js --%>
<script src="${CP}/resources/js/jquery_3_7_1.js"></script>

<%-- common js --%>
<script src="${CP}/resources/js/common.js"></script>

<%-- font : google nanum gothic --%>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap">

<%-- FontAwesome for icons --%>
<link rel="styleSheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<style>
	body{
		font-family: 'Nanum Gothic', sans-serif;
	}
	
	header{
		background-color : #005b9f;
		color: white;
		line-height : 50px;
	}
	
	a{
		color : white;
	}
	
	.navbar-nav .nav-link:hover{	
		background : #d26060;
		border-radius : 4px;
	}
	
	article {
		background-color : #f8f9fa;
		border : 1px solid #dee2e6;
		padding : 1rem;
		border-radius : 5px;
	}
	
	aside {
		background-color : #e9ecef;
		border : 1px solid #dee2e6;
		padding : 1rem;
		border-radius : 5px;
	}
	
	footer {
		background-color : #616161;
		color : white;
		text-align: center;
	}
	
	.content_wrap {
		min-height: calc(100vh - 200px);
	}
</style>
<title>룰루랄라</title>
</head>
<body>
	<header class="container-fluid py-2">
		<div class="container m-0">
			<h1 class="m-0">Header</h1>
		</div>
	</header>
	<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">PCWK</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="#">Home</a>
					</li>
					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="#">Member</a>
					</li>
					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="#">Board</a>
					</li>
					<li class="nav-item">
						<a class="nav-link active" href="#">Free Board</a>
					</li>
				</ul>
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="#"><i class="fab fa-twitter"></i></a>
					</li>
					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="#"><i class="fab fa-facebook"></i></a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="content_wrap container my-4" >
		<div class="row">
			<article class="col-12 col-md-9">
				<h2>Article</h2>
			</article>
			<aside class="col-12 col-md-3">
				<h2>Aside</h2>
			</aside>
		</div>
	</div>
	<footer class="container-fluid py-2">
		<div class="container">
			<p>&copy; 2024 PCWK Web Page All rights reserved.</p>
		</div>
	</footer>
<%--bootstrap js --%>
<script src="${CP}/resources/js/bootstrap.bundle.js"></script> 
</body>
</html>