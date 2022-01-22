<!-- 예약 인원 선택 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<%
		int table=Integer.parseInt(request.getAttribute("table").toString());
		String date=request.getAttribute("date").toString();
		String time=request.getAttribute("time").toString();
		
		url=new URL(api_url+"api/table/find/"+table);	// API로 선택된 테이블 정보 가져옴
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
				<form method="GET" action="reservationAddAction">
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
						for(int i=0;i<places;i++){	// 테이블의 최대 인원수만큼 옵션 생성
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