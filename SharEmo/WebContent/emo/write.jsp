<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<! DOCTYPE html>
<html>

<head>

<link href='https://fonts.googleapis.com/css?family=Dekko'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Didact Gothic'
	rel='stylesheet'>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<link type="text/css" rel="stylesheet"
	href="emo/assets/css/write_style.css">

<title>SharEmo - Free Emoticon Share Website</title>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="emo/assets/js/dropbox2.js">
</script>
<script type="text/javascript" src="emo/assets/js/preview.js">
</script>
</head>

<body>

	<section id="header">
		<div id="navbar-top">
			<a href="main.do"> <img src="emo/images/sharEmo_logo_2.png">
			</a>
			<ul id="navbar-top-right">
				<c:choose>
					<c:when test="${user != null}">
						<li class="nav-top-item">
							<img src="${user.mascot}">
							<a href='mypage.do'>${user.id}님</a>
						</li>
						<li class="nav-top-item"><a href='logout.do'>Logout</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-top-item"><a href='loginUI.do'>Login</a></li>
					</c:otherwise>
				</c:choose>
				<li class="nav-top-item"><a href="signUpUI.do">Sign up</a></li>
			</ul>
		</div>
	</section>

	<nav id="navbar-mid">
		<form action="search.do" method="post">
			<input type="search" name="searchValue"
				placeholder="Search for emoticons e.g. happy, sad, angry...">
			<button type="submit">
				<img src="emo/images/musica-searcher.png" width="20px" height="20px">
			</button>
		</form>
		<ul>
			<li class="nav-mid-item"><a href="main.do">Home</a></li>
			<li class="nav-mid-item"><a href="listPage.do?method=1">Emotion</a>
				<div class="nav-mid-item-drop">
					<ul>
						<a href="listPage.do?method=1"><li><span>New</span></li></a>
						<a href="listPage.do?method=2"><li><span>Popular</span></li></a>
						<a href="listPage.do?method=3"><li><span>Hot</span></li></a>
					</ul>
				</div></li>
			<li class="nav-mid-item"><a href="artistListPage.do?method=1">Artist</a>
				<div class="nav-mid-item-drop">
					<ul>
						<a href="artistListPage.do?method=1"><li><span>New</span></li></a>
						<a href="artistListPage.do?method=2"><li><span>Popular</span></li></a>
						<a href="artistListPage.do?method=3"><li><span>Most<br/>Published</span></li></a>
					</ul>
				</div></li>
			<li class="nav-mid-item"><a href="mypage.do">MyGallery</a>
				<div class="nav-mid-item-drop">
					<ul>
						<c:if test="${user!=null}">
							<a href="listPage.do?method=5&id=${user.id}"><li><span>Like</span></li></a>
							<a href="artistListPage.do?method=4&id=${user.id}"><li><span>Follow</span></li></a>
							<a href="writeui.do"><li><span>Upload</span></li></a>
							<a href="mypage.do"><li><span>My Gallery</span></li></a>
						</c:if>
						<c:if test="${user==null}">
							<a href="loginUI.do"><li><span>Like</span></li></a>
							<a href="loginUI.do"><li><span>Follow</span></li></a>
							<a href="loginUI.do"><li><span>Upload</span></li></a>
							<a href="loginUI.do"><li><span>My Gallery</span></li></a>
						</c:if>
					</ul>
				</div></li>
		</ul>
	</nav>

	<section id="container">
		<section id="content">
			<div id="content-wrapper">
				<h2>Share your new COOL emoticon!</h2>
				<form id="frm" name=form1 action="update.do" method=post enctype="multipart/form-data">
					<div id="title">
						<label for="title">Title : </label> <input type="text"
							name="title" required>
					</div>
					<p>Artist : ${user.nickname}</p>
					<div id="desc_label">
						<label for="description">Description</label>
						<p>0/500bytes</p>
					</div>
					<textarea name="description" rows="8" cols="100"></textarea>
					<div id="file_label">
						<label for="upload">Upload Images</label> <input type="file"
							name="upload" id="input_images" value="폴더 선택" accept="image/*" multiple>
							<input type="button" value="파일 리셋" onclick="resetFiles()">이미지 클릭 시 개별 삭제
					</div>
					<div class="upload_images"></div>
					<span><button onclick="submitWriteAction()">SUBMIT</button></span>
				</form>
			</div>
		</section>
		<div id="ad">
			<a href="https://www.idowell.co.kr/home/" target="_blank"><img
				src="emo/images/ad/winnerstel.png"></a><br /> <a
				href="https://www.duo.co.kr/html/love_test/main.asp?u_div=agency1_DA5_2019&utm_medium=double&utm_source=kakao_banner&utm_campaign=DT_%EB%93%80%EC%98%A4pc&utm_term=%EB%A6%AC%ED%83%80%EA%B2%9F"
				target="_blank"><img src="emo/images/ad/duo.jpg"></a>
		</div>
	</section>

	<section id="footer">
		<img src="emo/images/cbnu_white.png" width="221" height="67">
		<p>2019  오픈소스 전문 프로젝트 TEAM 11</p>
		<p>윤송희  전준호  정희주  장형규</p>
		<p>주소 : 충북 청주시 서원구 충대로 1, 충북대학교 S4-1 소프트웨어학과 / TEL : 043)261-2114</p>
	</section>

</body>

</html>