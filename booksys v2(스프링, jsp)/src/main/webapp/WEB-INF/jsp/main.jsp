<!-- 메인페이지 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
		<%
		
		if(!grade.equals("0")){								// 관리자가 아닐 경우 mypage로 리다이렉트
			%>
			<meta http-equiv="refresh" content="0; url=mypage"></meta>
			<%
		}
		url=new URL(api_url+"api/table/list");				// API로 table 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray tableArray=(JSONArray) jsonPar.parse(br);
		JSONObject tableObj;
		
		url=new URL(api_url+"api/reservation/list");		// API로 예약 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray reservationArray=(JSONArray) jsonPar.parse(br);
		JSONObject reservationObj;
		String reservationDate, reservationTime;
		Time tmpTime;
		int hour, min;
		ArrayList<Integer> reservationOidList=new ArrayList();
		ArrayList<Time> reservationTimeList=new ArrayList();
		ArrayList<Integer> reservationTableList=new ArrayList();
		ArrayList<Integer> reservationCustomerList=new ArrayList();
		
		for(int i=0;i<reservationArray.size();i++){			// 오늘 날짜 예약 정보들을 ArrayList에 저장
			reservationObj=(JSONObject) reservationArray.get(i);
			reservationDate=reservationObj.get("date").toString();
			if(today.toString().equals(reservationDate)){
				reservationOidList.add(Integer.parseInt(reservationObj.get("oid").toString()));
				reservationTime=reservationObj.get("time").toString();
				hour=Integer.parseInt(reservationTime.split(":")[0]);
				min=Integer.parseInt(reservationTime.split(":")[1]);
				for(int j=0;j<4;j++){
					tmpTime=new Time(hour, min+30*j, 0);
					reservationTimeList.add(tmpTime);
				}
				reservationTableList.add(Integer.parseInt(reservationObj.get("table_id").toString()));
				reservationCustomerList.add(Integer.parseInt(reservationObj.get("customer_id").toString()));
			}
		}
		
		url=new URL(api_url+"api/walkin/list");				// API로 워크인 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray walkinArray=(JSONArray) jsonPar.parse(br);
		JSONObject walkinObj;
		
		String walkinDate, walkinTime;
		ArrayList<Integer> walkinOidList=new ArrayList();
		ArrayList<Time> walkinTimeList=new ArrayList();
		ArrayList<Integer> walkinTableList=new ArrayList();
		
		for(int i=0;i<walkinArray.size();i++){				// 오늘 날짜 워크인 정보들을 ArrayList에 저장
			walkinObj=(JSONObject) walkinArray.get(i);
			walkinDate=walkinObj.get("date").toString();
			if(today.toString().equals(walkinDate)){
				walkinOidList.add(Integer.parseInt(walkinObj.get("oid").toString()));
				walkinTime=walkinObj.get("time").toString();
				hour=Integer.parseInt(walkinTime.split(":")[0]);
				min=Integer.parseInt(walkinTime.split(":")[1]);
				for(int j=0;j<4;j++){
					tmpTime=new Time(hour, min+30*j, 0);
					walkinTimeList.add(tmpTime);
				}
				walkinTableList.add(Integer.parseInt(walkinObj.get("table_id").toString()));
			}
		}
	%>
	
	<style>
		table, th, td {
    		border: 1px solid black;
    		border-collapse: collapse;
    		text-align: center;
    		width : 100px;
    		height: 40px;
		}
		th, td {
    		padding: 20px;
		}
		th {
 		   text-align: center;
		}
	</style>
	
	<h2><%=today %></h2>  
	<table style="width:100%">
  		<tr>
    		<th> </th>
    		<th>18:00</th>
    		<th>18:30</th>
    		<th>19:00</th>
    		<th>19:30</th>
    		<th>20:00</th>
    		<th>20:30</th>
    		<th>21:00</th>
    		<th>21:30</th>
    		<th>22:00</th>
    		<th>22:30</th>
    		<th>23:00</th>
    		<th>23:30</th>
  		</tr>
		<%
		int tableOid, number, places;
		int chk=0;
		int col=0;
		int customer_id=0;
		int oid=0;
		for(int i=0;i<tableArray.size();i++) {
			tableObj=(JSONObject) tableArray.get(i);
			tableOid=Integer.parseInt(tableObj.get("oid").toString());
			number=Integer.parseInt(tableObj.get("number").toString());
			places=Integer.parseInt(tableObj.get("places").toString());
			Time time;
			%>
			<tr>
				<th><%=number %>번 테이블<br><%=places %>명</th>
				<%
				for(int j=0;j<12;j++){						// 18:00:00 ~ 23:30:00를 30분 간격으로 표시
					time = new Time(18, 30*j, 00);
					for(int k=0;k<reservationTableList.size();k++){
						if(reservationTableList.get(k)==tableOid){
							for(int l=k*4;l<k*4+4;l++){		// 예약 별로 테이블 칸 수 지정
								if(reservationTimeList.get(l).equals(time)){
									chk=-1;
									col++;
									if(l==(k*4+3) || reservationTimeList.get(l).equals(new Date(23,30,00))){
										oid=reservationOidList.get(k);
										customer_id=reservationCustomerList.get(k);
										chk=1;
									}
								}
							}
						}
					}
					
					for(int k=0;k<walkinTableList.size();k++){
						if(walkinTableList.get(k)==tableOid){
							for(int l=k*4;l<k*4+4;l++){		// 워크인 별로 테이블 칸 수 지정
								if(walkinTimeList.get(l).equals(time)){
									chk=-1;
									col++;
									if(l==(k*4+3) || walkinTimeList.get(l).equals(new Date(23,30,00))){
										oid=walkinOidList.get(k); 
										chk=2;
									}
								}
							}
						}
					}
					
					if(chk==0){			// 빈칸을 누를 경우 워크인 저장하는 페이지로 이동
						%>
						<td onClick="location.href='walkinCovers?table=<%=number %>&date=<%=today %>&time=<%=time %>'"></td>
						<%
					}
					else if(chk==1){	// 예약 칸에 사용자 정보 출력, 예약 칸을 누를 경우 예약 도착처리 페이지로 이동
						url=new URL(api_url+"api/customer/find2/"+customer_id);
						conn=(HttpURLConnection)url.openConnection();
						br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
						jsonObj=(JSONObject) jsonPar.parse(br);
						%>
						<td colspan="<%=col %>" onClick="location.href='reservationArrivalAction?oid=<%=oid %>&userId=<%=jsonObj.get("userId") %>'">
							<%=jsonObj.get("name") %>
							<br>
							<%=jsonObj.get("phoneNumber") %>
						</td>
						<%
						chk=0;
						col=0;
					}
					else if(chk==2){	// 워크인 칸을 누를 경우 워크인 삭제 페이지로 이동
						%>
						<td colspan="<%=col %>" onClick="location.href='walkinDeleteAction2?oid=<%=oid %>'">WalkIn</td>
						<%
						chk=0;
						col=0;
					}
				}
			}
		%>

	</table>
<%@ include file="../header/footer.jsp" %>