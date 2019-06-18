package com.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.BoardDAO;
import com.dao.EmoticonDAO;
import com.dao.UserDAO;
import com.entity.EmoticonTO;
import com.entity.PageTO;
import com.entity.UserTO;

public class BoardMyPageCommand implements BoardCommand {
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		UserTO target_user = null;
		if (id == null) {
			HttpSession session=request.getSession();
			target_user = (UserTO)session.getAttribute("user");
		}
		else {
			UserDAO user_dao = new UserDAO();
			target_user = user_dao.getUser(id);
		}
		
		BoardDAO board_dao = new BoardDAO();
		EmoticonDAO emo_dao = new EmoticonDAO();
		UserDAO user_dao = new UserDAO();
		PageTO listA = board_dao.pageWhose(1, target_user.getNickname());
		PageTO listB = board_dao.pageLike(1, target_user.getId());
		PageTO listC = user_dao.pageFollow(1, target_user.getId());

		//이모티콘 이미지 저장
		ArrayList<EmoticonTO> ticon = emo_dao.getEmoticon();
		request.setAttribute("ticon", ticon);
		//user 정보 저장
		request.setAttribute("target_user", target_user);
		//uploaded post 정보 저장
		request.setAttribute("listA", listA.getBoardList());
		request.setAttribute("listB", listB.getBoardList());
		request.setAttribute("listC", listC.getUserList());
		
		return "emo/mypageUI.jsp";
	}
}
