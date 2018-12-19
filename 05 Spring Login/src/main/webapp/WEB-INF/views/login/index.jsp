<%@include file="../helper/header.jsp"%>

<div class="container">
	<sec:authorize access="isAnonymous()">
		<div class="row login-box">
			<div class="col s3 m3 l3"></div>
		
			<div class="col s6 m6 l6">
		        <form id="loginForm" method="post" action="login/authenticate">
					<div class="card">
						<div class="card-content">
							<span class="card-title">
								Login com sua conta no sistema
							</span>
			
							<br/>
			
							<div class="input-field">
								<input type="text" id="user-email" name="username">
								<label for="user-email">Email:</label>
							</div>
			
							<div class="input-field">
								<input type="password" id="user-password" name="password"> 
								<label for="user-password">Senha:</label>
							</div>
						</div>
						
				        <div class="card-action">
							<button type="submit" class="btn" >
								Envia
							</button>
							<a href="login/forgot" class="right small-text-button">
								Esqueci minha senha
							</a>
							<a href="login/create" class="right small-text-button">
								Cria nova conta
							</a> 
						</div>
					</div>
		        </form>
			</div>
		
			<div class="col s3 m3 l3"></div>
		</div>
	</sec:authorize>
		
	<sec:authorize access="isAuthenticated()">
		<div class="row authenticated-box">
			<div class="col s12 m12 l12">
				<h3><span>${user.nome}</span>, você já está autenticado.</h3>
				<h5>Clique <a href="homepage">aqui</a> para a homepage.</h5>
			</div>
		</div>
	</sec:authorize>
</div>

<%@include file="../helper/footer.jsp"%>
