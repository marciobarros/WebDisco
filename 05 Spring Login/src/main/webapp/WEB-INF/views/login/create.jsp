<%@include file="../helper/header.jsp"%>

<div class="container">
	<br/>
	<br/>
	
	<div class="row">
		<div class="col s3 m3 l3"></div>
	
		<div class="col s6 m6 l6">
	        <form:form action="login/create" commandName="form" role="form" method="post" enctype="utf8">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				
				<div class="card">
					<div class="card-content">
						<span class="card-title">Nova Conta</span>
						<p>Entre seus dados abaixo para criar uma nova conta.</p>
						<br />
		
				        <div class="input-field">
				            <label for="user-name">Nome:</label>
							<form:input id="nome-usuario" path="nome" size="64"/>
							<form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
				        </div>
				        
				        <div class="input-field">
				            <label for="user-email">E-mail:</label>
							<form:input id="email-usuario" path="email" size="64"/>
							<form:errors id="error-email" path="email" cssClass="error-block"/>				            
				        </div>
				        
				        <div class="input-field">
				            <label for="user-password">Senha:</label>
							<form:password id="senha-usuario" path="senha" size="64"/>
							<form:errors id="error-senha" path="senha" cssClass="error-block"/>				            
				        </div>
				        
				        <div class="input-field">
				            <label for="user-repeatPassword">Repita sua senha:</label>
							<form:password id="senhaRepetida-usuario" path="senhaRepetida" size="64"/>
							<form:errors id="error-senhaRepetida" path="senhaRepetida" cssClass="error-block"/>				            
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
