<!-- 로그인 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../header/beforeLoginHeader.jsp" %>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<form method="post" action="loginAction">
					<h3 style="text-align: center;"> 로그인화면 </h3>
					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="아이디" name="userId" maxlength="20">
					</div>
					<div class="form-group">
						<input type="password" class="form-control"
							placeholder="비밀번호" name="password" maxlength="20">
					</div>
					<input type="submit" class="btn btn-primary form-control" value="로그인">
				</form>
			</div>
		</div>
	</div>	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>