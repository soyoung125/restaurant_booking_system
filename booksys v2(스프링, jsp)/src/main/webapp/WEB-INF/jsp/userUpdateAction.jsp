<!-- 고객 업데이트 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		if (password == "" || phoneNumber == ""){	// 입력 안 된 사항 있을 경우 접근 불가
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else{
			try {
				url=new URL(api_url+"api/customer/find/"+userId);	// API로 고객 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				jsonObj=(JSONObject) jsonPar.parse(br);
				String passwordCheck=jsonObj.get("password").toString();
				if(!BCrypt.checkpw(password, passwordCheck)){		// 비밀번호가 일치하지 않을 경우 업데이트 불가
					script = response.getWriter();
					script.println("<script>");
					script.println("alert('비밀번호가 일치하지 않습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
				else{
					url=new URL(api_url+"api/customer/update");		// API로 고객 정보 업데이트
					conn=(HttpURLConnection)url.openConnection();
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					String json="{\"userId\":\""+userId+"\",\"phoneNumber\":\""+phoneNumber+"\"}";	// json 형태로 전송
					os=conn.getOutputStream();
					input=json.getBytes();
					os.write(input,0,input.length);
					br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
					script = response.getWriter();
					script.println("<script>");
					script.println("alert('수정되었습니다.')");
					script.println("location.href='main'");
						script.println("</script>");
				}
			} catch(Exception e) {}
		}
	%>
</body>
</body>
</html>