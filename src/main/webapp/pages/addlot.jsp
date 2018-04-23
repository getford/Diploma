<%@ page import="by.iba.uzhyhala.util.CommonUtil" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
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
        CookieUtil cookieUtil = new CookieUtil(request);
        String userLogin = CommonUtil.getUserLoginByUUID(cookieUtil.getUserUuidFromToken());
        //   if (!StringUtils.isBlank(uuidUser)) {
        try {

        } catch (Exception e) {
            response.getWriter().write("CATCH BLOCK ERROR");
        }
        //   }
    %>
</head>
<body>

<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo"><h1><span style="color: black">AUCTION DIPLOMA</span></h1> <%--<img src="/resources/images/index/logo.png" alt=""
                                                      title="Auction Diploma Project"/> --%></a>
        <nav>
            <ul>
                <li><a href="/pages/profile.jsp?user=<%=userLogin%>">Профиль</a></li>
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
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="text" id="input-1" name="name_lot"/>
					<label class="input__label input__label--madoka" for="input-1">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Название лота</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="text" id="input-2" name="info_lot"/>
					<label class="input__label input__label--madoka" for="input-2">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Информация о лоте</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="text" id="input-3" name="cost"/>
					<label class="input__label input__label--madoka" for="input-3">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Начальная цена</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="text" id="input-4" name="blitz"/>
					<label class="input__label input__label--madoka" for="input-4">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Блиц цена</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="text" id="input-5" name="step"/>
					<label class="input__label input__label--madoka" for="input-5">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Шаг цены</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="date" id="input-6" name="date_start"/>
					<label class="input__label input__label--madoka" for="input-6">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Когда на торги?</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="time" id="input-7" name="time_start"/>
					<label class="input__label input__label--madoka" for="input-7">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">В какое время начать?</span>
					</label>
        </span>
        <br/>
        <span class="input input--madoka">
					<input class="input__field input__field--madoka" type="text" id="input-8" name="cat"/>
					<label class="input__label input__label--madoka" for="input-8">
						<svg class="graphic graphic--madoka" width="100%" height="100%" viewBox="0 0 404 77"
                             preserveAspectRatio="none">
							<path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
						</svg>
						<span class="input__label-content input__label-content--madoka">Категория</span>
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