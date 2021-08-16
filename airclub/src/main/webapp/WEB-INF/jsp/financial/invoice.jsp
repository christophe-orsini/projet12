<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<%@ include file="../theme/head.jsp" %>
<body class="container">
	<section class="col-10 row justify-content-center">	
		<div class="col-12 row">
			<div class="col-6">
				<p class="h3">Aéroclub de ...</p>
				<p>Adresse</p>
				<p>CP, Ville</p>
			</div>
			<div class="col-6">
				<p class="h3">Facture</p>
			</div>
		</div>
		<div class="col-12 row">
			<div class="col-6">
				<fmt:parseDate value="${invoice.flightDate}" pattern="yyyy-MM-dd" var="flightDate" type="date" />
				<p class="h4">Numéro : ${invoice.id}</p>
				<p>Date : <fmt:formatDate type="date" pattern="dd/MM/yyyy" value = "${flightDate}" /></p>
			</div>
			<div class="col-6 row">
				<p class="h3">${givenName} ${familyName}</p>
				<p>Email : ${email}</p>
			</div>
		</div>
		<div class="col-12 row">
			<div class="col-12">
				<p class="h4">Détail de la facture</p>
			</div>
			<div class="col-12 table-responsive">
			<table class="table table-sm table-striped table-bordered">
				<thead>
					<tr>
						<th class="col-2">Aéronef</th>
						<th class="col-3">Description</th>
						<th class="col-1">Date du vol</th>
						<th class="col-1 text-center">Durée</th>
						<th class="col-1">Montant unitaire HT</th>
						<th class="col-1">Montant total HT</th>
					</tr>
				</thead>	
				<tbody>
					<tr>
						<td>${invoice.aircraft}</td>
						<td>${invoice.lineItem}</td>
						<td><fmt:formatDate type = "date" pattern="dd/MM/yyyy" value = "${flightDate}" /></td>
						<td style="text-align: center">${invoice.flightHours}</td>
						<td style="text-align: right"><c:out value="${invoice.amount / invoice.flightHours}" /> €</td>					
						<td style="text-align: right">${invoice.amount} €</td>					
					</tr>
				</tbody>
				<tfoot>
					<tr style="text-align: right" >
						<td colspan="5">Total HT</td>
						<td>${invoice.amount} €</td>
					</tr>
					<tr style="text-align: right">
						<td colspan="5">Total TTC</td>
						<td>${invoice.amount} €</td>
					</tr>
					<tr style="text-align: right">
						<td colspan="5">Déjà payé</td>
						<td>${invoice.payment} €</td>
					</tr>
					<tr style="text-align: right">
						<th colspan="5">Reste dû</th>
						<th><c:out value="${invoice.amount - invoice.payment}" /> €</th>
					</tr>
				</tfoot>
			</table>
			</div>
		</div>
		<div class="col-12 row">
			<p>Facture en Euros : TVA non applicable en vertu de l'article 293B du CGI.</p>
		</div>
	</section>
</body>
</html>