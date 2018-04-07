<%@ page import="by.iba.uzhyhala.entity.PersonalInformationEntity" %>
<%@ page import="by.iba.uzhyhala.user.Logout" %>
<%@ page import="by.iba.uzhyhala.user.Profile" %>
<%@ page import="by.iba.uzhyhala.util.CookieUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile - Auction Diploma</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        Profile profile = null;

        List<PersonalInformationEntity> personalInformationEntity = null;
        if (cookieUtil.isFindCookie()) {
            profile = new Profile(request.getParameter("user").toLowerCase());
            profile.getUserPersonalInformation();

            personalInformationEntity = profile.getPersonalInformationList();
        } else {
            new Logout(request, response);
        }
        assert personalInformationEntity != null;
    %>
</head>
<body>
<%--
<h1><%=profile.getUuidUser()%>
</h1>
<h1><%=personalInformationEntity.get(0).getLastName()%>
</h1>
--%>
</body>
</html>