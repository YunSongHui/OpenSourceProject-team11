package com.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dao.BoardDAO;
import com.dao.EmoticonDAO;
import com.entity.EmoticonTO;
import com.entity.UserTO;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

//BoardWriteCommand는 글쓰기 화면에서 저장 버튼을 눌렀을때 실행되는 기능입니다.
public class BoardUpdateCommand implements BoardCommand {

	// Processing the file name
	public String newFileName(String oldFileName) {
		// Gets the last "." index; appear
		int index = oldFileName.lastIndexOf(".");
		// The interception of the last "." content before
		String frontStr = oldFileName.substring(0, index);
		// Gets the last "." after the content
		String behindStr = oldFileName.substring(index);
		// Reassemble the file name

		String newFileName = frontStr + System.currentTimeMillis() + "_" + behindStr;

		return newFileName;
	}

	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 실행시 사용자가 입력한 정보를 받아와서 DAO를 통해 DB에 새로운 row를 삽입합니다.

		HttpSession session = request.getSession();

		response.sendRedirect("listPage.do?method=1");
		return null;
	}
}
