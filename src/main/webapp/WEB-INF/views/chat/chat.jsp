<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<sec:authentication property="principal" var="user"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script  src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>'; 
	let userId = '<c:out value="${userId }"/>'; // 이 방에 참여하고 있는 모든 회원들의 아이디
	let userIdList = userId.split("|");
	let admitCkeck = false;
	for(var i=0; i<userIdList.length; i++){
		if(userIdList[i] === id){
			admitCkeck = true;
			break;
		}
	}
	if(!admitCkeck){
		alert('허용된 사용자가 아닙니다.');
		window.location.href = '/board002/chat/allChatRooms';
	}
	 
</script>


<style>
pre {margin:0 padding:0;}
.block1 {
    width: 100%;
    height: 100%;
    border:1px solid;
    border-color:red;
    margin:0;
    padding:0;
}

.block2 {
    width: 100%;
    height: 100%;
    border:1px solid;
    border-color:orange;
    margin:0;
    padding:0;
}

.block3 {
    width: 100%;
    height: 100%;
    border:1px solid;
    border-color:blue;
    margin:0;
    padding:0;
}

.block4{
    width: 100%;
    height: 100%;
    border:1px solid;
    border-color:green;
    margin:0;
    padding:0;
}

.block5 {
    width: 100%;
    height: 100%;
    border:1px solid;
    border-color:pink;
    margin:0;
    padding:0;
}

.g-block-1 {
    width: 50%;
    height:100%;
    float: left;
    display:inline-block;
    margin:0;
    padding:0;
}

.g-block-2 {
    width: 50%;
    float: right;
    height:100%;
    display:inline-block;
    margin:0;
    padding:0;
}
}
</style>

</head>

<body>
	<div>
    <div class="g-block-1">
        <div class="block1">
          <pre>
              some content
              some content
              some content
              some content
              some content
              some content
              some content
              some content
          </pre>
        </div> 
        <div class="block2">
          <pre>
              some content
              some content
              some content
              some content
              some content
              some content
              some content
              some content
              some content
              some content
          </pre>
        </div>
    </div>
    <div class="g-block-2">
        <div class="block3">
          <pre>
              some content
              some content
              some content
              some content
              some content
          </pre>
        </div> 
        <div class="block4">
          <pre>
              some content
              some content
              some content
              some content
              some content
          </pre>
        </div>
        <div class="block5">
          <pre>
              some content
              some content
              some content
              some content
              some content
          </pre>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">

</script>
</html>