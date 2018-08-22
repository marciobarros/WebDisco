<%@include file="../../helper/header.jsp"%>

<div class="container">
	<h3>Connect to Facebook</h3>
	
	<div class="formInfo">
		<p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
	</div>

	<form action="connect/facebook" method="POST">
		<input type="hidden" name="scope" value="public_profile" />
		<p><button type="submit"><img src="static/img/social/facebook/signin.png"/></button></p>
	</form>
</div>

<%@include file="../../helper/footer.jsp"%>
