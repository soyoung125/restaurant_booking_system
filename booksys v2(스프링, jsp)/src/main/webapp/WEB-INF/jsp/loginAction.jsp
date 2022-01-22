<!-- 로그인 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<jsp:useBean id="customer" class="booksys.domain.CustomerDTO" scope="page" />
<jsp:setProperty name="customer" property="userId" />
<jsp:setProperty name="customer" property="password" /> 
	<%
		try {
			if(customer.getUserId()==null || customer.getPassword()==null){		// 빈칸이 있을 경우 로그인 페이지로 이동
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			
			url=new URL(api_url+"api/customer/find/"+customer.getUserId());		// API로 유저 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonObj=(JSONObject) jsonPar.parse(br);
			String password=(String) jsonObj.get("password");
			int grade=Integer.parseInt(jsonObj.get("grade").toString());
			
			if(BCrypt.checkpw(customer.getPassword(), password) && grade<6){	// 비밀번호가 일치하고 차단되지 않았으면 main으로 이동
				session.setAttribute("userId", customer.getUserId());	// 세션 생성
				script=response.getWriter();
			}
			else if(grade>=6){	// 차단되었을 경우 로그인 불가
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('차단된 계정입니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else{				// 비밀번호가 틀렸을 경우 로그인 불가
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
		} catch(Exception e) {	// 가입하지 않은 아이디일 경우 로그인 불가
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
	%>
</body>
</body>
</html>