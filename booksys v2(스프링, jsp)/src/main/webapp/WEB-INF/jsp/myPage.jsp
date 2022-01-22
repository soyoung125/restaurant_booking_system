<!-- 마이페이지 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<div class="container" style="width:49%;height:500px;float:left;overflow:auto">
		<h2>예약</h2>  
		<div class="table-responsive">
			<form method="POST">
			<input type="submit" class="pull-right" value="취소" formaction="reservationCancleAction">
			<table class="table table-bordered table-hover text-center">
	    		<thead>
	      			<tr>
						<th>#</th>
						<th>날짜</th>
						<th>시간</th>
						<th>테이블 번호</th>
						<th>인원</th>
						<th>고객 이름</th>
						<th>고객 연락처</th>
						<th>도착 시간</th>
	      			</tr>
	    		</thead>
	    		<tbody>
	<%
			String customerOid=jsonObj.get("oid").toString();
			String oid, name, phoneNumber;
			url=new URL(api_url+"api/reservation/list");		// API로 예약 리스트 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JSONArray reservationJsonArray=(JSONArray) jsonPar.parse(br);
			JSONObject reservationJsonObj, tableJsonObj;
			String tableNumber;
			Date reservationDate;
			for(int i=0;i<reservationJsonArray.size();i++){		// 오늘 이후 예약만 테이블에 출력
				reservationJsonObj=(JSONObject) reservationJsonArray.get(i);
				reservationDate=Date.valueOf(reservationJsonObj.get("date").toString());
				if((!reservationDate.after(today)) && (!today.toString().equals(reservationDate.toString()))){		// 오늘 이전 예약은 패스
					continue;
				}
				
				url=new URL(api_url+"api/customer/find2/"+reservationJsonObj.get("customer_id").toString());		// API로 예약한 고객 정보 가져옴, 본인 예약만 출력하기 위함
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				JSONObject customerJsonObj=(JSONObject) jsonPar.parse(br.readLine());
				oid=customerJsonObj.get("oid").toString();
				name=customerJsonObj.get("name").toString();
				phoneNumber=customerJsonObj.get("phoneNumber").toString();
				
				if(grade.equals("0")){}		// 관리자일 경우 모든 예약 출력
				else if(!reservationJsonObj.get("customer_id").toString().equals(customerOid)){
					continue;
				}
				
				url=new URL(api_url+"api/table/find2/"+reservationJsonObj.get("table_id").toString());	// API로 예약된 테이블 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				tableJsonObj=(JSONObject) jsonPar.parse(br.readLine());
				tableNumber=tableJsonObj.get("number").toString();
				%>       
        <tr>
			<td><input type="checkbox" name="reservation" value="<%=reservationJsonObj.get("oid") %>"></td>  
            <td><%=reservationJsonObj.get("date") %></td>
            <td><%=reservationJsonObj.get("time") %></td>
            <td><%=tableNumber %></td>
            <td><%=reservationJsonObj.get("covers") %></td>
            <td><%=name %></td>
            <td><%=phoneNumber %></td>
            <td><%=reservationJsonObj.get("arrivalTime") %></td>
        </tr>
				<%
			}%>
	    		</tbody>
	  		</table>
			</form>
		</div>
	</div>	
	
	<div class="container" style="width:49%;height:500px;float:right;overflow:auto">
		<h2>과거 예약</h2>  
		<div class="table-responsive">
			<form>
			<input type="text" style="border: none;background-color:transparent;">
			<table class="table table-bordered table-hover text-center">
	    		<thead>
	      			<tr>
						<th>날짜</th>
						<th>시간</th>
						<th>테이블 번호</th>
						<th>인원</th>
						<th>고객 이름</th>
						<th>고객 연락처</th>
						<th>도착 시간</th>
	      			</tr>
	    		</thead>
	    		<tbody>
	<%
			url=new URL(api_url+"api/reservation/list");		// API로 예약 리스트 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			reservationJsonArray=(JSONArray) jsonPar.parse(br);
			for(int i=0;i<reservationJsonArray.size();i++){		// 과거 예약만 테이블에 출력
				reservationJsonObj=(JSONObject) reservationJsonArray.get(i);
				reservationDate=Date.valueOf(reservationJsonObj.get("date").toString());
				if((!reservationDate.before(today)) || (today.equals(reservationDate))){
					continue;
				}
				url=new URL(api_url+"api/customer/find2/"+reservationJsonObj.get("customer_id").toString());		// API로 예약한 고객 정보 가져옴, 본인 예약만 출력하기 위함
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				JSONObject customerJsonObj=(JSONObject) jsonPar.parse(br.readLine());
				oid=customerJsonObj.get("oid").toString();
				name=customerJsonObj.get("name").toString();
				phoneNumber=customerJsonObj.get("phoneNumber").toString();
				if(grade.equals("0")){}		// 관리자일 경우 모든 예약 출력
				else if(!reservationJsonObj.get("customer_id").toString().equals(customerOid)){
					continue;
				}
				url=new URL(api_url+"api/table/find2/"+reservationJsonObj.get("table_id").toString());	// API로 예약된 테이블 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				tableJsonObj=(JSONObject) jsonPar.parse(br.readLine());
				tableNumber=tableJsonObj.get("number").toString();
				%>       
        <tr> 
            <td><%=reservationJsonObj.get("date") %></td>
            <td><%=reservationJsonObj.get("time") %></td>
            <td><%=tableNumber %></td>
            <td><%=reservationJsonObj.get("covers") %></td>
            <td><%=name %></td>
            <td><%=phoneNumber %></td>
            <td><%=reservationJsonObj.get("arrivalTime") %></td>
        </tr>
				<%
			}%>
	    		</tbody>
	  		</table>
	  		</form>
		</div>
	</div>	
	<%
			if(grade.equals("0")){		// 워크인은 관리자에게만 출력
	%>
	<div class="container" style="height:500px;float:left;overflow:auto">
		<h2>워크인</h2>  
		<div class="table-responsive">
			<form method="POST">
			<input type="submit" class="pull-right" value="삭제" formaction="walkinDeleteAction">
			<table class="table table-bordered table-hover text-center">
	    		<thead>
	      			<tr>
						<th>#</th>
						<th>날짜</th>
						<th>시간</th>
						<th>테이블 번호</th>
						<th>인원</th>
	      			</tr>
	    		</thead>
	    		<tbody>
	<%
			url=new URL(api_url+"api/walkin/list");			// API로 워크인 리스트 가져옴
			conn=(HttpURLConnection)url.openConnection();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JSONArray walkinJsonArray=(JSONArray) jsonPar.parse(br);
			JSONObject walkinJsonObj;
			Date walkinDate;
			for(int i=0;i<walkinJsonArray.size();i++){		// 모든 워크인 테이블에 출력
				walkinJsonObj=(JSONObject) walkinJsonArray.get(i);
				walkinDate=Date.valueOf(walkinJsonObj.get("date").toString());
				url=new URL(api_url+"api/table/find2/"+walkinJsonObj.get("table_id").toString());	// API로 예약된 테이블 정보 가져옴
				conn=(HttpURLConnection)url.openConnection();
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				tableJsonObj=(JSONObject) jsonPar.parse(br.readLine());
				tableNumber=tableJsonObj.get("number").toString();
				%>       
        <tr>
			<td><input type="checkbox" name="walkin" value="<%=walkinJsonObj.get("oid") %>"></td>  
            <td><%=walkinJsonObj.get("date") %></td>
            <td><%=walkinJsonObj.get("time") %></td>
            <td><%=tableNumber %></td>
            <td><%=walkinJsonObj.get("covers") %></td>
        </tr>
			<%
				}
			}
			%>
	    		</tbody>
	  		</table>
			</form>
		</div>
	</div>	
<%@ include file="../header/footer.jsp" %>