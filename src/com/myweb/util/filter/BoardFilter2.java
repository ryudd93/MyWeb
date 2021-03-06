package com.myweb.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserVO;

@WebFilter({"/board/modify.board", "/board/update.board", "/board/delete.board"})
public class BoardFilter2 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		

		//1. 등록화면에서는 작성자를 id값으러 고정
		//2. 각 요청으로 id가 parameter로 전달되는지 확인
		//3. writer화면에서 작성자를 id값으로 고정
		//4. modify.board, update.board, delete.board 요청으로 넘어갈때 writer를 담아서 보내주도록 처리
		request.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		String writer = request.getParameter("writer");	//세션에서 넘어온 파라미터값
		
		if (user == null) {
			res.sendRedirect("/MyWeb/user/login.user");
			return;
		}
		
		
		String id = user.getId();	//세션에서 넘어온 아이디
//		System.out.println("넘어온 id 값 : " + writer);
		
		if (writer == null || !id.equals(writer)) {//권한이 없는 경우
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<script>");
			out.println("alert('권한이 없습니다.');");
			out.println("location.href='/MyWeb/board/list.board';");	//로그인 화면
			out.println("</script>");
			return;	//필터의 종료
		}
		
		
		chain.doFilter(request, response);	//컨트롤러 실행
		
	}
	

	
}
