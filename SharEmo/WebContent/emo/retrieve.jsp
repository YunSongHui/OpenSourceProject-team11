<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.dao.BoardDAO, com.entity.BoardDTO"%>
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
	href="emo/assets/css/retrieve_style.css">

<title>SharEmo - Free Emoticon Share Website</title>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="emo/assets/js/dropbox2.js">
</script>

<script> 
function img_resize() { 
	var maxsize = 200; 
	var content = document.getElementById("emoticon-package"); 
	var img = content.getElementsByTagName("img"); 
	for(i=0; i<img.length; i++) { 
	if ( eval('img[' + i + '].width > maxsize') ) { 
		eval('img[' + i + '].width = maxsize');
 			}
 		} 
} 
window.onload = img_resize; 

function delete_board(){
	if(confirm("정말로 삭제하시겠습니까?")){
		location.href='delete.do?num=${retrieve.num}';
	}
}
</script>

<script>

$(document).ready(function changeColor(){  
	if(${isFollow}){
		$("#follow").css("background-color", "rgb(255, 81, 106)");
	}
	else{
		$("#follow").css("background-color", "gray")
	}
	
	if(${isLike}){
		$("#likes").css("background-color", "rgb(0, 131, 255)");
	}
	else{
		$("#likes").css("background-color", "gray");
	}
}); 

	function like() {
		if( ${user != null}){
			$.ajax({
				url : "like.do",
				type : "POST",
				data : {
					num : "${retrieve.num}",
					userid : "${user.id}"
				},
				success : function(data) {
					//alert("'좋아요'가 반영되었습니다!");
					// data중 put한 것의 이름 like 
					var likes=data.split('\n');
					
					if(likes[1].indexOf("true") > -1 ){
						$("#likes").css("background-color", "rgb(0, 131, 255)");
					}
					else{
						$("#likes").css("background-color", "gray");
					}
					
					$("#likes_num").html(likes[0]);
					//id값이 like_result인 html을 찾아서 data.like값으로 바꿔준다. 
				},
				error : function(request, status, error) {
					alert("오류");
				}
			});
		}
		else{
			alert("로그인이 필요합니다.");
		}
	}
	function follow() {
		if( ${user != null} ){
			$.ajax({
				url : "follow.do",
				type : "POST",
				data : {
					follow : "${retrieve.userid }",
					follower : "${user.id}"
				},
				success : function(data) {
					//alert("'좋아요'가 반영되었습니다!");
					// data중 put한 것의 이름 like 
					var follow=data.split('\n');
					if(follow[1].indexOf("true") > -1){
						$("#follow").css("background-color", "rgb(255, 81, 106)");
					}
					else{
						$("#follow").css("background-color", "gray")
					}
					$("#follower_num").html(follow[0]);
					//id값이 like_result인 html을 찾아서 data.like값으로 바꿔준다. 
				},
				error : function(request, status, error) {
					alert("오류");
				}
			});
		}
		else{
			alert("로그인이 필요합니다.");
		}
	}
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
						<li class="nav-top-item"><img src="${user.mascot}"> <a
							href='mypage.do'>${user.id}님</a></li>
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
		<nav id="nav-mid-left">
			<ul>
				<li><a href="listPage.do?method=1">New Emoticon</a></li>
				<li><a href="listPage.do?method=2">Popular Emoticon</a></li>
				<li><a href="listPage.do?method=3">Hot Emoticon</a></li>
			</ul>
		</nav>
		<section id="content">
			<div id="content-wrapper">
				<p>view : ${retrieve.readcnt}</p>
				<h2>${retrieve.title}</h2>
				<div id="detail-wrapper">
					<div id="detail-image">
						<c:if test="${ticon!=null}">
						<img class="emoticon-Thumbnail"
							src="emosave/${ticon[0].boardnum}/${ticon[0].src}">
							</c:if><br />
						<button id="likes" type="button" onclick="return like();">
							<img src="emo/images/likes_white.png">
						</button>
						<button id="follow" type="button" onclick="return follow();">
							<img src="emo/images/follow_white.png">
						</button>
					</div>
					<fieldset id="detail">
						<legend>detail</legend>
						<p>
							<b>ARTIST :</b> ${retrieve.author}
						</p>
						<p>
							<b>LIKES : </b> <span id="likes_num">${retrieve.likes}</span>
						</p>
						<p>
							<b>FOLLOW : </b><span id="follower_num">${followernum}</span>
						</p>
						<p>
							<b>DESCRIPTION</b>
						</p>
						<textarea rows="6" cols="50" disabled>${retrieve.content}</textarea>
					</fieldset>
				</div>
				<!-- 
				<div id="tags">
					<p>tags</p>
					<ul>
						<li><a href="#">#animation</a></li>
						<li><a href="#">#cat</a></li>
						<li><a href="#">#japan</a></li>
						<li><a href="#">#cute</a></li>
						<li><a href="#">#doraemon</a></li>
					</ul>
				</div>
				 -->
				<div id="emoticon-package">
					<table>
						<c:forEach var="emo" items="${ticon}" varStatus="status">
							<c:if test="${status.index % 4 eq 0}">
								<tr>
							</c:if>
							<td><img class="emoticon-Thumbnail"
								src="emosave/${emo.boardnum}/${emo.src}"></td>
							</td>
							<c:if test="${status.count % 4 eq 0}">
								</tr>
							</c:if>
						</c:forEach>
					</table>

					<button id="download_btn" type="button"
						onclick="location.href='download.do?num=${retrieve.num}'">DOWNLOAD</button>

					<div id="other_btns">
						<c:if test="${user.id==retrieve.userid || user.id=='admin' }">
							<button type="button" id="edit_btn"
								onclick="location.href='updateUI.do?num=${retrieve.num}'">
								<img src="emo/images/edit.png">
							</button>
							<button type="button" id="delete_btn"
								onclick="delete_board()">
								<img src="emo/images/delete.png">
							</button>
						</c:if>
						<button type="button"
							onclick="location.href='listPage.do?method=1'">
							<img src="emo/images/exit.png">
						</button>
					</div>
				</div>
			</div>
		</section>
		<div id="ad">
			<a href="https://www.idowell.co.kr/home/" target="_blank"> <img
				src="emo/images/ad/winnerstel.png">
			</a> <a
				href="https://www.duo.co.kr/html/love_test/main.asp?u_div=agency1_DA5_2019&utm_medium=double&utm_source=kakao_banner&utm_campaign=DT_%EB%93%80%EC%98%A4pc&utm_term=%EB%A6%AC%ED%83%80%EA%B2%9F"
				target="_blank"> <img src="emo/images/ad/duo.jpg">
			</a>
		</div>
	</section>

	<section id="footer">
		<img src="emo/images/cbnu_white.png" width="221" height="67">
		<p>2019 오픈소스 전문 프로젝트 TEAM 11</p>
		<p>윤송희 전준호 정희주 장형규</p>
		<p>주소 : 충북 청주시 서원구 충대로 1, 충북대학교 S4-1 소프트웨어학과 / TEL : 043)261-2114</p>
	</section>

</body>

</html>

