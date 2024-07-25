<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Haha-HoHo</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
    <h3>나다 오태식</h3>
    
    <form action="/ehr/sync/sync_result.do" method="POST">
        <label for = "name">이름 : 오태식</label>
        <input type = "text" id = "name" name = "name">
        <input type = "submit" value = "전송">
    </form>
    
</body>
</html>