<!-- 고객 차단 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		try {
			url=new URL(api_url+"api/customer/find/"+userId);	// API로 고객 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonObj=(JSONObject) jsonPar.parse(br);
			int grade=Integer.parseInt(jsonObj.get("grade").toString());
			if(grade != 0){			// 관리자가 아닐 경우 접근 불가
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('관리자가 아닙니다.')");
				script.println("location.href='main'");
				script.println("</script>");
			}
			String[] value=request.getParameterValues("user");
			for(String v:value){	// API로 선택된 고객 차단
				url=new URL(api_url+"api/customer/find/"+v);		// API로 고객 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				jsonObj=(JSONObject) jsonPar.parse(br);
				grade=Integer.parseInt(jsonObj.get("grade").toString());
				
				if(grade>=6 || grade==0) continue;					// 이미 차단된 고객이거나 관리자일 경우 패스
				
				url=new URL(api_url+"api/customer/update");			// API로 고객 정보 업데이트
				conn=(HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");	//json 형태로 전송
				conn.setDoOutput(true);
				String json="{\"userId\":\""+v+"\",\"grade\":"+(grade+5)+"}";	//등급 6 이상은 차단
				os=conn.getOutputStream();
				input=json.getBytes();
				os.write(input,0,input.length);
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('차단되었습니다.')");
			script.println("location.href='userManager'");
			script.println("</script>");
		} catch(Exception e){	// 선택 안 할 경우 차단 불가
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('1개 이상 선택해주세요.')");
			script.println("location.href='userManager'");
			script.println("</script>");}
	%>
</body>
</body>
</html>