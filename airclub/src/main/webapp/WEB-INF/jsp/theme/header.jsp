<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <header class="row  bg-dark text-white">
 	<div class="col-12">
  		<h1>Aéroclub de ...</h1>
 	</div>
 	<div class="col-12 row justify-content-between">
 		<div class="col-8"><h3 class="d-none d-md-block">Le site de l'aéroclub de ...</h3></div>
 		<div class="col-4">
		 	<sec:authorize access="isAuthenticated()">
		 		<p>Connecté en tant que <sec:authentication property="name"/></p>
		 	</sec:authorize>
	 	</div>
   	</div>
</header>