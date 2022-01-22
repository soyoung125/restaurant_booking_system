<!-- 고객 비밀번호 변경 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
			<form method="post" action="userPasswordUpdateAction">
				<h3 style="text-align: center;">비밀번호 변경</h3>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="기존 비밀번호" name="password" maxlength="20">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="비밀번호" name="password1" maxlength="20">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="비밀번호 확인" name="password2" maxlength="20">
				</div>
				<input type="submit" class="btn btn-primary form-control" value="수정완료">
			</form>
		</div>
		<div class="col-lg-4"></div>
	</div>
<%@ include file="../header/footer.jsp" %>