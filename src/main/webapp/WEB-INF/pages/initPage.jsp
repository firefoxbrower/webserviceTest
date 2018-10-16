<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/16
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>webservice</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-css/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-css/uploadify.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-css/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-css/demo.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"  charset="GBK"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"  charset="GBK"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.uploadify.js"  charset="GBK"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.uploadify.min.js"  charset="GBK"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/swfobject.js"  charset="GBK"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/EasyUIComTree.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#submit_btn").click(function() {
               var data = JSON.stringify($("#webserviceForm").serializeObject());
               alert(data);
               <%--return false;--%>
                <%--$.ajax({--%>
                    <%--url : "${pageContext.request.contextPath}/websvr.do?method=savePaper",--%>
                    <%--type : "POST",--%>
                    <%--data : data,--%>
                    <%--success : function() {--%>
                        <%--location.reload();--%>
                    <%--}--%>
                <%--});--%>
            });
        })
    </script>
</head>
<body>
<%--<script>--%>
     <%--window.location.href='${pageContext.request.contextPath}/service/sendFile.ws?wsdl';--%>
<%--</script>--%>
<div id="content_div">
    <form name="webservcieForm" name="thisForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/websvr.do?method=savePaper" >
        <table class="editTable" cellspacing="0" cellpadding="0">
            <tr>
                <td style="text-align: right;">项目名字:<span class="mustSpan">[*]</span></td>
                <td>
                    <%-- <input type="text" class="required"  size="60" name="paperTitle" id="paperTitle" value="${paper.paperTitle}"> --%>
                    <select name="prjName" style="width:370px">
                        <option value="1">aaa</option>
                        <option value="2">bbb</option>
                        <option value="3">ccc</option>
                    </select>
                </td>
            </tr>
         
            <tr>
                <td style="text-align: right;">提交人:<span class="mustSpan">[*]</span></td>
                <td>
                    <select name="sendTo" style="width:370px">
                        <option value="冯静">冯静</option>
                        <option value="刘小林">刘小林 </option>
                        <option value="曾部长">曾部长</option>
                    </select>
                </td>
            </tr>
            <tr style="text-align: right;">
                <td style="text-align: right;">上传文件:<span class="mustSpan">[*]</span></td>
                <td><input type="file" name="file"></td>
            </tr>
            <tr>
                <th colspan=2>
                    <center><input type="button" value="确定" id="submit_btn" >&nbsp;&nbsp;&nbsp;<input type="button" value="取消" onclick="cacleBun();"></center>
                </th>
            </tr>
        </table>
        <input type="hidden" name="prjId" id="prjId" value="prjId001">
        <input type="hidden" name="submitterID" id="submitterID" value="submitter001">
    </form>
</div>
  </body>
</html>
