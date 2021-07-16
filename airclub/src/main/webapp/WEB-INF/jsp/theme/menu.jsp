<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light row">
	<div class="container-fluid">
		<a class="navbar-brand" href="/">Aéroclub de ...</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"	data-bs-target="#menu">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="menu">	
			<sec:authorize access="isAuthenticated()">
				<a class="nav-link" href="/aircraft/list">Liste des aéronefs</a>
				<a class="nav-link" href="/aircraft/F-GCNS">F-GCNS</a>
			</sec:authorize>
		</div>
	</div>
</nav>