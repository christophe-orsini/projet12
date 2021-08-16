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
			<p class="col-8 h3">Liste de vos factures</p>
			<a class="col-2 btn btn-secondary ${btn_1}" href="/financial/invoices" role="button">A régler</a>
			<a class="col-2 btn btn-secondary ${btn_2}" data-toggle="button" href="/financial/invoices/paid" role="button">Payées</a>
		</div>
		<div class="col-12 table-responsive">
			<table class="table table-sm table-striped table-bordered">
				<thead>
					<tr>
						<th class="col-1">Numéro</th>
						<th class="col-2">Aéronef</th>
						<th class="col-2">Description</th>
						<th class="col-1">Date du vol</th>
						<th class="col-1">Durée</th>
						<th class="col-1">Montant</th>
						<th class="col-1">Payé le</th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach items="${flights}" var="flight" varStatus="status">
					<fmt:parseDate value="${flight.flightDate}" pattern="yyyy-MM-dd" var="flightDate" type="date" />
					<tr>
						<td><a href="/financial/invoice/${flight.id}">N° ${flight.id}</a></td>
						<td>${flight.aircraft}</td>
						<td>${flight.lineItem}</td>
						<td><fmt:formatDate type = "both" pattern="dd/MM/yyyy" value = "${flightDate}" /></td>
						<td>${flight.flightHours}</td>
						<c:if test="${ flight.closed == false }">
							<td>${flight.amount} €</td>
							<td>A régler</td>
						</c:if>
						<c:if test="${ flight.closed == true }">
							<td>${flight.payment} €</td>
							<td>${flight.paymentDate}</td>
						</c:if>		
					</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td  colspan="4">Totaux</td>
						<td>${totalTime}</td>
						<td>${totalAmount} €</td>
						<td></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</section>
<%@ include file="../theme/footer.jsp" %>
</body>
</html>