<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
	  xmlns:social="http://spring.io/springsocial"
	  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
	  layout:decorator="layout">
	<head>
		<title>Spring Social Showcase</title>
	</head>
	<body>
		<div id="header">
			<h1><a th:href="@{/}">Spring Social Showcase</a></h1>
		</div>
		
		<div id="content" layout:fragment="content">
			<h3>Connected to Facebook</h3>
			
			<form id="disconnect" method="post">
				<input type="hidden" name="_csrf" th:value="${_csrf.token}" />
				<div class="formInfo">
					<p>
						Spring Social Showcase is connected to your Facebook account.
						Click the button if you wish to disconnect.
					</p>		
				</div>
				<button type="submit">Disconnect</button>	
				<input type="hidden" name="_method" value="delete" />
			</form>
			
			<a th:href="@{/facebook}">View your Facebook profile</a>
		</div>		
	</body>
</html>