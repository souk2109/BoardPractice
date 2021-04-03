<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>로그인 페이지</title>
	<script  src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="/board002/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/board002/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="/board002/resources/dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="/board002/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
    <script src="/board002/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="/board002/resources/vendor/metisMenu/metisMenu.min.js"></script>
    <script src="/board002/resources/dist/js/sb-admin-2.js"></script>
</head>
<body>
	<sec:authorize access="isAuthenticated()">
		<% response.sendRedirect("/board002/board/list"); %>
	</sec:authorize>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">로그인</h3>
                    </div>
					<div class="panel-body">
						<form role="form" method="post" action="/board002/user/doLogin">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="아이디" name="id"
										type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="비밀번호" name="pwd"
										type="password" value="">
								</div>
								<div class="checkbox">
									<label> <input name="remember" type="checkbox"
										value="Remember Me">자동 로그인
									</label>
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<div class="form-group">
									<input class="btn btn-lg btn-success btn-block loginBtn"
										type="submit" value="로그인">
								</div>
								<div class="form-group">
									<input class="btn btn-lg btn-success btn-block registerBtn"
										type="submit" value="회원가입">
								</div>
							</fieldset>
						</form>
					</div> 
				</div>
            </div>
        </div>
    </div>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$(".registerBtn").on("click", function(e) {
			e.preventDefault();
			$("form").attr("action","/board002/user/register")
			$("form").attr("method","get");
			$("form").submit();
		});
	});	 
	</script>
</body>
</html>