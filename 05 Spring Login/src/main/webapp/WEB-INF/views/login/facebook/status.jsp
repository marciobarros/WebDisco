<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="content">
	<h3>Your Connections</h3>

	<div class="accountConnection" th:each="providerId : ${providerIds}">
		<h4><img src="@{#{${providerId} + '.icon'}}" width="36" height="36" /><span th:text="#{${providerId} + '.displayName'}">provider name</span></h4>
		<p>
			<span if="${!#lists.isEmpty(connectionMap[__${providerId}__])}">
			You are connected to <span th:text="#{${providerId} + '.displayName'}">provider name</span> as 
			<span text="${connectionMap[__${providerId}__][0].displayName}">user display name</span>.
		</span>
		
		<span if="${#lists.isEmpty(connectionMap[__${providerId}__])}">
			You are not yet connected to <span text="#{${providerId} + '.displayName'}">provider name</span>.
		</span>
		
		Click <a href="@{'/connect/' + ${providerId}}">here</a> to manage your <span text="#{${providerId} + '.displayName'}">provider name</span> connection.
		</p>
	</div>
</div>