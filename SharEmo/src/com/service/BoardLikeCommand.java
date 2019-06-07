package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.entity.BoardDTO;
import com.entity.Emoticon;

public class BoardLikeCommand implements BoardCommand {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String num = request.getParameter("num");
		String userid = request.getParameter("userid");
		BoardDAO dao = new BoardDAO();
		int likes=dao.setLikes(num,userid);
		
		PrintWriter out = response.getWriter();
		out.println(likes);
		out.close();
		
		return null;
	}
}
