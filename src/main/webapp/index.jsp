<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<body>
<h2>Hello SangBigYe!</h2>
</body>
<script>
    window.location.href='${pageContext.request.contextPath}/services/IBankingService?wsdl';
</script>

</html>
