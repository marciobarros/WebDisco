<%@include file="../../helper/header.jsp"%>

<div class="container">
	<h3>Connect to Facebook</h3>
	
	<form action="/connect/facebook" method="POST">
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<input type="hidden" name="scope" value="user_posts,user_photos" />
		<div class="formInfo">
			<p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
		</div>
		<p><button type="submit"><img src="static/social/facebook/connect.gif"/></button></p>
	</form>
</div>

<%@include file="../../helper/footer.jsp"%>
