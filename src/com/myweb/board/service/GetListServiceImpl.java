package com.myweb.board.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardVO;
import com.myweb.util.PageVO;

public class GetListServiceImpl implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = BoardDAO.getInstance();
//		ArrayList<BoardVO> list = dao.getList();	//호출 결과를 리스트로 반환
		
		//1.첫번째 페이지 진입할 때 값
		int pageNum = 1;
		int amount = 10;
		
		//4. pageNum가 넘어올때 pageNum, amount값을 세팅
		if (request.getParameter("pageNum") != null || request.getParameter("amount") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			amount = Integer.parseInt(request.getParameter("amount"));
			
		}
		
		//2. PageVO 생성
		ArrayList<BoardVO> list = dao.getList(pageNum, amount);
		int total = dao.getTotal();
		PageVO pageVO = new PageVO(pageNum, amount, total);
		
		
		
		//화면으로 가져가기 위해 request에 저장
		request.setAttribute("list", list);
		
		//3. 페이지네이션을 화면에 전달
		request.setAttribute("pageVO", pageVO);
		
		//
		
	}
	
	

}
