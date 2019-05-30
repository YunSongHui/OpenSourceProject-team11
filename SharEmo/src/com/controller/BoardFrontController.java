package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.BoardCommand;
import com.service.BoardDeleteCommand;
import com.service.BoardLoginCommand;
import com.service.BoardPageCommand;
import com.service.BoardReplyCommand;
import com.service.BoardReplyUICommand;
import com.service.BoardRetrieveCommand;
import com.service.BoardSearchCommand;
import com.service.BoardSignUpCommand;
import com.service.BoardUpdateCommand;
import com.service.BoardWriteCommand;

//해당 어노테이션은 "~.do"로 들어오는 모든 요청을 이 서블릿에서 처리하겠다는 의미입니다. (맵핑)
@SuppressWarnings("serial")
@WebServlet("*.do")
public class BoardFrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doPost를 호출함으로써 get방식으로 온 요청도 post방식과 함께 처리합니다.
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// URL에서 스키마, 서버이름, 포트번호를 제외한 나머지 주소와 파라미터를 가져옵니다.
		// (http://localhost:8080)/Sharjsp/jsp/list.do
		String requestURL = request.getRequestURI();
		// URL에서 컨텍스트 경로를 가져옵니다. (/Sharemo)
		String contextPath = request.getContextPath();
		// 36줄에서 가져온 페이지 주소에서 컨텍스트 경로마저 제외시킵니다. (/emo/list.do)
		String com = requestURL.substring(contextPath.length());
		BoardCommand command = null;
		String nextPage = null;
		// 이렇게 추출한 com 변수를 각각 적합한 처리 모델에 넘겨서 일을 처리하고
		// 그다음에 수행할 요청 혹은 띄울 페이지를 설정해줍니다.

		// 목록 보기
		if (com.equals("/")) {
			command = new BoardPageCommand();
			command.execute(request, response);
			nextPage = "emo/main.jsp";
		}

		// 로그인 화면
		if (com.equals("/loginUI.do")) {
			nextPage = "emo/loginUI.jsp";
		}

		// 로그인
		if (com.equals("/login.do")) {
			command = new BoardLoginCommand();
			command.execute(request, response);
			nextPage = "emo/login.jsp";
		}

		// 로그아웃
		if (com.equals("/logout.do")) {
			nextPage = "emo/logout.jsp";
		}

		// 로그인 에러
		if (com.equals("/loginerror.do")) {
			nextPage = "emo/loginerror.jsp";
		}

		// 로그인 에러
		if (com.equals("/mypage.do")) {
			nextPage = "emo/mypage.jsp";
		}

		// 회원가입 페이지 이동
		if (com.equals("/signUpUI.do")) {
			nextPage = "emo/signUpUI.jsp";
		}

		// 회원가입
		if (com.equals("/signUp.do")) {
			command = new BoardSignUpCommand();
			command.execute(request, response);
			nextPage = "emo/join.jsp";
		}

		// 홈페이지
		if (com.equals("/main.do")) {
			command = new BoardPageCommand();
			command.execute(request, response);
			nextPage = "emo/main.jsp";
		}
		// 페이징 처리
		if (com.equals("/list.do")) {
			command = new BoardPageCommand();
			command.execute(request, response);
			nextPage = "emo/listPage.jsp";
		}

		// 글쓰기 폼
		if (com.equals("/writeui.do")) {
			nextPage = "emo/write.jsp";
		}
		// 글쓰기
		if (com.equals("/write.do")) {
			command = new BoardWriteCommand();
			command.execute(request, response);
			nextPage = "main.do";
		}
		// 글 자세히 보기
		if (com.equals("/retrieve.do")) {
			command = new BoardRetrieveCommand();
			command.execute(request, response);
			nextPage = "emo/retrieve.jsp";
		}
		// 글 수정하기
		if (com.equals("/update.do")) {
			command = new BoardUpdateCommand();
			command.execute(request, response);
			nextPage = "main.do";
		}
		// 글 삭제하기
		if (com.equals("/delete.do")) {
			command = new BoardDeleteCommand();
			command.execute(request, response);
			nextPage = "main.do";
		}
		// 글 검색하기
		if (com.equals("/search.do")) {
			command = new BoardSearchCommand();
			command.execute(request, response);
			nextPage = "emo/listPage.jsp";
		}

		// 답변글 입력 폼 보기
		if (com.equals("/replyui.do")) {
			command = new BoardReplyUICommand();
			command.execute(request, response);
			nextPage = "emo/reply.jsp";
		}

		// 답변 글쓰기
		if (com.equals("/reply.do")) {
			command = new BoardReplyCommand();
			command.execute(request, response);
			nextPage = "main.do";
		}

		// 지정한 경로로 제어를 이동(리다이렉트)시키기 위한 코드입니다.
		RequestDispatcher dis = request.getRequestDispatcher(nextPage);
		dis.forward(request, response);
	}

}
