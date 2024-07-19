<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<meta charset="UTF-8">
<title>sync_index</title>
</head>
<body>
	<h2>Sync-Test</h2>
	<form action="/ehr/sync/sync_result.do" method="get">
		<label for="name">이름 : </label>
		<input type="text" id="name" name="name">
		<input type="submit" value="전송">
	</form>
</body>
</html>