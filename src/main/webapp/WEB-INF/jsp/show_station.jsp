<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome to Oslo city bike</title>
    <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
    <link href="<c:url value="css/common.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<%
    response.setIntHeader("Refresh", 60*10); //in your case 60*5=300 (for 5 min)
%>
<button onclick=location=URL>Click to update</button>
<div id="map" style="height: 650px; width: 1250px;">
</div>
<script type="text/javascript">

    var locations = [
            <c:forEach items="${stations}" var="station">[ 'Station:'+
            '<c:out value="${station.name}" />' + ',' +
            ' Bikes:' + <c:out value="${station.num_bikes_available}" /> +',' +
            ' Docks:' + <c:out value="${station.num_docks_available}" />,
            <c:out value="${station.lat}" />,
            <c:out value="${station.lon}" />
        ],
        </c:forEach>
    ];
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 10,
        center: new google.maps.LatLng(59.9099822, 10.7914482),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;

    for (i = 0; i < locations.length; i++) {
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
            map: map
        });

        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infowindow.setContent(locations[i][0]);
                infowindow.open(map, marker);
            }
        })(marker, i));
    }
</script>
</body>
</html>