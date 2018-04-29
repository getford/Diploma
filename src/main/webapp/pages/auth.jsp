<%@ page import="by.iba.uzhyhala.util.ReCaptchaUtil" %>
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
<body>

<header>

    <div class="wrapper">
        <a href="/pages/index.jsp" class="logo"><h1><span style="color: black">AUCTION DIPLOMA</span></h1> <%--<img src="/resources/images/index/logo.png" alt=""
                                                      title="Auction Diploma Project"/> --%></a>
        <nav>
            <ul>
                <li><a href="/pages/profile.jsp">Профиль</a></li>
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
        <p class="cap_title">Авторизация/Регистрация</p>
        <p class="cap_desc">Скоро тут будет шикар...подожди-подожди...ная площадка для торгов</p>
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
                    <button type="submit" name="login">Log in</button>
                    </span>
                </form>
            </div>

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
						<span class="input__label-content input__label-content--isao">Пароль</span>
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
                    <div class="g-recaptcha" data-sitekey="<%=ReCaptchaUtil.PUBLIC%>"></div>
                    <button type="submit" name="reg">REG</button>
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
