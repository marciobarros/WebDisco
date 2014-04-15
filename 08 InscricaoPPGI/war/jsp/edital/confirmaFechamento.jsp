<%@include file="/jsp/helpers/template.jsp"%>
<mvc:redirectUserLevel level="ADM" page="/login/login.do"/>

<div id="content">
	<h3>Sistema de Inscrição On-Line</h3>
	
	<p>Você está pedindo para fechar o Edital aberto em <fmt:formatDate value="${requestScope.edital.dataAbertura}" pattern="dd/MM/yyyy"/>.
	Clique <b><a href="/edital/fecha.do">aqui</a></b> para confirmar o fechamento do Edital.</p>
</div>
