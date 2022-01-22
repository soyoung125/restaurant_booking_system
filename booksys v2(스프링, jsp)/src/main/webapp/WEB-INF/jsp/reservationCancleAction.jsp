<!-- 예약 취소 백엔드 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/actionHeader.jsp" %>
<%@ include file="../header/loginCheck.jsp" %>
	<%
		try {
			String[] value=request.getParameterValues("reservation");
			for(String v:value){
				url=new URL(api_url+"api/reservation/find/"+v);		// API로 예약 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				jsonObj=(JSONObject) jsonPar.parse(br);
				Object arrivalTime=jsonObj.get("arrivalTime");
				
				if(arrivalTime !=null && value.length==1){			// 도착 처리된 예약 예외처리
					script = response.getWriter();
					script.println("<script>");
					script.println("alert('도착 처리된 예약은 취소할 수 없습니다.')");
					script.println("location.href='mypage'");
					script.println("</script>");
				}
				
				if(arrivalTime!=null) continue;						// 도착 처리된 예약 예외처리
				
				url=new URL(api_url+"api/reservation/delete/"+v);	// API로 선택된 예약 정보 삭제
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			
			url=new URL(api_url+"api/customer/find/"+userId);		// API로 고객 정보 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			jsonObj=(JSONObject) jsonPar.parse(br);
			int reservationCount=Integer.parseInt(jsonObj.get("reservationCount").toString());
			
			url=new URL(api_url+"api/customer/reservationCount/update");		// API로 고객의 reservationCount 업데이트
			conn=(HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			String json="{\"userId\":\""+userId+"\",\"reservationCount\":"+(reservationCount-1)+"}";	// json 형태로 전송
			os=conn.getOutputStream();
			input=json.getBytes();
			os.write(input,0,input.length);
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('취소되었습니다.')");
			script.println("location.href='mypage'");
			script.println("</script>");
		} catch(Exception e){
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('1개 이상 선택해주세요.')");
			script.println("location.href='mypage'");
			script.println("</script>");
		}
	%>
</body>
</body>
</html>