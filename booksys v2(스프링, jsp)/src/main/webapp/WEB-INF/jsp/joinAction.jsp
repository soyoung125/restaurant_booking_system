<!-- 회원가입 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<jsp:useBean id="customer" class="booksys.domain.CustomerDTO" scope="page" />
<jsp:setProperty name="customer" property="userId" />
<jsp:setProperty name="customer" property="password" /> 
<jsp:setProperty name="customer" property="name" />
<jsp:setProperty name="customer" property="phoneNumber" />
	<%
		String passwordCheck = request.getParameter("passwordCheck");
	
		// 입력 안 한 사항이 있는지 확인
		if (customer.getUserId() == null || customer.getPassword() == null || customer.getName() == null	
			|| customer.getPhoneNumber() == null || passwordCheck == null){
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		
		// 비밀번호와 비밀번호 확인이 같은지 확인
		else if(!customer.getPassword().equals(passwordCheck)){		
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 일치하지 않습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else{
			try {
				url=new URL(api_url+"api/customer/find/"+customer.getUserId());		// API로 유저 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				jsonObj=(JSONObject) jsonPar.parse(br);
				
				script = response.getWriter();						// API로 가져온 유저 정보가 있을 경우 회원가입 페이지로 이동
				script.println("<script>");
				script.println("alert('이미 있는 아이디입니다.')");
				script.println("history.back()");
				script.println("</script>");
			} catch(Exception e){
				url=new URL(api_url+"api/customer/save");			// 가져온 유저 정보가 없을 경우 API로 유저 정보 저장
				conn=(HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				String json="{\"userId\":\""+customer.getUserId()	// json 형식으로 전송
					+"\",\"password\":\""+customer.getPassword()
					+"\",\"name\":\""+customer.getName()
					+"\",\"phoneNumber\":\""+customer.getPhoneNumber()
					+"\",\"grade\":1}";
				os=conn.getOutputStream();
				input=json.getBytes();
				os.write(input,0,input.length);
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));					
				script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'login'");
				script.println("</script>");
			}
		}

	%>
</body>
</body>
</html>