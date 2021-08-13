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
			<p class="h3">Erreur</p>
		</div>
		<div class="col-12">
			<p>${exceptionMessage.getMessage()}</p>
			<a class="col-3 btn btn-primary" href="/member/planning" role="button">Retour</a>
		</div>	
	</section>
</body>
</html>