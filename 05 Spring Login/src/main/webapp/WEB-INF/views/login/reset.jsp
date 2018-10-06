<%@include file="../helper/header.jsp"%>

<div class="container">
	<br/>
	<br/>
	
	<div class="row">
		<div class="col s3 m3 l3"></div>
	
		<div class="col s6 m6 l6">
	        <form:form action="login/reset" commandName="form" role="form" method="post" enctype="utf8">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <form:input type="hidden" path="email"/>
                <form:input type="hidden" path="token"/>
				
				<div class="card">
					<div class="card-content">
						<span class="card-title">Esqueci minha senha</span>
						<p>Entre seu e-mail abaixo e você receberá um link para troca de senha.</p>
						<br />
		
						<div class="input-field">
				           	<form:password id="form-senha" path="senha"/>
                           	<form:errors id="error-senha" path="senha" cssClass="error-block"/>				            
				            <label for="form-senha"><spring:message code="login.reset.password.label.new.password"/>:</label>
						</div>
		
						<div class="input-field">
				           	<form:password id="form-senhaRepetida" path="senhaRepetida"/>
                           	<form:errors id="error-senhaRepetida" path="senhaRepetida" cssClass="error-block"/>				            
				            <label for="form-senhaRepetida"><spring:message code="login.reset.password.label.new.password.verification"/>:</label>
						</div>
					</div>
		
					<div class="card-action">
						<button type="submit" class="btn">Envia</button> 
						<a href="login" class="right small-text-button">Retorna ao login</a>
					</div>
				</div>
			</form:form>
		</div>
	
		<div class="col s3 m3 l3"></div>
	</div>

	<br/>
	<br/>
</div>

<%@include file="../helper/footer.jsp"%>
