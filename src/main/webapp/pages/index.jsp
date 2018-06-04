<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index - Auction Diploma</title>
    <meta charset="utf-8">
    <meta name="author" content="Zhyhala">
    <meta name="description" content="Auction Diploma Project index page"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/index/reset.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/index/main.css">
    <script type="text/javascript" src="/resources/scripts/index/jquery.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/main_head.js"></script>
    <%
        String userLogin = CookieUtil.getUserLoginFromCookie(request);
        boolean isDisableLink = false;
        if (!StringUtils.isBlank(userLogin)) {
            isDisableLink = true;
        }
//        if (!StringUtils.isBlank(userLogin)) {
//            userLogin = "";
//            try {
//
//            } catch (Exception e) {
//                response.getWriter().write("CATCH BLOCK ERROR");
//            }
//        }
    %>
</head>
<body>

<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo">
            <img src="/resources/images/logo.jpg" alt=""
                 title="Auction Diploma Project"/></a>
        <nav>
            <ul>
                <li><a href="/pages/profile.jsp?user=<%=userLogin%>">Профиль</a>(<%=userLogin%>)</li>
                <li><a href="/pages/addlot.jsp">Добавить лот</a></li>
                <li><a href="/pages/auth.jsp">Авторизация/Регистрация</a></li>
                <li><a href="/pages/admin.jsp">Админ</a></li>
                <li><a href="">Обратная связь</a></li>
            </ul>
        </nav>
    </div>

</header><!-- End Header -->

<section class="billboard">
    <section class="caption">
        <p class="cap_title">Аукцион услуг</p>
    </section>
</section><!-- End billboard -->

<!--	<section class="services wrapper">
		<ul>
			<li>
				<index class="s_icons" src="/index/s1_icon.png" title="" alt=""/>
				<h3>Duis aute irure</h3>
				<p>Excepteur sint occaecat cupi datat non it proident, sunt in culpa qui officia dese runtorn mollit anim id est laborum.</p>
			</li>
			<li>
				<index class="s_icons" src="/index/s2_icon.png" title="" alt=""/>
				<h3>Duis aute irure</h3>
				<p>Excepteur sint occaecat cupi datat non it proident, sunt in culpa qui officia dese runtorn mollit anim id est laborum.</p>
			</li>
			<li>
				<index class="s_icons" src="/index/s3_icon.png" title="" alt=""/>
				<h3>Duis aute irure</h3>
				<p>Excepteur sint occaecat cupi datat non it proident, sunt in culpa qui officia dese runtorn mollit anim id est laborum.</p>
			</li>
		</ul>
	</section> End services -->


<!-- <section class="video wrapper">
    <h3>sunt in culpa qui officia deserunt mollit</h3>
    <p>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
    <div class="ex_video">
        <a href="#"><index src="/index/video.jpg" alt="" title=""/></a>
    </div>
</section>	End video -->
<br/><br/>
<section class="recent_work wrapper">
    <h3 class="S_title">Новые лоты</h3>
    <input type="hidden" id="start">

    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 2
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 3
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 4
            <i></i>
        </a>
    </div>
</section>
<section class="recent_work wrapper">

    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 2
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 3
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 4
            <i></i>
        </a>
    </div>
</section>
<section class="recent_work wrapper">

    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 2
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 3
            <i></i>
        </a>
    </div>
    <div class="work">
        <a href="#" class="work_img">
            <img src="/resources/images/index/work_image.jpg" alt="" title=""/>
        </a>
        <a href="#" class="work_title">
            Brand Project 4
            <i></i>
        </a>
    </div>
</section>
<!-- End recent_work -->

<%--<section class="social">
    <a href="http://facebook.com/pixelhint" target="_blank" class="fb"></a>
    <a href="http://twitter.com/pixelhint" target="_blank" class="t"></a>
    <a href="http://dribbble.com/pixelhint" target="_blank" class="d"></a>
    <a href="#" target="_blank" class="g"></a>
</section><!-- End social -->--%>


<footer>
    <div class="wrapper">
        <div class="f_cols">
            <p>BLR, Minsk<span class="phone"><%=VariablesUtil.EMAIL_SUPPORT%></span></p>
        </div>

        <%-- <div class="f_cols">
             <h3>Company</h3>
             <ul>
                 <li><a href="#">Our Story</a></li>
                 <li><a href="#">Mission</a></li>
                 <li><a href="#">Journal</a></li>
                 <li><a href="#">Careers</a></li>
             </ul>
         </div>--%>

        <div class="f_cols">
            <h3>Support</h3>
            <ul>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">Contact Us</a></li>
                <li><a href="#">Policies</a></li>
            </ul>
        </div>

        <div class="f_cols">
            <p>Copyright &copy; 2018<span class="phone"> Uladzimir Zhyhala inc.</span></p>
        </div>


    </div>
</footer><!-- End footer -->

<div id="lightbox"></div>
<div id="box">
    <a href="" class="close"></a>
    <form method="" action="">
        <input type="text" id="email" placeholder="Email"/>
        <input type="text" id="name" placeholder="Name"/>
        <input type="submit" id="submit" value="Subscribe Now"/>
    </form>
</div>
</body>
</html>