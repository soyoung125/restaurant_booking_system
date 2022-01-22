<!-- 워크인 삭제 2(관리자 메인페이지용 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		try {
			int oid=Integer.parseInt(request.getAttribute("oid").toString());
			url=new URL(api_url+"api/walkin/delete/"+oid);	// API로 워크인 삭제
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('삭제되었습니다.')");
			script.println("location.href='main'");
			script.println("</script>");
		} catch(Exception e){}
	%>
</body>
</body>
</html>