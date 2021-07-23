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
		<div class="col-3 mt-1">
			<div class="col row justify-content-between">
				<a class="col-3 btn btn-primary" href="/member/before/${currentDate}" role="button">Avant</a>
				<p class="h5 col-6 border border-primary">${currentDate}</p>
				<a class="col-3 btn btn-primary" href="/member/after/${currentDate}" role="button">Après</a>
			</div>
			<p class="h3">Aéronefs</p>
			<ul class="list-group">
				<c:forEach items="${aircrafts}" var="aircraft" varStatus="status">
					<li><a href="/member/planning/${aircraft.id}/date/${currentDate}">
						${aircraft.registration} ${aircraft.make} ${aircraft.model} ${aircraft.hourlyRate} €/h
					</a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="col-5 table-responsive">
			<p class="h3">Réservations en cours</p>
			<table class="table table-sm table-striped table-bordered">
				<thead>
					<tr>
						<th>Heure départ</th>
						<th>Heure retour</th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach items="${bookings}" var="booking" varStatus="status">
						<tr>
							<fmt:parseDate value="${booking.departureTime}" pattern="yyyy-MM-dd'T'HH:mm" var="departure" type="date" />					
							<fmt:parseDate value="${booking.arrivalTime}" pattern="yyyy-MM-dd'T'HH:mm" var="arrival" type="date" />					
							<td><fmt:formatDate type = "both" pattern="dd-MM HH:mm" value = "${departure}" /></td>
							<td><fmt:formatDate type = "both" pattern="dd-MM HH:mm" value = "${arrival}" /></td>				
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-4">
			<c:if test="${selectedAircraft != null}">
				<p class="h3">Faire votre réservation</p>
				<div class="col-12">
					<c:url value="/member/book" var="action"/>
					<form:form action="${action}" method="post" class="form-row form-inline" modelAttribute="newBooking">   			
						<form:input type="hidden" path="aircraftId" />
						<form:input type="hidden" path="memberId" />
						
						<form:label path="description" class="col">Description :</form:label>
						<form:input type="text" class="form-control form-control-sm col-7 col-md-3"
							path="description" required="required" autofocus="autofocus" />
						
						<form:label path="departureTime" class="col">Départ :</form:label>
						<div class="col row"> 
							<form:input type="date" class="form-control form-control-sm col-6" 
								path="departureDate" required="required"  />
							<form:input type="time" class="form-control form-control-sm col-6" 
								path="departureTime" required="required"  />
						</div>
															
						<form:label path="arrivalTime" class="col">Retour</form:label>
						<div class="col row"> 
							<form:input type="date" class="form-control form-control-sm col-6"
								path="arrivalDate" required="required" />
							<form:input type="time" class="form-control form-control-sm col-6"
								path="arrivalTime" required="required" />	
						</div>								
						<input class="btn btn-primary offset-4 mt-3" type="submit" value="Réservez">						
					</form:form>
				</div>
			</c:if>
		</div>
	</section>
<%@ include file="../theme/footer.jsp" %>
</body>
</html>