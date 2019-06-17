package com.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.UserDAO;
import com.entity.EmoticonTO;
import com.entity.PageTO;

public class BoardArtistPageCommand implements BoardCommand{
	static boolean start=false;
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int curPage = 1;
		if(request.getParameter("curPage") !=null) {
			curPage=Integer.parseInt(request.getParameter("curPage"));
		}
		int method = Integer.parseInt(request.getParameter("method"));
		
		UserDAO dao = new UserDAO();
		PageTO list = null;
		switch(method){
			case 1:
				list = dao.page(curPage, null, true);
				break;
			case 2:
				list = dao.page(curPage, "followernum", false);
				break;
			case 3:
				list = dao.page(curPage, "postnum", false);
				break;
		}
		
		//listPage.jsp에서 목록 리스트 데이터 저장
		request.setAttribute("list", list.getUserList());
		//page.jsp에서 페이징 처리 데이터 저장
		request.setAttribute("page", list);
		return "emo/artistListPage.jsp";
	}
}
