<%@ page import="by.iba.uzhyhala.entity.LotEntity" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getLots" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.ENTITY_LOT" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.FOLDER_UPLOAD_IMAGES" %>
<%@ page import="static java.io.File.separator" %>
<%@ page import="static by.iba.uzhyhala.util.CommonUtil.getLotsLimitRows" %>
<%@ page import="static by.iba.uzhyhala.util.VariablesUtil.LIMIT_SELECT_LOT" %>
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
                <%
                    CookieUtil cookieUtil = new CookieUtil(request);
                    if (cookieUtil.isFindCookie() && cookieUtil.isAdmin()) {
                %>
                <li><a href="/pages/admin.jsp">Админ</a></li>
                <%
                } else {
                %>
                <li><a style="pointer-events: none" href="/pages/admin.jsp">Админ</a></li>
                <%
                    }
                %>
                <li><a href="">Обратная связь</a></li>
            </ul>
        </nav>
    </div>

</header><!-- End Header -->

<section class="billboard">
    <section class="caption">
    </section>
</section><!-- End billboard -->

<br/><br/>
<section class="recent_work wrapper">
    <h3 class="S_title">Новые лоты</h3>
    <%
        List<LotEntity> lotInfoList = getLotsLimitRows("SELECT l FROM " + ENTITY_LOT + " l ORDER BY id DESC", LIMIT_SELECT_LOT);
        for (LotEntity lot : lotInfoList) {
            String id = lot.getUuid();
            String lotName = lot.getName();
            String imagesLot = separator + FOLDER_UPLOAD_IMAGES + separator + lot.getImagesName();
    %>
    <div class="work">
        <a href="/pages/lot.jsp?uuid=<%=id%>" class="work_img">
            <img src="<%=imagesLot%>" alt="" title="<%=lotName%>"/>
        </a>
        <a href="/pages/lot.jsp?uuid=<%=id%>" class="work_title">
            <%=lotName%>
            <i></i>
        </a>
    </div>
    <%
        }
    %>
</section>

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