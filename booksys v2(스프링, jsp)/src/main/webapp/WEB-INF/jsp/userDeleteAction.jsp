<!-- 고객 삭제 백엔드 -->

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
			JSONArray jsonArray;
			int grade=Integer.parseInt(jsonObj.get("grade").toString());
			if(grade != 0){		// 관리자가 아닐 경우 접근 불가
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('관리자가 아닙니다.')");
				script.println("location.href='main'");
				script.println("</script>");
			}
			ArrayList<Integer> oidList=new ArrayList();
			String[] value=request.getParameterValues("user");
			String customer_id;
			for(String v:value){	// API로 선택된 고객 삭제
				url=new URL(api_url+"api/customer/find/"+v);		// API로 고객 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				jsonObj=(JSONObject) jsonPar.parse(br);
				customer_id=jsonObj.get("oid").toString();
				grade=Integer.parseInt(jsonObj.get("grade").toString());
				
				if(grade==0) continue;								// 관리자일 경우 패스
				
				url=new URL(api_url+"api/customer/delete/"+v);		// API로 고객 정보 삭제
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				url=new URL(api_url+"api/reservation/list");		// API로 예약 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				jsonArray=(JSONArray) jsonPar.parse(br);
				for(int i=0;i<jsonArray.size();i++){				// 삭제된 고객이 예약한 예약의 oid를 ArrayList에 저장
					jsonObj=(JSONObject) jsonArray.get(i);
					if(jsonObj.get("customer_id").toString().equals(customer_id)){
						oidList.add(Integer.parseInt(jsonObj.get("oid").toString()));
					}
				}
			}
			for(int oid:oidList){	// 삭제된 고객의 예약 삭제
				url=new URL(api_url+"api/reservation/delete/"+oid);	// API로 예약 삭제
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('삭제되었습니다.')");
			script.println("location.href='userManager'");
			script.println("</script>");
		} catch(Exception e){		// 1개 이상 선택하지 않았을 경우 삭제 불가
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('1개 이상 선택해주세요.')");
			script.println("location.href='userManager'");
			script.println("</script>");}
	%>
</body>
</body>
</html>