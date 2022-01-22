<!-- 예약 도착처리 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		try {
			int oid=Integer.parseInt(request.getAttribute("oid").toString());
			userId=request.getAttribute("userId").toString();
			Time time=new Time(System.currentTimeMillis());
			
			url=new URL(api_url+"api/customer/find/"+userId);	// API로 고객 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonObj=(JSONObject) jsonPar.parse(br);
			int arrivalCount=Integer.parseInt(jsonObj.get("arrivalCount").toString());
			
			url=new URL(api_url+"api/customer/update");			// API로 고객 정보 업데이트
			conn=(HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			String json="{\"userId\":\""+userId+"\",\"arrivalCount\":"+(arrivalCount+1)+"}";	// json 형태로 전송
			os=conn.getOutputStream();
			input=json.getBytes();
			os.write(input,0,input.length);
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			url=new URL(api_url+"api/reservation/find/"+oid);	// API로 예약 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonObj=(JSONObject) jsonPar.parse(br);
			json="{\"oid\":"+oid		// json 형태로 전송
					+",\"covers\":"+jsonObj.get("covers").toString()
					+",\"date\":\""+jsonObj.get("date").toString()
					+"\",\"time\":\""+jsonObj.get("time").toString()
					+"\",\"table_id\":"+jsonObj.get("table_id").toString()
					+",\"customer_id\":"+jsonObj.get("customer_id").toString()
					+",\"arrivalTime\":\""+time.toString()
					+"\"}";
					
			url=new URL(api_url+"api/reservation/update");		// API로 예약 정보 업데이트
			conn=(HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			os=conn.getOutputStream();
			input=json.getBytes();
			os.write(input,0,input.length);
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('도착 처리되었습니다.')");
			script.println("location.href = 'main'");
			script.println("</script>");
		} catch(Exception e){
			e.printStackTrace();
		}
	%>
</body>
</body>
</html>