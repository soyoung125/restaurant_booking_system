<!-- 테이블 업데이트 백엔드 -->

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
			String[] value=request.getParameterValues("table");
			String[] places=request.getParameterValues("places");
			for(String v:value){	// API로 선택된 테이블 정보 업데이트
				url=new URL(api_url+"api/table/update/"+v+"/"+places[0]);
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('수정되었습니다.')");
			script.println("location.href='tableManager'");
			script.println("</script>");
		} catch(Exception e){		// 선택된 테이블이 없을 경우 업데이트 불가
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('1개 이상 선택해주세요.')");
			script.println("location.href='tableManager'");
			script.println("</script>");}
	%>
</body>
</body>
</html>