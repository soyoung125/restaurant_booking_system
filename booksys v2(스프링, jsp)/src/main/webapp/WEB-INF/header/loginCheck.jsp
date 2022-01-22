<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	<%
		String userId=null;
	
		if(session.getAttribute("userId")!=null){	// 세션을 통해 userId를 가져옴
			userId=(String) session.getAttribute("userId");
		}
		
		if(userId == null){							// 세션을 통해 가져온 userId가 null일 경우 로그인 페이지로 이동
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인 되어있지 않습니다.')");
			script.println("location.href='login'");
			script.println("</script>");
		}
	%>