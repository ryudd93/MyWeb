<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp" %>

<section>
	<div align="center">
		<h2>회원가입 연습</h2>
		<form action="joinForm.user" method="post" name="regForm" >
			<table>
				<tr>
					<td align="right">아이디 : </td>
					<td><input type="text" name="id" placeholder="4글자 이상"></td>
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
					<td><input type="text" name="name"></td>
				</tr>
				
				<tr>
					<td align="right">이메일 : </td>
					<td><input type="email" name="email"></td>
				</tr>
				
				<tr>
					<td align="right">주소 : </td>
					<td><input type="text" name="address"></td>
				</tr>
			</table>
			
			<br/>
			<p>${msg }</p>
			
			<br/>
			<input type="button" value = "가입" class="btn btn-default" onclick="check()">
			<input type="button" value = "로그인" class="btn btn-primary" onclick="location.href='login.jsp'">
		</form>
	</div>
</section>

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
</script>


<%@ include file = "../include/footer.jsp" %>