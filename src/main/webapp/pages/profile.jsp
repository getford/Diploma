<%@ page import="by.iba.uzhyhala.entity.PersonalInformationEntity" %>
<%@ page import="by.iba.uzhyhala.user.Profile" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile - Auction Diploma</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%
        Profile profile = new Profile(request.getParameter("user"));

        List<PersonalInformationEntity> personalInformationEntity = profile.getUserPersonalInformation();
        assert personalInformationEntity != null;%>
</head>
<body>
<h1><%=personalInformationEntity.get(0).getLastName()%>
</h1>
</body>
</html>
