<!-- 테이블 관리 페이지 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<%
		if(!grade.equals("0")){		// 관리자가 아닐 경우 접근 제한
			script = response.getWriter();
			script.println("<script>");
			script.println("alert('관리자가 아닙니다.')");
			script.println("location.href='main'");
			script.println("</script>");
		}
		url=new URL(api_url+"api/table/list");	// API로 테이블 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
	%>
	<div class="container">
		<h2>테이블 관리</h2>  
		<div class="table-responsive">
			<form method="POST">
			<input type="submit" class="pull-right" value="삭제" formaction="tableDeleteAction">
			<input type="submit" class="pull-right" value="수정" formaction="tableUpdateAction">
			<input type="submit" class="pull-right" value="추가" formaction="tableAddAction">
			<table class="table table-bordered table-hover text-center">
	    		<tbody>
	      			<tr>
						<th>#</th>
						<th>번호</th>
						<th>인원</th>
						<th rowspan="<%=jsonArray.size()+1 %>">
            				<select name="places">
            				<%
            					for(int j=1;j<11;j++){		// 테이블 인원 정보 수정(1~10)
            						%>
            						<option value="<%=j %>"><%=j %></option>
            						<%
            					}
            				%>
            				</select>
    					</th>
	      			</tr>
	    <%
			for(int i=0;i<jsonArray.size();i++){	// 테이블 정보 출력
				jsonObj=(JSONObject) jsonArray.get(i);
		%>
        <tr>
			<td><input type="checkbox" name="table" value="<%=jsonObj.get("number") %>"></td>  
            <td><%=jsonObj.get("number") %></td>
            <td><%=jsonObj.get("places") %></td>
            
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