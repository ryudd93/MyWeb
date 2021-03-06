package com.myweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.myweb.board.service.UpdateServiceImpl;
import com.myweb.user.model.UserVO;
import com.myweb.user.service.UserDeleteServiceImpl;
import com.myweb.user.service.UserJoinServiceImpl;
import com.myweb.user.service.UserLoginServiceImpl;
import com.myweb.user.service.UserService;
import com.myweb.user.service.UserUpdateServiceImpl;

@WebServlet("*.user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchServlet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchServlet(request, response);
	}
	
	protected void dispatchServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//요청 분기
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		
		String command = uri.substring(conPath.length());
		
		System.out.println(uri);
		
		
		
		//부모 타입 선언
		UserService service;
		if (command.equals("/user/join.user")) {	//회원 가입 화면 처리
			
			request.getRequestDispatcher("join.jsp").forward(request, response);
			
		} else if (command.equals("/user/login.user")) {	//로그인 화면 처리
			
			request.getRequestDispatcher("login.jsp").forward(request, response);
		
		} else if (command.equals("/user/joinForm.user")) { //회원 가입 요청
			
			service = new UserJoinServiceImpl();
			int result = service.execute(request, response);	//중복시 1, 성공시 0
			
			if (result == 1) {	//중복
				
				request.setAttribute("msg", "이미 존재하는 회원입니다.");
				request.getRequestDispatcher("join.jsp").forward(request, response);
				
			} else {	//성공
				response.sendRedirect("login.user");
			}
			
		} else if (command.equals("/user/loginForm.user")) {
			/*
			 * 1. UserLoginServiceImpl()로 연결
			 * 2. 폼값을 받아서 DAO의 login메서드를 이용해서 로그인 처리를 합니다.
			 * 3. 로그인애 성공시 user라는 이름으로 세션에 UserVO를 저장
			 * 		mypage로 MVC2방식으로 이동.
			 * 4. 로그인 실패시 msg에 "아이디, 비밀번호를 확인하세요"를 담아서 user_login.jsp로 이동
			 * 
			 * HttpSession session = request.getSession(); -> 자바에서 세션을 얻는 방법
			 * 
			*/
			
			service = new UserLoginServiceImpl();
			int result = service.execute(request, response);
			
			if (result == 0) {
				request.setAttribute("msg", "아이디, 비밀번호를 확인하세요");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				response.sendRedirect("mypage.user");
			}
			
		} else if (command.equals("/user/mypage.user")) {	//마이페이지 화면처리
			
			request.getRequestDispatcher("mypage.jsp").forward(request, response);
		} else if (command.equals("/user/logout.user")) {	//로그아웃
			HttpSession session = request.getSession();
			session.invalidate();
			
			response.sendRedirect(request.getContextPath());
			
		} else if (command.equals("/user/update.user")) {	//수정화면 이동
			//세션에 값이 들어 있기 때문에 화면으로 이동
			request.getRequestDispatcher("update.jsp").forward(request, response);
			
		} else if (command.equals("/user/updateForm.user")) {	//정보 수정 요청
			/*
			 * 1. UserUpdateServiceImpl()을 생성하고 호출
			 * 2. execute메서드에서는 update() 메서드를 실행시키고 성공, 실패 여부를 받아서 컨트롤로 리턴
			 * 
			*/
			service = new UserUpdateServiceImpl();
			int result = service.execute(request, response);
			
			if (result == 1) {
				//문자열의 형태로 스크립트를 작성하여 out.println()화면에 전달
				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=UTF-8");	//html로 전
				out.println("<script>");
				out.println("alert('성공');");
				out.println("location.href='mypage.jsp';");
				out.println("</script>");
				
			} else {
				response.sendRedirect("mypage.user");
			}
			
		} else if (command.equals("/user/delete.user")) {	//삭제 화면 요청
			
			request.getRequestDispatcher("delete.jsp").forward(request, response);
		
		} else if (command.equals("/user/deleteForm.user")) {	//회원탈퇴 요청
			/*
			 * 1. UserDeleteServiceImpl()로 생성, 연결
			 * 2. delete()메서드를 통해 삭제 처리
			 * 3. 성공 실패 결과를 컨트롤러로 받아와서 성공시엔, 세션 삭제 후 홈화면으로 리다이렉트
			 * 4. 실패시에는 실패 메시지를 delete.jsp로 처리
			*/
			
			service = new UserDeleteServiceImpl();
			int result = service.execute(request, response);
			
			if (result == 1) {	//탈퇴 성공
				response.sendRedirect(request.getContextPath());
			} else {	//비밀번호 틀림
				request.setAttribute("msg", "아이디, 비밀번호를 확인하세요");
				request.getRequestDispatcher("delete.jsp").forward(request, response);
			}
			
			
		}
			
	}

}
