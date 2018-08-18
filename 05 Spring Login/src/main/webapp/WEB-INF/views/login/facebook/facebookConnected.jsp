<%@include file="../../helper/header.jsp"%>

<div class="container">
	<h3>Connected to Facebook</h3>
	
	<form id="disconnect" method="post">
		<div class="formInfo">
			<p>
				Spring Social Showcase is connected to your Facebook account.
				Click the button if you wish to disconnect.
			</p>		
		</div>
		<button type="submit">Disconnect</button>	
		<input type="hidden" name="_method" value="delete" />
	</form>
	
	<a href="/facebook">View your Facebook profile</a>
</div>		

<%@include file="../../helper/footer.jsp"%>
