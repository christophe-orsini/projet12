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
		<div class="col-6">
			<p class="h3">Clôture le réservation numéro ${closedBooking.id}</p>
			<div class="col-12">
				<c:url value="/member/close" var="action"/>
				<form:form action="${action}" method="post" class="form-row form-inline" modelAttribute="closedBooking"> 
					<form:input type="hidden" path="id" />
				  			
					<form:label path="description" class="col">Description :</form:label>
					<form:input type="text" class="form-control form-control-sm col-7 col-md-3"
						path="description" required="required" autofocus="autofocus" />
					
					<form:label path="departureTime" class="col">Départ :</form:label>
						<div class="col-12 row"> 
							<form:input type="date" class="form-control form-control-sm col-6" 
								path="departureDate" required="required"  />
							<form:input type="time" class="form-control form-control-sm col-6" 
								path="departureTime" required="required"  />
						</div>
															
						<form:label path="arrivalTime" class="col">Retour</form:label>
						<div class="col-12 row"> 
							<form:input type="date" class="form-control form-control-sm col-6"
								path="arrivalDate" required="required" />
							<form:input type="time" class="form-control form-control-sm col-6"
								path="arrivalTime" required="required" />	
						</div>		
													
					<input class="btn btn-primary offset-4 mt-3" type="submit" value="Clôturer">						
					<input class="btn btn-primary offset-4 mt-3" type="reset" value="Annuler">						
				</form:form>
			</div>
		</div>
	</section>
<%@ include file="../theme/footer.jsp" %>
</body>
</html>