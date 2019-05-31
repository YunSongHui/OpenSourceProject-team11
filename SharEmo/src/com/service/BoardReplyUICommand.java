package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.entity.BoardDTO;

public class BoardReplyUICommand implements BoardCommand {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String num = request.getParameter("num");
		BoardDAO dao = new BoardDAO();
		
		BoardDTO data = dao.replyui(num);
		request.setAttribute("replyui", data);
		return "emo/reply.jsp";
	}
}
