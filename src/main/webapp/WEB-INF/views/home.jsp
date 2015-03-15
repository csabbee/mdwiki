<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="header.jsp"/>
<body>
<h1>
	Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>

<div ng-app="mdwiki" class="row">
    <div ui-view="navigationPane" class="navigation"></div>
	<div ui-view="content" class="content"></div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
