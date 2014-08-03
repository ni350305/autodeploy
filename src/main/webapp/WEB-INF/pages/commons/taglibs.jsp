<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="base" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<c:set var="jqueryBase"value="${base}/js/jquery-1.7.1.js"/>
<c:set var="jsVersion" value="default"></c:set>
<c:set var="cssVersion" value="default"></c:set>
<c:set var="jsBase"value="${base}/js/${jsVersion}"></c:set>
<c:set var="cssBase"value="${base}/css/${cssVersion}"></c:set>
<c:set var="imageBase"value="${base}/images/${cssVersion}"></c:set>