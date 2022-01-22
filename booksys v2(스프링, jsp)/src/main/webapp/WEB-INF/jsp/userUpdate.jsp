<!-- 고객 업데이트 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
			<form method="post" action="userUpdateAction">
				<h3 style="text-align: center;">회원정보 수정</h3>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="아이디" name="userId" maxlength="20" value="<%=userId%>" readonly>
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="비밀번호" name="password" maxlength="20">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="이름" name="userName" maxlength="20" value="<%=jsonObj.get("name")%>" readonly>
				</div>
				<div class="form-group">
					<input type="tel" class="form-control" placeholder="전화번호" name="phoneNumber" maxlength="13" value="<%=jsonObj.get("phoneNumber")%>">
				</div>
				<input type="submit" class="btn btn-primary form-control" value="수정완료">
			</form>
		</div>
		<div class="col-lg-4"></div>
	</div>
<%@ include file="../header/footer.jsp" %>