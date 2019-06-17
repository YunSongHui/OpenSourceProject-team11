<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	int Thumbnailnum = 0;
	request.setAttribute("thumbnum", Thumbnailnum);
%>
<! DOCTYPE html>
<html>

<head>

<link type="text/css" rel="stylesheet"
	href="emo/assets/css/main_style.css">
<link href='https://fonts.googleapis.com/css?family=Didact Gothic'
	rel='stylesheet'>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Dekko'
	rel='stylesheet'>

<title>SharEmo - Free Emoticon Share Website</title>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="emo/assets/js/dropbox.js">
	
</script>

</head>

<body>

	<nav id="navbar-top">
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
	</nav>

	<section id="header">
		<div id="header-content">
			<a href="main.do"> <img src="emo/images/sharEmo_logo_3.png"> </a>
			<p>Find your emoticon whatever you want!!</p>
			<form action="search.do" method="post">
				<input type="search" name="searchValue"
					placeholder="Search for emoticons e.g. happy, sad, angry...">
				<button type="submit">
					<img src="emo/images/musica-searcher.png" width="20px"
						height="20px">
				</button>
			</form>
		</div>
	</section>

	<nav id="navbar-mid">
		<ul>
			<li class="nav-mid-item"><a href="#">Home</a></li>
			<li class="nav-mid-item"><a href="listPage.do?method=1">Emotion</a>
				<div class="nav-mid-item-drop">
					<img src="emo/images/말꼬리.png" width="180px" height="40px">
					<ul>
						<a href="listPage.do?method=1"><li><span>New Emoticon</span></li></a>
						<a href="listPage.do?method=2"><li><span>Popular Emoticon</span></li></a>
						<a href="listPage.do?method=3"><li><span>Hot Emoticon</span></li></a>
					</ul>
				</div></li>
			<li class="nav-mid-item"><a href="artistListPage.do?method=1">Artist</a>
				<div class="nav-mid-item-drop">
					<img src="emo/images/말꼬리.png" width="180px" height="40px">
					<ul>
						<a href="artistListPage.do?method=1"><li><span>New Artist</span></li></a>
						<a href="artistListPage.do?method=2"><li><span>Popular Artist</span></li></a>
						<a href="artistListPage.do?method=3"><li><span>Most Published Artist</span></li></a>
					</ul>
				</div></li>
			<li class="nav-mid-item"><a href="mypage.do">MyGallery</a>
				<div class="nav-mid-item-drop">
					<img src="emo/images/말꼬리.png" width="180px" height="40px">
					<ul>
						<c:if test="${user!=null}">
							<a href="#"><li><span>Liked Emoticon</span></li></a>
							<a href="#"><li><span>Following Artist</span></li></a>
							<a href="writeui.do"><li><span>Upload Emoticon</span></li></a>
							<a href="mypage.do"><li><span>My Gallery</span></li></a>
						</c:if>
						<c:if test="${user==null}">
							<a href="loginUI.do"><li><span>Liked Emoticon</span></li></a>
							<a href="loginUI.do"><li><span>Following Artist</span></li></a>
							<a href="loginUI.do"><li><span>Upload Emoticon</span></li></a>
							<a href="loginUI.do"><li><span>My Gallery</span></li></a>
						</c:if>
					</ul>
				</div></li>
		</ul>
	</nav>

	<section class="emoticon-container">
		<div class="container-header">
			<h2>New Emoticon</h2>
			<button type="button" onclick="location.href='listPage.do?method=1'">→Browse</button>
		</div>
		<div class="container-main">
			<c:forEach var="dto" items="${listNew}" begin="0" end="3">
				<div class="emoticon-package">
					<a href="retrieve.do?num=${dto.num}">
						<div class="emoticon-Thumbnail">
							<c:forEach var="emo" items="${ticon}" varStatus="status">
								<c:if test="${thumbnum <6 && dto.num eq emo.boardnum}">
									<%
										Thumbnailnum++;
										request.setAttribute("thumbnum", Thumbnailnum);
									%>
									<img id="Thumbnail${thumbnum}" src="emosave/${emo.boardnum}/${emo.src}">
								</c:if>
							</c:forEach>
							<%
								Thumbnailnum = 0;
								request.setAttribute("thumbnum", Thumbnailnum);
							%>
						</div>
					</a>
					<p class="title">
						Title: <a style=" text-decoration: none;" href="retrieve.do?num=${dto.num}">${dto.title}</a>
					</p>
					<p class="artist">Artist: ${dto.author}</p>
					<div class="other"><img id="likes" src="emo/images/likes.png">${dto.likes}　　
						<img src="emo/images/view.png">${dto.readcnt}</div>
				</div>
			</c:forEach>
		</div>
	</section>

	<section class="emoticon-container">
		<div class="container-header">
			<h2>Popular Emoticon</h2>
			<button type="button" onclick="location.href='listPage.do?method=2'">→ Browse</button>
		</div>
		<div class="container-main">
			<c:forEach var="dto" items="${listPop}" begin="0" end="3">
				<div class="emoticon-package">
					<a href="retrieve.do?num=${dto.num}">
						<div class="emoticon-Thumbnail">
							<c:forEach var="emo" items="${ticon}" varStatus="status">
								<c:if test="${thumbnum <6 && dto.num eq emo.boardnum}">
									<%
										Thumbnailnum++;
													request.setAttribute("thumbnum", Thumbnailnum);
									%>
									<img id="Thumbnail${thumbnum}" src="emosave/${emo.boardnum}/${emo.src}">
								</c:if>
							</c:forEach>
							<%
								Thumbnailnum = 0;
									request.setAttribute("thumbnum", Thumbnailnum);
							%>
						</div>
					</a>
					<p class="title">
						Title: <a style=" text-decoration: none;" href="retrieve.do?num=${dto.num}">${dto.title}</a>
					</p>
					<p class="artist">Artist: ${dto.author}</p>
					<div class="other"><img id="likes" src="emo/images/likes.png">${dto.likes}　　
						<img src="emo/images/view.png">${dto.readcnt}</div>
				</div>
			</c:forEach>
		</div>
	</section>

	<section class="emoticon-container">
		<div class="container-header">
			<h2>Hot Emoticon</h2>
			<button type="button" onclick="location.href='listPage.do?method=3'">→ Browse</button>
		</div>
		<div class="container-main">
			<c:forEach var="dto" items="${listHot}" begin="0" end="3">
				<div class="emoticon-package">
					<a href="retrieve.do?num=${dto.num}">
						<div class="emoticon-Thumbnail">
							<c:forEach var="emo" items="${ticon}" varStatus="status">
								<c:if test="${thumbnum <6 && dto.num eq emo.boardnum}">
									<%
										Thumbnailnum++;
													request.setAttribute("thumbnum", Thumbnailnum);
									%>
									<img id="Thumbnail${thumbnum}" src="emosave/${emo.boardnum}/${emo.src}">
								</c:if>
							</c:forEach>
							<%
								Thumbnailnum = 0;
									request.setAttribute("thumbnum", Thumbnailnum);
							%>
						</div>
					</a>
					<p class="title">
						Title: <a style=" text-decoration: none;" href="retrieve.do?num=${dto.num}">${dto.title}</a>
					</p>
					<p class="artist">Artist: ${dto.author}</p>
					<div class="other"><img id="likes" src="emo/images/likes.png">${dto.likes}　　
						<img src="emo/images/view.png">${dto.readcnt}</div>
				</div>
			</c:forEach>
		</div>
	</section>

	<!-- 
	<section id="tags-container">
		<div class="container-header">
			<h2>Styles</h2>
			<button type="button">→ Browse</button>
		</div>
		<div id="styles">
			<div class="tags">
				<ul>
					<li><a href="">abcd</a></li>
					<li><a href="">abcd</a></li>
					<li><a href="">abcd</a></li>
				</ul>
			</div>
		</div>
	</section> 
	-->
	
	 <div id="remote_control">
        <a id="up_button" href="#navbar-top">UP</a>
    </div>
	

	<section id="footer">
		<img src="emo/images/cbnu_white.png" width="221" height="67">
		<p>2019  오픈소스 전문 프로젝트 TEAM 11</p>
		<p>윤송희  전준호  정희주  장형규</p>
		<p>주소 : 충북 청주시 서원구 충대로 1, 충북대학교 S4-1 소프트웨어학과 / TEL : 043)261-2114</p>
	</section>

</body>

</html>