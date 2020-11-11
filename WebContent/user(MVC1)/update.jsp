<%@page import="com.myweb.user.model.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<%
	// 이 페이지에 진입했을때, 비밀번호를 제외한 회원의 정보를 input태그에 처리
	UserVO vo = (UserVO)session.getAttribute("user");

	String id = vo.getId();
	String name = vo.getName();
	String email = vo.getEmail();
	String address = vo.getAddress();

	
	//readonly는 input태그의 읽기만 가능하게 하는 속성

	
%>

<section>
	<div align="center">
			<h2>회원정보 연습</h2>
			<form action="update_ok.jsp" method="post" name="regForm" >
				<table>
					<tr>
						<td align="right">아이디 : </td>
						<td><input type="text" name="id" value="<%=id %>" readonly></td>
						<!-- disabled는 없는 값으로 값이 넘어가지 않음 -->
					</tr>
					
					<tr>
						<td align="right">비밀번호 : </td>
						<td><input type="password" name="pw"></td>
					</tr>
					
					<tr>
						<td align="right">비밀번호 확인 : </td>
						<td><input type="password" name="pwCheck"></td>
					</tr>
					
					<tr>
						<td align="right">이름 : </td>
						<td><input type="text" name="name" value="<%=name %>"></td>
					</tr>
					
					<tr>
						<td align="right">이메일 : </td>
						<td><input type="email" name="email" value="<%=email %>"></td>
					</tr>
					
					<tr>
						<td align="right">주소 : </td>
						<td><input type="text" name="address" value="<%=address %>"></td>
					</tr>
				</table>
				<br/>
				<input type="submit" value = "정보수정" class="btn btn-default" onclick="check()">
				<input type="button" value = "취소" class="btn btn-primary" onclick="history.go(-1)">
			</form>
		</div>

</section>

<%@ include file="../include/footer.jsp" %>


<script>
	function check() {
	// form테그는 유일하게 document.form이름.이름... 접근이 가능
		if (document.regForm.id.value.length < 4){
			alert('아이디는 4자리 이상입니다.');
			return; //함수 종료
		} else if (document.regForm.pw.value.length < 4){
			alert('비밀번호는 4자리 이상입니다.');
			return;
		} else if (document.regForm.pw.value != document.regForm.pwCheck.value){
			alert('비밀번호가 다릅니다.');
			return;
		} else if(document.regForm.name.value == '') {
			alert('이름은 필수입니다.');
			return;
		} else {
			//submit()은 자바 스크립트의 서브밋기능
			document.regForm.submit();
		}
	
	

	}