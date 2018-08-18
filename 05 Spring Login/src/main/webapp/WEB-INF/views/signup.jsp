<%@include file="helper/header.jsp"%>

<div class="container">
	<div class="jumbotron">
		<h1>Sign Up</h1>

		<div th:if="${!#strings.isEmpty(message)}" th:class="${message.type.cssClass}" th:text="${message.text}">
			error message text
		</div>

		<form id="signup" action="/signup" method="post">
			<div class="formInfo">
				<div class="error" th:if="${#fields.hasErrors('*')}">Unable to sign up. Please fix the errors below and resubmit.</div>
			</div>

			<fieldset>
				<label for="firstName">First Name <span
					th:if="${#fields.hasErrors('firstName')}" class="error"
					th:text="${#fields.errors('firstName')[0]}">Error</span></label> <input
					type="text" th:field="*{firstName}" /><br /> <label for="lastName">Last
					Name <span th:if="${#fields.hasErrors('lastName')}" class="error"
					th:text="${#fields.errors('lastName')[0]}">Error</span>
				</label> <input type="text" th:field="*{lastName}" /><br /> <label
					for="username">Username <span
					th:if="${#fields.hasErrors('username')}" class="error"
					th:text="${#fields.errors('username')[0]}">Error</span></label> <input
					type="text" th:field="*{username}" /><br /> <label for="password">Password<br />
				<span th:if="${#fields.hasErrors('password')}" class="error"
					th:text="${#fields.errors('password')[0]}">Error</span></label> <input
					type="password" th:field="*{password}" /> <small>(at least
					6 characters)</small>
			</fieldset>
			<br />
			<button type="submit" class="btn btn-form">Sign Up</button>
		</form>

	</div>
</div>

<%@include file="helper/footer.jsp"%>
