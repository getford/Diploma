<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add lot - Auction Diploma</title>
    <meta charset="utf-8">
    <meta name="author" content="Zhyhala">
    <meta name="description" content="Auction Diploma Project index page"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/index/reset.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/index/main.css">
    <script type="text/javascript" src="/resources/scripts/index/jquery.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/main_head.js"></script>

    <%-- FOR INPUT --%>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/fonts/input/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/component.css"/>
    <%
        String userLogin = CommonUtil.getUserLoginFromCookie(request);
        if (StringUtils.isBlank(userLogin))
            response.sendRedirect("/pages/auth.jsp");
    %>
</head>
<body>

<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo"><h1><span style="color: black">AUCTION DIPLOMA</span></h1> <%--<img src="/resources/images/index/logo.png" alt=""
                                                      title="Auction Diploma Project"/> --%></a>
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
        <p class="cap_title">Добавим лот?</p>
        <p class="cap_desc">Скоро тут будет шикар...подожди-подожди...ная площадка для торгов</p>
    </section>
</section><!-- End billboard -->

<br/><br/>
<section class="recent_work wrapper">
    <h3 class="S_title">Заполните все поля</h3>
    <form action="/lothandler" method="post">
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-1" name="name_lot"/>
					<label class="input__label input__label--isao" for="input-1">
						<span class="input__label-content input__label-content--isao">Название лота</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-2" name="info_lot"/>
					<label class="input__label input__label--isao" for="input-2">
						<span class="input__label-content input__label-content--isao">Информация о лоте</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-3" name="cost"/>
					<label class="input__label input__label--isao" for="input-3">
						<span class="input__label-content input__label-content--isao">Начальная цена</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-4" name="blitz"/>
					<label class="input__label input__label--isao" for="input-4">
						<span class="input__label-content input__label-content--isao">Блиц цена</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-5" name="step"/>
					<label class="input__label input__label--isao" for="input-5">
						<span class="input__label-content input__label-content--isao">Шаг цены</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="date" id="input-6" name="date_start"/>
					<label class="input__label input__label--isao" for="input-6">
						<span class="input__label-content input__label-content--isao">Когда на торги?</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="time" id="input-7" name="time_start"/>
					<label class="input__label input__label--isao" for="input-7">
						<span class="input__label-content input__label-content--isao">В какое время начать?</span>
					</label>
        </span>
        <br/>
        <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-8" name="cat"/>
					<label class="input__label input__label--isao" for="input-8">
						<span class="input__label-content input__label-content--isao">Категория</span>
					</label>
        </span>
        <br/>
        <button type="submit" name="add_lot">Добавить лот</button>
    </form>
</section>
<!-- End recent_work -->

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
</body>
</html>