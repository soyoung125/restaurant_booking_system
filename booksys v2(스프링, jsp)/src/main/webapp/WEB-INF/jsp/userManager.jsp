<!-- 고객 관리 페이지 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<div class="container">
		<h2>유저 관리</h2>  
		<div class="table-responsive">
			<form method="POST">
			<input type="submit" class="pull-right" value="삭제" formaction="userDeleteAction">
			<input type="submit" class="pull-right" value="차단 해제" formaction="userUnblockAction">
			<input type="submit" class="pull-right" value="차단" formaction="userBlockAction">
			<table class="table table-bordered table-hover text-center">
	    		<thead>
	      			<tr>
						<th>#</th>
						<th>아이디</th>
						<th>이름</th>
						<th>전화</th>
						<th>예약 횟수</th>
						<th>도착 횟수</th>
						<th>등급</th>
						<th>차단 여부</th>
	      			</tr>
	    		</thead>
	    		<tbody>
	<%
			if(!grade.equals("0")){		// 관리자가 아닐 경우 접근 제한
				script = response.getWriter();
				script.println("<script>");
				script.println("alert('관리자가 아닙니다.')");
				script.println("location.href='main'");
				script.println("</script>");
			}
			url=new URL(api_url+"api/customer/list");	// API로 고객 리스트 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			for(int i=0;i<jsonArray.size();i++){		// 고객 정보 출력
				jsonObj=(JSONObject) jsonArray.get(i);
				%>       
        <tr>
			<td><input type="checkbox" name="user" value="<%=jsonObj.get("userId") %>"></td>  
            <td><%=jsonObj.get("userId") %></td>
            <td><%=jsonObj.get("name") %></td>
            <td><%=jsonObj.get("phoneNumber") %></td>
            <td><%=jsonObj.get("reservationCount") %></td>
            <td><%=jsonObj.get("arrivalCount") %></td>
            <td><%=jsonObj.get("grade") %></td>
            <td><%if(Integer.parseInt(jsonObj.get("grade").toString())>=6){%>차단<%}%></td>
        </tr>
			<%
			}
    		%>
	    		</tbody>
	  		</table>
			</form>
		</div>
	</div>	
<%@ include file="../header/footer.jsp" %>