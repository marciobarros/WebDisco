<%@include file="helper/header.jsp"%>

<div class="container">
	<div class="jumbotron">
		<h1>Sign In</h1>

		<form id="signin" th:action="/signin/authenticate" method="post">
			<!-- input type="hidden" name="_csrf" th:value="${_csrf.token}"></input -->
			<div class="formInfo">
				<div class="error" th:if="${param.error eq 'bad_credentials'}">
					Your sign in information was incorrect. Please try again or <a href="/signup">sign up</a>.
				</div>
			
				<div class="error">
					Multiple local accounts are connected to the provider account. Try
					again with a different provider or with your username and password.
				</div>
			</div>
			
			<fieldset class="loginForm">
				<label for="login">Username</label> 
				<input id="login" name="username" type="text" size="25"></input><br /> 
				<label for="password">Password</label> 
				<input id="password" name="password" type="password" size="25"></input> <br />
				<br />
				<button type="submit" class="btn btn-form">Sign In</button>
			</fieldset>
			<br />

			<p>Some test user/password pairs you may use are:</p>
			<ul>
				<li>habuma/tacos</li>
				<li>rclarkson/atlanta</li>
			</ul>

			<p>
				Or you can <a href="/signup">signup</a> with a new account.
			</p>
		</form>

		<!-- TWITTER SIGNIN -->
		<!-- form id="tw_signin" th:action="@{/signin/twitter}" method="POST">
			<input type="hidden" name="_csrf" th:value="${_csrf.token}"></input>
			<button type="submit">
				<img th:src="@{/social/twitter/sign-in-with-twitter-d.png}"></img>
			</button>
		</form -->

		<!-- FACEBOOK SIGNIN -->
		<form name="fb_signin" id="fb_signin" action="signin/facebook" method="POST">
			<input type="hidden" name="scope" value="read_stream,user_posts"></input>
			<button type="submit">
				<img src="static/img/social/facebook/signin.png"></img>
			</button>
		</form>
	</div>
</div>

<%@include file="helper/footer.jsp"%>
