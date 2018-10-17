<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>webservice</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    "<script src="https://lib.baomitu.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://lib.baomitu.com/jquery.form/3.51/jquery.form.js"></script>

</head>
<body>
<div class="container">
    <h2>提交表单</h2>


    <%--<form name="serForm" action="${pageContext.request.contextPath}/websvr.do?method=upload" method="post"  enctype="multipart/form-data">--%>
    <%--<p><input type="test" name="username" placeholder="请输入用户名。。。" autofocus="autofocus"/></p>--%>
    <%--<input type="file" name="selectFile">--%>
    <%--<p><input type="submit" value="提交"/></p>--%>

    <%--</form>--%>
    <form enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/websvr.do?method=upload" id='addForm'>
        <h1>采用流的方式上传文件</h1>
        <input type="file" name="file">
        <input type="submit" value="upload"/>
    </form>
</div>

</body>
</html>
