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
				<a class="nav-link" href="/member/planning">Planning</a>
				<a class="nav-link" href="/member/bookings">Mes réservations</a>
				<a class="nav-link" href="/aircraft/F-GCNS">Mes factures</a>
			</sec:authorize>
			<sec:authorize access="hasRole('BENEVOLE')">
				<a class="nav-link" href="/aircraft/F-GCNS">Finances</a>
				<div class="col-3">
					<select class="form-select" name="member" id="member-select">
					    <option value="">Choisir un membre</option>
					    <option value="id1">Pilot1</option>
					    <option value="id2">Pilot2</option>
					    <option value="id3">Pilot3</option>
					    <option value="id4">Pilot4</option>
					    <option value="id5">Pilot5</option>
					    <option value="id6">Pilot6</option>
					</select>
				</div>
			</sec:authorize>
		</div>
	</div>
</nav>