<%@include file="../helper/header.jsp"%>

<div class="container">
	<br/>
	<br/>
	
	<div class="row">
		<div class="col s3 m3 l3"></div>
	
		<div class="col s6 m6 l6">
	        <form:form action="login/change" commandName="form" role="form" method="post" enctype="utf8">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				
				<div class="card">
					<div class="card-content">
						<span class="card-title">Esqueci minha senha</span>
						<p>Entre seu e-mail abaixo e você receberá um link para troca de senha.</p>
						<br />
		
						<div class="input-field">
				           	<form:password id="form-senhaAtual" path="senhaAtual"/>
                         	<form:errors id="error-senhaAtual" path="senhaAtual" cssClass="error-block"/>				            
				            <label for="form-senhaAtual"><spring:message code="login.change.password.label.current.password"/>:</label>
						</div>
		
						<div class="input-field">
				           	<form:password id="form-senhaNova" path="senhaNova"/>
                           	<form:errors id="error-senhaNova" path="senhaNova" cssClass="error-block"/>				            
				            <label for="form-senhaNova"><spring:message code="login.change.password.label.new.password"/>:</label>
						</div>
		
						<div class="input-field">
				           	<form:password id="form-senhaNovaRepetida" path="senhaNovaRepetida"/>
                           	<form:errors id="error-senhaNovaRepetida" path="senhaNovaRepetida" cssClass="error-block"/>				            
				            <label for="form-senhaNovaRepetida"><spring:message code="login.change.password.label.new.password.verification"/>:</label>
						</div>
					</div>
		
					<div class="card-action">
						<button type="submit" class="btn">Envia</button> 
						<a href="homepage" class="right small-text-button">Retorna a homepage</a>
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
