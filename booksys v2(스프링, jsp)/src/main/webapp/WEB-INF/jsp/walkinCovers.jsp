<!-- 워크인 인원수 선택 -->

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
		url=new URL(api_url+"api/table/list");			// API로 테이블 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
		int table=Integer.parseInt(request.getAttribute("table").toString());
		String date=request.getAttribute("date").toString();
		String time=request.getAttribute("time").toString();
		
		url=new URL(api_url+"api/table/find/"+table);	// API로 테이블 정보 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		jsonObj=(JSONObject) jsonPar.parse(br);
		int places=Integer.parseInt(jsonObj.get("places").toString());
	%>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<h1><%=date %></h1>
				<h2><%=time %></h2>
				<h3><%=table %>번 테이블</h3>
				<form method="GET" action="walkinAddAction">
					<div class="form-group">
						<input type="hidden" class="form-control" name="date" value="<%=date %>" readonly>
					</div>
					<div class="form-group">
						<input type="hidden" class="form-control" name="time" value="<%=time %>" readonly>
					</div>
					<div class="form-group">
						<input type="hidden" class="form-control" name="table" value="<%=table%>" readonly>
					</div>
					<select name="covers">
					<%
						for(int i=0;i<places;i++){	// 테이블 최대 인원수만큼 옵션 생성
							%>
							<option value="<%=i+1 %>"><%=i+1 %></option>
							<%
						}
					%>
					</select>
					명
					<input type="submit" class="btn btn-primary form-control" value="선택">
				</form>
			</div>
		</div>
	</div>
<%@ include file="../header/footer.jsp" %>