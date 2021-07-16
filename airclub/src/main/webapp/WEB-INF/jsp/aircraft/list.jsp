<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<%@ include file="../theme/head.jsp" %>
<body class="container">
<%@ include file="../theme/header.jsp" %>
<%@ include file="../theme/menu.jsp" %>
	<section class="row justify-content-center">	
		<div class="col-12">
			<p class="h3">Liste des aéronefs</p>
		</div>
		<div class="col-12 table-responsive">
			<table class="table table-sm table-striped table-bordered">
				<caption>Cliquer sur le titre pour voir les détails</caption>
				<thead>
					<tr>
						<th>Immatriculation</th>
						<th>Fabricant</th>
						<th>Modèle</th>
						<th>Tarif horaire</th>
						<th>Disponible</th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach items="${aircrafts}" var="aircraft" varStatus="status">
					<tr>
						<td>${aircraft.registration}</td>
						<td>${aircraft.make}</td>
						<td>${aircraft.model}</td>
						<td>${aircraft.hourlyRate} €/h</td>
						<td><c:if test="${ aircraft.available = true }">Oui</c:if></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
<%@ include file="../theme/footer.jsp" %>
</body>
</html>