<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.Socket" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SOCKET</title>
    <%
        try {
            Socket s = new Socket("localhost", 8080);

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(s.getInputStream()));
            PrintWriter socketOut = new PrintWriter(s.getOutputStream());

            socketOut.print("GET /pages/index.jsp\n\n");
            socketOut.flush();

            String line;

            while ((line = in.readLine()) != null) {
                out.println(line);
            }

        } catch (Exception e) {
            System.err.println("CATCH");
            System.err.println(e.getLocalizedMessage());
        }
    %>
</head>
<body>

</body>
</html>
