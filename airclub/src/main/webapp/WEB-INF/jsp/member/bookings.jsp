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
			<p class="h3">Liste de vos résezrvations</p>
		</div>
		<div class="col-12 table-responsive">
			<table class="table table-sm table-striped table-bordered">
				<thead>
					<tr>
						<th>Aéronef</th>
						<th>Description</th>
						<th>Heure départ</th>
						<th>Heure retour</th>
						<th>Action</th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach items="${bookings}" var="booking" varStatus="status">
					<fmt:parseDate value="${booking.departureTime}" pattern="yyyy-MM-dd'T'HH:mm" var="departure" type="date" />
					<fmt:parseDate value="${booking.arrivalTime}" pattern="yyyy-MM-dd'T'HH:mm" var="arrival" type="date" />	
					<tr>
						<td>${booking.aircraft}</td>
						<td>${booking.description}</td>
						<td><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm" value = "${departure}" /></td>
						<td><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm" value = "${arrival}" /></td>				
						<td><c:if test="${ booking.canCancel == true }">Oui</c:if></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
<%@ include file="../theme/footer.jsp" %>
</body>
</html>