package com.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.BoardDAO;
import com.dao.EmoticonDAO;
import com.entity.EmoticonTO;
import com.entity.PageTO;
import com.entity.UserTO;

public class BoardMyBoardCommand implements BoardCommand {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserTO target_user = (UserTO)session.getAttribute("target_user");
		
		int method = Integer.parseInt(request.getParameter("method"));
		int curPage = 1;
		if(request.getParameter("curPage") !=null) {
			curPage=Integer.parseInt(request.getParameter("curPage"));
		}
		
		String nextPage = null;
		if(method == 1) { 
			nextPage = "emo/listPage.jsp";
			BoardDAO board_dao = new BoardDAO();
			EmoticonDAO emodao = new EmoticonDAO();
			PageTO list = board_dao.pageWhose(curPage, target_user.getNickname());
			//이모티콘 이미지 저장
			ArrayList<EmoticonTO> ticon = emodao.getEmoticon();
			request.setAttribute("ticon", ticon);
			//listPage.jsp에서 목록 리스트 데이터 저장
			request.setAttribute("list", list.getBoardList());
			request.setAttribute("method", method);
			//page.jsp에서 페이징 처리 데이터 저장
			request.setAttribute("page", list);
		}
		else if(method == 2) {
			nextPage = "emo/listPage.jsp";
			BoardDAO board_dao = new BoardDAO();
			EmoticonDAO emodao = new EmoticonDAO();
			PageTO list = board_dao.pageWhose(curPage, target_user.getNickname());
			//이모티콘 이미지 저장
			ArrayList<EmoticonTO> ticon = emodao.getEmoticon();
			request.setAttribute("ticon", ticon);
			//listPage.jsp에서 목록 리스트 데이터 저장
			request.setAttribute("list", list.getBoardList());
			request.setAttribute("method", method);
			//page.jsp에서 페이징 처리 데이터 저장
			request.setAttribute("page", list);
		}
		else if(method == 3) {
			nextPage = "emo/artistListPage.jsp";
		}

		return nextPage;
	}
}
