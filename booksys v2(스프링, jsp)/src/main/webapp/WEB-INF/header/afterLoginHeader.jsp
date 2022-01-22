<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ include file="../header/variableHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width" initial-scale="1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<title>Booking System</title>
</head>
<body>
	<%@ include file="../header/loginCheck.jsp" %>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" 
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expaned="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main">Booking Systems</a>
		</div>
		<div class="collapse navbar-collapse" id="#bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main">메인</a></li>
				<li><a href="calendar">예약</a></li>
				<li><a href="mypage">마이페이지</a></li>
				<%
					try {
						url=new URL(api_url+"api/customer/find/"+userId);						// API로 userId에 해당되는 등급 가져옴 
						conn=(HttpURLConnection)url.openConnection();							// grade -> 0:관리자, 1-5:일반 사용자, 6 이상:차단
						br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
						jsonObj=(JSONObject) jsonPar.parse(br);
						String grade=jsonObj.get("grade").toString();
						if(grade.equals("0")){													// 등급이 관리자일 경우 관리자 메뉴 표시
							%>
							<li><a href="userManager">유저 관리</a></li>
							<li><a href="tableManager">테이블 관리</a></li>
							<%
						}
				%>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="userUpdate">정보수정</a></li>
				<li><a href="userPasswordUpdate">비밀번호변경</a></li>
				<li><a href="logoutAction">로그아웃</a></li>
			</ul>
				</li>
			</ul>
		</div> 
	</nav>	
	<%
		Date today=new Date(System.currentTimeMillis());		// 오늘 날짜 저장
	%>