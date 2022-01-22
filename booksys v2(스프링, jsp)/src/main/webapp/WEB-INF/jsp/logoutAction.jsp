<!-- 로그아웃 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jsp 게시판 웹사이트</title>
</head>
<body>
	<%
		session.invalidate(); 		// 접속한 회원의 세션을 빼앗음
	%>
	<script>	
		location.href = 'login';	//로그인 페이지로 이동
	</script>
</body>
</body>
</html>