<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	int Thumbnailnum = 0;
	request.setAttribute("thumbnum", Thumbnailnum);
%>
<!DOCTYPE html>
<html>

<head>
 	
    <link href='https://fonts.googleapis.com/css?family=Dekko' rel='stylesheet'>
    <link href='https://fonts.googleapis.com/css?family=Didact Gothic' rel='stylesheet'>
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
   <link type="text/css" rel="stylesheet"
	href="emo/assets/css/mypage.css">

    <title>SharEmo - Free Emoticon Share Website</title>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="emo/assets/js/dropbox2.js">
    </script>
    <script type="text/javascript" src="emo/assets/js/preview.js">
    </script>
    
<script type="text/javascript" src="emo/assets/js/dropbox.js">
	
</script>
    <script>
        $('.fun-btn').on('click', function (event) {
            $(this).toggleClass('start-fun');
            var $page = $('.page');
            $page.toggleClass('color-bg-start')
                .toggleClass('bg-animate-color');


            $(this).hasClass('start-fun') ?
                $(this).text('stop the fun') :
                $(this).text('start the fun');

        });
    </script>
    <style>
        .wrap {
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .button {
            width: 140px;
            height: 45px;
            font-family: 'Roboto', sans-serif;
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 2.5px;
            font-weight: 500;
            color: #000;
            background-color: #fff;
            border: none;
            border-radius: 45px;
            box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease 0s;
            cursor: pointer;
            outline: none;
        }

        .button:hover {
            background-color: #2EE59D;
            box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
            color: #fff;
            transform: translateY(-7px);
        }
    </style>
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
            <input type="search" name="searchValue" placeholder="Search for emoticons e.g. happy, sad, angry...">
            <button type="submit">
                <img src="emo/images/musica-searcher.png" width="20px" height="20px">
            </button>
        </form>
        <ul>
            <li class="nav-mid-item"><a href="main.do">Home</a></li>
            <li class="nav-mid-item"><a href="listPage.do?method=1">Emotion</a>
                <div class="nav-mid-item-drop">
                    <ul>
                        <a href="listPage.do?method=1">
                            <li><span>New</span></li>
                        </a>
                        <a href="listPage.do?method=2">
                            <li><span>Popular</span></li>
                        </a>
                        <a href="listPage.do?method=3">
                            <li><span>Hot</span></li>
                        </a>
                    </ul>
                </div>
            </li>
            <li class="nav-mid-item"><a href="#">Artist</a>
                <div class="nav-mid-item-drop">
                    <ul>
                        <a href="#">
                            <li><span>New</span></li>
                        </a>
                        <a href="#">
                            <li><span>Popular</span></li>
                        </a>
                        <a href="#">
                            <li><span>Most<br />followed</span></li>
                        </a>
                    </ul>
                </div>
            </li>
            <li class="nav-mid-item"><a href="mypage.do">MyGallery</a>
                <div class="nav-mid-item-drop">
                    <ul>
                        <c:if test="${user!=null}">
                            <a href="#">
                                <li><span>Like</span></li>
                            </a>
                            <a href="#">
                                <li><span>Follow</span></li>
                            </a>
                            <a href="writeui.do">
                                <li><span>Upload</span></li>
                            </a>
                            <a href="mypage.do">
                                <li><span>My Gallery</span></li>
                            </a>
                        </c:if>
                        <c:if test="${user==null}">
                            <a href="loginUI.do">
                                <li><span>Like</span></li>
                            </a>
                            <a href="loginUI.do">
                                <li><span>Follow</span></li>
                            </a>
                            <a href="loginUI.do">
                                <li><span>Upload</span></li>
                            </a>
                            <a href="loginUI.do">
                                <li><span>My Gallery</span></li>
                            </a>
                        </c:if>
                    </ul>
                </div>
            </li>
        </ul>
    </nav>
   
    <section id="container_mypage">
        <section id="content_mypage">
            <div>
                <h1 style="text-align: center; font-size: 30px">${user.id}'s PAGE</h1>
                <img src="${user.mascot}", width="300" , height="300" />

                <p>Nickname : ${user.nickname}</p>
                <p>Name : ${user.name}</p>
                <p>Phone : ${user.phone}</p>
                <p>Email : ${user.email}</p>

                <hr />
                <table>
                    <tr>
                        <td style="width: 1100px">
                            <p style="text-align: left; padding-left:20px">upload emoticon</p>
                        </td>
                        <td>
                            <div class="wrap"><button class="button">Browse</button></div>
                        </td>
                    </tr>                
                </table>
                <table>
                <div class="container-main"></div>
			
				<div class="emoticon-package">
					<a href="retrieve.do?num=${dto.num}"></a>
						<div class="emoticon-Thumbnail" >
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
						Title: <a href="retrieve.do?num=${dto.num}">${dto.title}</a>
					</p>
					<p class="artist">Artist: ${dto.author}</p>
					<div class="other"><img id="likes" src="emo/images/likes.png">${dto.likes}　　
						<img src="emo/images/view.png">${dto.readcnt}</div>
				</div>
                </table>
                <hr />
                <table>
                        <tr>
                            <td style="width: 1100px">
                            <p style="text-align: left; padding-left:20px">like emoticon</p>
                        </td>
                        <td>
                            <div class="wrap"><button class="button">Browse</button></div>
                        </td>
                        </tr>
                      
                    </table>


                <hr />
                <table>
                        <tr>
                             <td style="width: 1100px">
                            <p style="text-align: left; padding-left:20px">follow emoticon</p>
                        </td>
                        <td>
                            <div class="wrap"><button class="button">Browse</button></div>
                        </td>
                        </tr>
                        
                    </table>
            </div>
        </section>
    </section>

    <section id="footer">
        <img src="emo/images/cbnu_white.png" width="221" height="67">
        <p>2019 오픈소스 전문 프로젝트 TEAM 11</p>
		<p>윤송희 전준호 정희주 장형규</p>
		<p>주소 : 충북 청주시 서원구 충대로 1, 충북대학교 S4-1 소프트웨어학과 / TEL : 043)261-2114</p>
    </section>

</body>

</html>