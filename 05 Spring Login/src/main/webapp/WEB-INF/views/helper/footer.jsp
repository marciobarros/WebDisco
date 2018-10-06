  <footer class="blue">
     <div>
     	  <p class="major">UNIRIO - Universidade Federal do Estado Rio de Janeiro</p>
     	  <p class="minor">
		      Developed with 
		      <a class="blue-text text-lighten-4" href="http://materializecss.com">Materialize</a>,
		      <a class="blue-text text-lighten-4" href="https://angularjs.org">AngularJS</a>,
		      <a class="blue-text text-lighten-4" href="https://spring.io/guides/gs/serving-web-content">Spring</a> and
		      <a class="blue-text text-lighten-4" href="https://www.mysql.com">MySQL</a>.
	      </p>
     </div>
  </footer>
</body>

<c:if test="${not empty param.message}">
	<spring:message var="paramMessage" code="${param.message}"/>
	<script>
	window.onload = function () {
		M.toast({html: "${paramMessage}"});
    };
	</script>
</c:if>

</html>