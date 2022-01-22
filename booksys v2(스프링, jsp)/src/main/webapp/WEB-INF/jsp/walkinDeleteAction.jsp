<!-- 워크인 삭제 1(관리자 마이페이지용) -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		try {
			String[] value=request.getParameterValues("walkin");
			for(String v:value){	// 선택된 워크인 삭제
				try {
					url=new URL(api_url+"api/walkin/delete/"+v);		// API로 워크인 삭제
					conn=(HttpURLConnection)url.openConnection();
					br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} catch(Exception e) {}
			}
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('삭제되었습니다.')");
			script.println("location.href='mypage'");
			script.println("</script>");
		} catch(Exception e){		// 1개 이상 선택 안할 경우 삭제 불가
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('1개 이상 선택해주세요.')");
			script.println("location.href='mypage'");
			script.println("</script>");}
	%>
</body>
</body>
</html>