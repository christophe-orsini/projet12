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
		<div class="col-12 row">
			<p class="col-8 h3">Liste de vos réservations</p>
			<a class="col-2 btn btn-secondary ${btn_1}" href="/member/bookings" role="button">En cours</a>
			<a class="col-2 btn btn-secondary ${btn_2}" data-toggle="button" href="/member/bookings/closed" role="button">Terminées</a>
		</div>
		<div class="col-12 table-responsive">
			<table class="table table-sm table-striped table-bordered">
				<thead>
					<tr>
						<th class="col-1">Numéro</th>
						<th class="col-2">Aéronef</th>
						<th class="col-2">Description</th>
						<th class="col-1">Heure départ</th>
						<th class="col-1">Heure retour</th>
						<th class="col-2"></th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach items="${bookings}" var="booking" varStatus="status">
					<fmt:parseDate value="${booking.departureTime}" pattern="yyyy-MM-dd'T'HH:mm" var="departure" type="date" />
					<fmt:parseDate value="${booking.arrivalTime}" pattern="yyyy-MM-dd'T'HH:mm" var="arrival" type="date" />	
					<tr>
						<td>${booking.id}</td>
						<td>${booking.aircraft}</td>
						<td>${booking.description}</td>
						<td><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm" value = "${departure}" /></td>
						<td><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm" value = "${arrival}" /></td>
						<c:if test="${ booking.closed == false }">
							<td>
								<a class="col-5 btn btn-primary" href="/member/bookings/close/${booking.id}" role="button">Clôturer</a>
								<c:if test="${ booking.canCancel == true }">
									<a class="col-5 btn btn-primary" href="/member/bookings/delete/${booking.id}" role="button">Annuler</a>
								</c:if>
							</td>
						</c:if>
						<c:if test="${ booking.closed == true }">
							<td>Terminée</td>
						</c:if>			
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
<%@ include file="../theme/footer.jsp" %>
</body>
</html>