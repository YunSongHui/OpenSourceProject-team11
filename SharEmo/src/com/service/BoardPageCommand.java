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

public class BoardPageCommand implements BoardCommand{
	static boolean start=false;
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int curPage = 1;
		if(request.getParameter("curPage") !=null) {
			curPage=Integer.parseInt(request.getParameter("curPage"));
		}
		int method = Integer.parseInt(request.getParameter("method"));
		
		BoardDAO dao = new BoardDAO();
		PageTO list = null;
		switch(method){
			case 1:
				list = dao.page(curPage, null, true);
				break;
			case 2:
				list = dao.page(curPage, "likes", false);
				break;
			case 3:
				list = dao.page(curPage, "readCnt", false);
				break;
			case 4:
				String author = request.getParameter("author");
				list = dao.pageWhose(curPage, author);
				break;
			case 5:
				String id = request.getParameter("id");
				list = dao.pageLike(curPage, id);
				break;
		}

		EmoticonDAO emodao = new EmoticonDAO();
		//메소드 데이터 저장
		request.setAttribute("method", method);
		//listPage.jsp에서 목록 리스트 데이터 저장
		request.setAttribute("list", list.getBoardList());
		//이모티콘 이미지 저장
		ArrayList<EmoticonTO> ticon = emodao.getEmoticon();
		request.setAttribute("ticon", ticon);
		//page.jsp에서 페이징 처리 데이터 저장
		request.setAttribute("page", list);
		return "emo/listPage.jsp";
	}
}
