<%@page session="false"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Weather Report</title>

<c:url var="home" value="/" scope="request" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script>
    jQuery(document).ready(function($) {
    	$("#error").hide();
        $("#errorData").html("");
        $("#result_container").hide();
        $("#weather").submit(function(event) {
            event.preventDefault();
            var city = $("#city").val().trim();
            var validForm = true;
            if (city.length == 0 || (city.length == 1 && ',' == city.charAt(0))) {
                validForm = false;
            }
            if (!validForm) {
                $("#errorData").html("Enter city name");
                $("#result_container").hide();
                $("#error").show();
                return;
            }
            $.ajax({
                   type : "GET",
                   contentType : "application/json",
                   url : "${home}search?city=" + city,
                   dataType : 'json',
                   success : function(data) {
                       $("#result_container").empty();
                       $.each(data, function(i, weather) {
                           if(weather.error) {
                               $("#result_container").append("<ul class='list-group'>");
                               $("#result_container").append("<li class='list-group-item active'>" + weather.error + "</li>");
                               $("#result_container").append("</ul>");
                           } else {
                               $("#result_container").append("<ul class='list-group'>");
                               $("#result_container").append("<li class='list-group-item active'>Weather for " + weather.name + "</li>");
                               $("#result_container").append("<li class='list-group-item'>Description : " + weather.description + "</li>");
                               $("#result_container").append("<li class='list-group-item'>Temperature : " + weather.temperature + "</li>");
                               $("#result_container").append("<li class='list-group-item'>Humidity : " + weather.humidity + "</li>");
                               $("#result_container").append("<li class='list-group-item'>Max Temperature : " + weather.maximumTemperature + "</li>");
                               $("#result_container").append("<li class='list-group-item'>Min Temperature : " + weather.minimumTemperature + "</li>");
                               $("#result_container").append("</ul>");
                           }                           
                       });
                       
                       $("#errorData").html("");
                       $("#result_container").show();
                       $("#error").hide();
                   },
                   error : function(e) {
                       var json = e.responseJSON;
                       var reason = json.reason;
                       $("#errorData").html(reason);
                       $("#result_container").hide();
                       $("#error").show();
                   },
                   done : function(e) {
                       $("#btn-submit").prop("disabled", true);
                   }
               });
        });
    });
</script>
</head>

<body>
<div class="container" style="min-height: 500px">
	<div class="starter-template">
		<br>
		<form class="form-horizontal" id="weather">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">
				    <spring:message code="weather.city" />
				</label>
				<div class="col-sm-5">
					<input type=text id="city" class="form-control" pattern="^[a-zA-Z, ]*$">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-md-10">
					<button type="submit" id="btn-submit" class="btn btn-success btn-sm">
						<spring:message code="weather.submit" />
					</button>
				</div>
			</div>
		</form>

	</div>
	<div id="error">
		<div id="errorData" class="alert alert-danger"></div>
	</div>
    <div id="result_container" class="container">
	</div>
</div>
</body>
</html>