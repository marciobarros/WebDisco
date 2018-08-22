<%@include file="helper/header.jsp"%>

<div class="container">
	<div class="jumbotron">
		<h1>Sign Up</h1>

		<form action="connect/facebook" method="POST">
			<input type="hidden" name="scope" value="public_profile"></input>
			<button type="submit"><img src="static/img/social/facebook/signin.png"></img></button>
		</form>
	</div>
</div>

<%@include file="helper/footer.jsp"%>
