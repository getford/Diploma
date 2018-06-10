<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="static by.iba.uzhyhala.util.ReCaptchaUtil.PUBLIC" %>
<%@ page import="by.iba.uzhyhala.util.VariablesUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auth - Auction Diploma</title>
    <meta charset="utf-8">
    <meta name="author" content="Zhyhala">
    <meta name="description" content="Auction Diploma Project auth page"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/index/reset.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/index/main.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/jquery.js"></script>
    <script type="text/javascript" src="/resources/scripts/index/main_head.js"></script>
    <script src='https://www.google.com/recaptcha/api.js'></script>

    <%-- FOR INPUT --%>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/fonts/input/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/input/component.css"/>
</head>
<%
    // if cookie not clear
%>
<body>

<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo">
            <img src="/resources/images/logo.jpg" alt=""
                 title="Auction Diploma Project"/></a>
        <nav>
            <ul>
                <li><a href="/pages/profile.jsp">Профиль</a></li>
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
        <p class="cap_title">Авторизация/Регистрация</p>
    </section>
</section><!-- End billboard -->

<section class="recent_work wrapper">
    <h3 class="S_title">Заполните все поля</h3>
    <div class="container">
        <div class="row">
            <div class="col-sm-5">
                <form action="/auth" method="post">
                    <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-1" name="login_or_email"/>
					<label class="input__label input__label--isao" for="input-1">
						<span class="input__label-content input__label-content--isao">Логин или email</span>
					</label>
                    </span>
                    <br/>
                    <span class="input input--isao">
					<input class="input__field input__field--isao" type="password" id="input-2" name="password"
                           title=""/>
					<label class="input__label input__label--isao" for="input-1">
						<span class="input__label-content input__label-content--isao">Пароль</span>
					</label>
                    </span>
                    <span class="input input--isao">
                    <button class="btn btn-primary" type="submit" name="login">Авторизация</button>
                    </span>
                </form>
            </div>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                Восстановить пароль
            </button>
            <div class="col-sm-5">
                <form action="/registration" method="post">
                    <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-3" name="login"/>
					<label class="input__label input__label--isao" for="input-1">
						<span class="input__label-content input__label-content--isao">Логин</span>
					</label>
                    </span>
                    <br/>
                    <span class="input input--isao">
					<input class="input__field input__field--isao" type="email" id="input-4" name="email"/>
					<label class="input__label input__label--isao" for="input-1">
						<span class="input__label-content input__label-content--isao">email</span>
					</label>
                    </span>
                    <br/>
                    <span class="input input--isao">
					<input class="input__field input__field--isao" type="text" id="input-5" name="password"/>
					<label class="input__label input__label--isao" for="input-1">
						<span class="input__label-content input__label-content--isao">Пароль</span>
					</label>
                    </span>
                    <span class="input input--isao">
                    <div class="g-recaptcha" data-sitekey="<%=PUBLIC%>"></div>
                    <button class="btn btn-primary" type="submit" name="reg">Регистрация</button>
                    </span>
                </form>
            </div>
        </div>
    </div>
</section>

<footer>
    <div class="wrapper">
        <div class="f_cols">
            <p>BLR, Minsk<span class="phone"><%=VariablesUtil.EMAIL_SUPPORT%></span></p>
        </div>

        <div class="f_cols">
            <p>Copyright &copy; 2018<span class="phone"> Uladzimir Zhyhala inc.</span></p>
        </div>

    </div>

</footer><!-- End footer -->

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Восстановление пароля</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/forgetpassword" method="get">
                <div class="modal-body">
                    <input type="text" name="login_email" placeholder="Логин или email">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                    <button type="submit" name="btn_recovery_passcode" class="btn btn-primary">Отправить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
