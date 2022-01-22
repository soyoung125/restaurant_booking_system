<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
	<%
		String api_url="http://corodiak.gotdns.org:33333/";			// API URL
		PrintWriter script;									// jsp에서 자바스크립트 사용
		URL url;											// url 변수, API 접근용
		HttpURLConnection conn;								// url connection 변수
		BufferedReader br;									// 버퍼 변수
		JSONParser jsonPar=new JSONParser();				// json 파싱 변수
		JSONObject jsonObj;									// 파싱된 json 저장 변수
	%>