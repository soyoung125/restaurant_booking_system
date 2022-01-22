<!-- 예약 시간 선택 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	
	<%
		String date=request.getAttribute("date").toString();
	
		url=new URL(api_url+"api/table/list");			// API로 테이블 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray tableArray=(JSONArray) jsonPar.parse(br);
		JSONObject tableObj;
		
		url=new URL(api_url+"api/reservation/list");	// API로 예약 리스트 가져옴
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
		for(int i=0;i<reservationArray.size();i++){		// 예약 시간~(예약시간+2시간)을 30분 단위로 ArrayList에 저장
			reservationObj=(JSONObject) reservationArray.get(i);
			reservationDate=reservationObj.get("date").toString();
			if(date.equals(reservationDate)){
				reservationOidList.add(Integer.parseInt(reservationObj.get("oid").toString()));
				reservationTime=reservationObj.get("time").toString();
				hour=Integer.parseInt(reservationTime.split(":")[0]);
				min=Integer.parseInt(reservationTime.split(":")[1]);
				for(int j=0;j<4;j++){
					tmpTime=new Time(hour, min+30*j, 0);
					reservationTimeList.add(tmpTime);
				}
				reservationTableList.add(Integer.parseInt(reservationObj.get("table_id").toString()));
			}
		}
		
		url=new URL(api_url+"api/walkin/list");		// API로 워크인 리스트 가져옴
		conn=(HttpURLConnection)url.openConnection();
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray walkinArray=(JSONArray) jsonPar.parse(br);
		JSONObject walkinObj;
		String walkinDate, walkinTime;
		ArrayList<Integer> walkinOidList=new ArrayList();
		ArrayList<Time> walkinTimeList=new ArrayList();
		ArrayList<Integer> walkinTableList=new ArrayList();
		for(int i=0;i<walkinArray.size();i++){		// 워크인 시간~(워크인시간+2시간)을 30분 단위로 ArrayList에 저장
			walkinObj=(JSONObject) walkinArray.get(i);
			walkinDate=walkinObj.get("date").toString();
			if(date.equals(walkinDate)){
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
		int oid, number, places;
		int chk=0;
		for(int i=0;i<tableArray.size();i++) {
			tableObj=(JSONObject) tableArray.get(i);
			oid=Integer.parseInt(tableObj.get("oid").toString());
			number=Integer.parseInt(tableObj.get("number").toString());
			places=Integer.parseInt(tableObj.get("places").toString());
			Time time;
			%>
			<tr>
				<th><%=number %>번 테이블<br><%=places %>명</th>
				<%
				for(int j=0;j<12;j++){	// 18:00 ~ 23:30을 30분 단위로 표시
					time = new Time(18, 30*j, 00);
					for(int k=0;k<reservationTableList.size();k++){
						if(reservationTableList.get(k)==oid){	// 예약이 있는 시간은 검은 칸으로 표시
							for(int l=k*4;l<k*4+4;l++){
								if(reservationTimeList.get(l).toString().equals(time.toString())){
									%><td bgcolor="black"></td><%
									chk=1;
								}
							}
						}
					}
					for(int k=0;k<walkinTableList.size();k++){
						if(walkinTableList.get(k)==oid){		// 워크인이 있는 시간은 검은 칸으로 표시
							for(int l=k*4;l<k*4+4;l++){
								if(walkinTimeList.get(l).toString().equals(time.toString())){
									%><td bgcolor="black"></td><%
									chk=1;
								}
							}
						}
					}
					if(chk==0){		// 빈칸 클릭시 인원수 정하는 페이지로 이동
						%>
						<td onClick="location.href='reservationCovers?table=<%=number %>&date=<%=date %>&time=<%=time %>'"></td>
						<%
					}
					else{
						chk=0;
					}
				}
		}
		%>
	</table>
<%@ include file="../header/footer.jsp" %>