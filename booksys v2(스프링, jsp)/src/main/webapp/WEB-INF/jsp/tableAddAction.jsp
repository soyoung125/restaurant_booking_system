<!-- 테이블 추가 백엔드 -->

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
			if(grade != 0){		// 관리자가 아닐 경우 접근 불가
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('관리자가 아닙니다.')");
				script.println("location.href='main'");
				script.println("</script>");
			}
			
			url=new URL(api_url+"api/table/list");	// API로 테이블 리스트 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonPar=new JSONParser();
			JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			jsonObj=(JSONObject) jsonArray.get(jsonArray.size()-1);
			int oid=Integer.parseInt(jsonObj.get("oid").toString());
			
			url=new URL(api_url+"api/table/save");	// API로 테이블 정보 추가
			conn=(HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			String json="{\"number\":"+(oid+1)+",\"places\":4}";	// json 형태로 전송
			os=conn.getOutputStream();
			input=json.getBytes();
			os.write(input,0,input.length);
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('추가되었습니다.')");
			script.println("location.href='tableManager'");
			script.println("</script>");
		} catch(Exception e){}
	%>
</body>
</body>
</html>