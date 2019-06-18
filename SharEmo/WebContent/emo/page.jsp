<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.entity.PageTO" %>

<%
	PageTO to =(PageTO)request.getAttribute("page");
	int curPage = to.getCurPage();
	int perPage = to.getPerPage();
	int method = (int)request.getAttribute("method");
	int totalCount;
	String pageTag;
	totalCount = to.getTotalCount();
	if(to.getBoardList() != null) {
		pageTag = "listPage";
	}
	else {
		pageTag = "artistListPage";
	}

	int totalPage = totalCount / perPage; //보여줄 페이지 번호 개수
	
	if( totalCount % perPage !=0 )
		totalPage++;
	
	for(int i=1; i<=totalPage; i++){
		if( curPage ==i){
			out.print("<font size=5 color='red'>"+ i+ "</font>");
		}
		else{
			out.print("<a href='"+pageTag+".do?method="+method+"&curPage="+ i+ "'>"+i+"</a>");
		}
		if(i!=totalPage)
			out.print(" / ");
	}

%>