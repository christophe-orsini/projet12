<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<%@ include file="head.jsp" %>
<body class="container">
<%@ include file="header.jsp" %>
<%@ include file="../theme/menu.jsp" %>
	<section class="row">
		<div class="col-12">
			<p class="h3">Bravo !</p>
		</div>
		<div class="col-12">
			<p>L'opération demandée a réussie</p>
			<p>${message}</p>
		</div>	
	</section>
</body>
</html>