<!-- 워크인 추가 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		try {
			int covers=Integer.parseInt(request.getAttribute("covers").toString());
			int table=Integer.parseInt(request.getAttribute("table").toString());
			String date=request.getAttribute("date").toString();
			String time=request.getAttribute("time").toString();
			time=time.replace("%3A", ":");
			url=new URL(api_url+"api/oid/find");			// API로 oid 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonObj=(JSONObject) jsonPar.parse(br);
			int oid=Integer.parseInt(jsonObj.get("last_id").toString());
			oid=oid+1;
			
			url=new URL(api_url+"api/table/find/"+table);	// API로 테이블 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonPar=new JSONParser();
			jsonObj=(JSONObject) jsonPar.parse(br);
			int table_id=Integer.parseInt(jsonObj.get("oid").toString());
			
			url=new URL(api_url+"api/walkin/save");			// API로 워크인 정보 저장
			conn=(HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			String json="{\"oid\":"+oid		// json 형태로 전송
				+",\"covers\":"+covers
				+",\"date\":\""+date
				+"\",\"time\":\""+time
				+"\",\"table_id\":"+table_id+"}";
			os=conn.getOutputStream();
			input=json.getBytes();
			os.write(input,0,input.length);
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			url=new URL(api_url+"api/oid/update");			// API로 oid 정보 업데이트
			conn=(HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			json="{\"last_id\":"+oid+"}";	//json 형태로 전송
			os=conn.getOutputStream();
			input=json.getBytes();
			os.write(input,0,input.length);
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));				
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('처리되었습니다.')");
			script.println("location.href = 'main'");
			script.println("</script>");
		} catch(Exception e){
			e.printStackTrace();
		}
	%>
</body>
</body>
</html>