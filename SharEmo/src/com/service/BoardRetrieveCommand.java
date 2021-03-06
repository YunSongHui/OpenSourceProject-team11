package com.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.BoardDAO;
import com.dao.EmoticonDAO;
import com.dao.UserDAO;
import com.entity.BoardDTO;
import com.entity.EmoticonTO;
import com.entity.UserTO;

public class BoardRetrieveCommand implements BoardCommand {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserTO user=(UserTO)session.getAttribute("user");
		
		String num = request.getParameter("num");
		BoardDAO dao = new BoardDAO();
		BoardDTO data = dao.retrieve(num);
		EmoticonDAO emodao = new EmoticonDAO();
		ArrayList<EmoticonTO> ticon =emodao.getEmoticon(num);
		
		UserDAO userdao = new UserDAO();
		String follow=data.getUserid();
		int followernum=userdao.getFollow(follow);
		boolean isFollow = false;
		boolean isLike = false;
		if(user != null) {
			isFollow = userdao.isFollow(follow,user.getId());
			isLike = userdao.isLike(num,user.getId());
		}
		request.setAttribute("isFollow", isFollow);
		request.setAttribute("isLike", isLike);
		
		//이모티콘 이미지 저장
		request.setAttribute("followernum", followernum);
		request.setAttribute("ticon", ticon);
		request.setAttribute("retrieve", data);
		return "emo/retrieve.jsp";
	}
}
