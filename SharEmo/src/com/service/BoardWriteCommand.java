package com.service;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
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
import com.entity.UserTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

//BoardWriteCommand는 글쓰기 화면에서 저장 버튼을 눌렀을때 실행되는 기능입니다.
public class BoardWriteCommand implements BoardCommand {

	public String moveFile(String folderName, String fileName, String beforeFilePath, String afterFilePath) {
		String path = afterFilePath + "\\" + folderName;
		String filePath = path + "\\" + fileName;
		File dir = new File(path);
		if (!dir.exists()) { // 폴더 없으면 폴더 생성
			dir.mkdirs();
		}
		try {
			File file = new File(beforeFilePath + "\\" + fileName);

			if (file.renameTo(new File(filePath))) { // 파일 이동
				return filePath; // 성공시 성공 파일 경로 return
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 실행시 사용자가 입력한 정보를 받아와서 DAO를 통해 DB에 새로운 row를 삽입합니다.

		HttpSession session = request.getSession();

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String path = request.getRealPath("emosave");
		int maxFileSize = 1024 * 1024 * 10;
		String enc = "utf-8";
		int num;
		BoardDAO bdao = new BoardDAO();
		EmoticonDAO emodao = new EmoticonDAO();
		num=bdao.getMaxNum()+1;
		//path+="\\"+num;
		
		MultipartRequest multi = new MultipartRequest(request, path, maxFileSize, enc, new DefaultFileRenamePolicy());
		
		UserTO user = (UserTO) session.getAttribute("user");
		if(user!=null) {
			String id = user.getId();
			String author = user.getNickname();
			String title = multi.getParameter("title");
			String content =multi.getParameter("content");
			
			bdao.write(id, title, author, content);
			Enumeration files = multi.getFileNames();
			while (files.hasMoreElements()) {
				String uploadFile = (String) files.nextElement();
				String orgName = multi.getOriginalFileName(uploadFile);
				String sysName = multi.getFilesystemName(uploadFile);
				num=emodao.writeEmoticon(sysName, orgName, null);
				moveFile(Integer.toString(num), sysName, path, path);
			}
		}
		return null;
	}
}
