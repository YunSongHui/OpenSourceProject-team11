package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.entity.BoardDTO;
import com.entity.EmoticonTO;
import com.entity.LikeTO;
import com.entity.PageTO;

public class BoardDAO {
	DataSource ds;
	// 생성자
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 게시물 리스트 보여주기
	public ArrayList<BoardDTO> getList() {

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String query = "SELECT num,author,title,content,likes, " + "DATE_FORMAT(writeday,'%Y/%M/%D') writeday,"
					+ "readcnt,repRoot, repStep, repIndent FROM " + "board order by repRoot desc, repStep asc";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int likes = rs.getInt("likes");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repRoot = rs.getInt("repRoot");
				int repStep = rs.getInt("repStep");
				int repIndent = rs.getInt("repIndent");

				BoardDTO data = new BoardDTO();
				data.setNum(num);
				data.setAuthor(author);
				data.setTitle(title);
				data.setContent(content);
				data.setLikes(likes);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
				data.setRepRoot(repRoot);
				data.setRepStep(repStep);
				data.setRepIndent(repIndent);
				list.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 회원가입
	public void write(String _userid, String _title, String _author, String _content) {
		int currval = 1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			String sql = "select ifnull(max(num), 0)+1 from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next())
				currval = rs.getInt(1);

			String query = "INSERT INTO board( userid, title, author,content,"
					+ "repRoot,repStep, repIndent) values (?,?,?,?,?, 0, 0)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, _userid);
			pstmt.setString(2, _title);
			pstmt.setString(3, _author);
			pstmt.setString(4, _content);
			pstmt.setInt(5, currval);
			pstmt.executeUpdate();
			
			query = "UPDATE user SET postnum = postnum + 1 WHERE id='" + _userid + "'";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 조회수 1증가
	public void readCount(String _num) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String query = "UPDATE board SET readcnt = readcnt + 1 WHERE num=" + _num;

			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 게시물 자세히 보기
	public BoardDTO retrieve(String _num) {

		// 조회수 1증가
		readCount(_num);

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO data = new BoardDTO();

		try {
			con = ds.getConnection();
			String query = "SELECT * FROM board WHERE num = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(_num));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String userid = rs.getString("userid");
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String content = rs.getString("content");
				int likes = rs.getInt("likes");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");

				data.setUserid(userid);
				data.setNum(num);
				data.setTitle(title);
				data.setAuthor(author);
				data.setContent(content);
				data.setLikes(likes);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	// 게시물 수정
	public void update(String _num, String _title, String _author, String _content) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String query = "UPDATE board SET title = ?, author = ?," + "content = ? WHERE num = ?";

			pstmt = con.prepareStatement(query);

			pstmt.setString(1, _title);
			pstmt.setString(2, _author);
			pstmt.setString(3, _content);
			pstmt.setInt(4, Integer.parseInt(_num));

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 게시물 삭제
	public void delete(String _num) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String query = "DELETE FROM board WHERE num=?";

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(_num));
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 답변 글 쓰기 UI 로드
	public BoardDTO replyui(String _num) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO data = new BoardDTO();

		try {
			con = ds.getConnection();
			String query = "SELECT * FROM board WHERE num=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(_num));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data.setNum(rs.getInt("num"));
				data.setTitle(rs.getString("title"));
				data.setAuthor(rs.getString("author"));
				data.setContent(rs.getString("content"));
				data.setLikes(rs.getInt("likes"));
				data.setWriteday(rs.getString("writeday"));
				data.setReadcnt(rs.getInt("readcnt"));
				data.setRepRoot(rs.getInt("repRoot"));
				data.setRepStep(rs.getInt("repStep"));
				data.setRepIndent(rs.getInt("repIndent"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	// 답변시 repStep 1 증가
	public void makeReply(String _root, String _step) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String query = "UPDATE board SET repStep = repStep + 1 " + "WHERE repRoot = ? AND repStep > ?";

			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, Integer.parseInt(_root));
			pstmt.setInt(2, Integer.parseInt(_step));

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 답변달기
	public void reply(String _num, String _title, String _author, String _content, String _repRoot, String _repStep,
			String _repIndent) {

		// repStep + 1
		makeReply(_repRoot, _repStep);

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			String query = "INSERT INTO board(title, author, content, repRoot,"
					+ "repStep, repIndent) values (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, _title);
			pstmt.setString(2, _author);
			pstmt.setString(3, _content);
			pstmt.setInt(4, Integer.parseInt(_repRoot));
			pstmt.setInt(5, Integer.parseInt(_repStep) + 1);
			pstmt.setInt(6, Integer.parseInt(_repIndent) + 1);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 글 검색하기
	public PageTO search(String _searchName, String _searchValue, int curPage) {
		PageTO to = new PageTO();
		int totalCount = totalCount();

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String query = "SELECT num,author,title,content," + "DATE_FORMAT(writeday,'%Y/%M/%D') writeday, readcnt "
					+ ",repRoot, repStep, repIndent FROM board ";
			if (_searchName.equals("title")) {
				query += "WHERE title LIKE ? ";
			} else {
				query += "WHERE author LIKE ? ";
			}
			query += "order by repRoot desc, repStep asc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + _searchValue + "%");
			rs = pstmt.executeQuery();

			int perPage = to.getPerPage();// 5

			int skip = (curPage - 1) * perPage;

			if (skip > 0) {
				rs.absolute(skip);
			}

			for (int i = 0; i < perPage && rs.next(); i++) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repRoot = rs.getInt("repRoot");
				int repStep = rs.getInt("repStep");
				int repIndent = rs.getInt("repIndent");

				BoardDTO data = new BoardDTO();
				data.setNum(num);
				data.setAuthor(author);
				data.setTitle(title);
				data.setContent(content);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
				data.setRepRoot(repRoot);
				data.setRepStep(repStep);
				data.setRepIndent(repIndent);
				list.add(data);
			}
			to.setBoardList(list);
			to.setTotalCount(totalCount);
			to.setCurPage(curPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return to;
	}

	public int totalCount() {

		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String query = "SELECT count(*) FROM board";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	// 페이지 구현
	public PageTO page(int curPage, String field_name, boolean method) {
		PageTO to = new PageTO();
		int totalCount = totalCount();

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String query = "SELECT * FROM board order by ";
			/*	String query = "SELECT num,author,title,content,likes," + "DATE_FORMAT(writeday,'%Y/%M/%D') writeday,"
			+ "readcnt,repRoot, repStep, repIndent, FROM board order by ";*/
			if (field_name != null) {
				query += field_name;
				if (method)
					query += " asc, ";
				else
					query += " desc, ";
			}
			query += "UNIX_TIMESTAMP(writeday) desc";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			int perPage = to.getPerPage();// 5

			int skip = (curPage - 1) * perPage;

			if (skip > 0) {
				rs.absolute(skip);
			}
			for (int i = 0; i < perPage && rs.next(); i++) {
				BoardDTO data = new BoardDTO();
				data.setNum(rs.getInt("num"));
				data.setAuthor(rs.getString("author"));
				data.setTitle(rs.getString("title"));
				data.setLikes(rs.getInt("likes"));
				data.setContent(rs.getString("content"));
				data.setWriteday(rs.getString("writeday"));
				data.setReadcnt(rs.getInt("readcnt"));
				data.setRepRoot(rs.getInt("repRoot"));
				data.setRepStep(rs.getInt("repStep"));
				data.setRepIndent(rs.getInt("repIndent"));
				list.add(data);
			}
			to.setBoardList(list);
			to.setTotalCount(totalCount);
			to.setCurPage(curPage);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return to;
	}

	// 페이지 구현
	public PageTO pageWhose(int _curPage, String _author) {
		PageTO to = new PageTO();
		int totalCount = totalCount();

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String query = "SELECT * FROM board WHERE author = ? ORDER BY UNIX_TIMESTAMP(writeday) desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, _author);
			rs = pstmt.executeQuery();

			int perPage = to.getPerPage();// 5

			int skip = (_curPage - 1) * perPage;

			if (skip > 0) {
				rs.absolute(skip);
			}
			for (int i = 0; i < perPage && rs.next(); i++) {
				BoardDTO data = new BoardDTO();
				data.setNum(rs.getInt("num"));
				data.setAuthor(rs.getString("author"));
				data.setTitle(rs.getString("title"));
				data.setLikes(rs.getInt("likes"));
				data.setContent(rs.getString("content"));
				data.setWriteday(rs.getString("writeday"));
				data.setReadcnt(rs.getInt("readcnt"));
				data.setRepRoot(rs.getInt("repRoot"));
				data.setRepStep(rs.getInt("repStep"));
				data.setRepIndent(rs.getInt("repIndent"));
				list.add(data);
			}
			to.setBoardList(list);
			to.setTotalCount(totalCount);
			to.setCurPage(_curPage);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return to;
	}
	
	// 좋아요를 누른 게시글
	public PageTO pageLike(int _curPage, String _userid) {
		PageTO to = new PageTO();
		int totalCount = totalCount();

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		ArrayList<LikeTO> like_list = new ArrayList<LikeTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String query = "SELECT * FROM likes WHERE like_userid = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, _userid);
			rs = pstmt.executeQuery();

			int perPage = to.getPerPage();// 5

			int skip = (_curPage - 1) * perPage;

			if (skip > 0) {
				rs.absolute(skip);
			}
			for (int i = 0; i < perPage && rs.next(); i++) {
				LikeTO like = new LikeTO();
				like.setBoardnum(rs.getInt("like_boardnum"));
				like.setUserid(rs.getString("like_userid"));
				like_list.add(like);
			}

			for (LikeTO like: like_list) {
				query = "SELECT * FROM board WHERE num = ?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, like.getBoardnum());
				rs = pstmt.executeQuery();

				if(rs.next()) {
					BoardDTO data = new BoardDTO();
					data.setNum(rs.getInt("num"));
					data.setAuthor(rs.getString("author"));
					data.setTitle(rs.getString("title"));
					data.setLikes(rs.getInt("likes"));
					data.setContent(rs.getString("content"));
					data.setWriteday(rs.getString("writeday"));
					data.setReadcnt(rs.getInt("readcnt"));
					data.setRepRoot(rs.getInt("repRoot"));
					data.setRepStep(rs.getInt("repStep"));
					data.setRepIndent(rs.getInt("repIndent"));
					list.add(data);
				}
			}
			
			to.setBoardList(list);
			to.setTotalCount(totalCount);
			to.setCurPage(_curPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return to;
		
	}

	public int getMaxNum() {
	
		int num = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = ds.getConnection();
			String query = "select ifnull(max(num), 0) from board";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
	
			if (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}

}
