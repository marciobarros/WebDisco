<%@ page import="java.util.Date" %>

<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="content-language" content="en-us">
	<title>PPGI - Programa de Pós-Graduação em Informática - UNIRIO</title>
	<style>
		body { font-family: verdana; font-size: 8 pt }
		h1 { font-family: verdana; font-size: 12pt; color: black; text-align: center; font-weight: bold; margin: 0px; }
		h2 { font-family: verdana; font-size: 11pt; color: black; text-align: center; font-weight: normal; margin: 0px; }
		p.header2 { font-family: verdana; font-size: 10pt; color: black; text-align: center; text-height: 11 pt; margin: 0px; font-weight: bold; text-decoration: underline; }
		p.section { margin: 24px 0px; font-weight: bold; }
		p.declaracao { margin-top: 24px; font-size: 12px; margin-bottom: 24px; }
		p.dataExtenso { text-align: right; font-size: 10pt; margin-bottom: 48px; }
		p.assinaturaEspaco { text-align: right; margin-bottom: 0px; }
		p.assinaturaNome { text-align: right; margin-top: 0px; margin-right: 150px; font-size: 10px; }
		table.dadosLinha { width: 100%; border: 1px solid black; border-spacing: 0px; }
		table.dadosLinha tr td { border: 1px solid black; padding: 2px 4px; margin: 0px; }
		table.dadosUsuario { width: 100%; border: 1px solid black; border-spacing: 0px; }
		table.dadosUsuario tr td { border: 1px solid black; padding: 2px 4px; margin: 0px; }
		table.dadosOcupacao { width: 100%; border: 1px solid black; border-spacing: 0px; }
		table.dadosOcupacao tr td { border: 1px solid black; padding: 2px 4px; margin: 0px; }
		table.dadosTopico { width: 100%; border: 1px solid black; border-spacing: 0px; margin-bottom: 16px; }
		table.dadosTopico tr td { border: 1px solid black; padding: 2px 4px; margin: 0px; }
		td.dark { background-color: #ddd; font-weight: bold }
	</style>
</head>

<c:set var="inscricao" value="${requestScope.inscricao}" scope="page" />
<c:set var="usuario" value="${requestScope.usuario}" scope="page" />

<body>
	<h1>UNIVERSIDADE FEDERAL DO ESTADO DO RIO DE JANEIRO</h1>
	<h2>Centro de Ciências Exatas e Tecnologia - CCET</h2>
	<h2>Programa de Pós-Graduação em Informática - PPGI - Mestrado</h2>
	<br><br>
	
	<p class="header2">FICHA DE INSCRIÇÃO</p>
	<br><br>
	
	<p class="section">1. Dados da Inscrição</p>
	
	<table class="dadosLinha">
	<tbody>
	<tr>
		<td width="180" class="dark">Linha Principal</td>
		<td><c:out value="${inscricao.linhaPesquisaPrincipal.nome}"/></td>
	</tr>
	<tr>
		<td class="dark">Linha Secundária</td>
		<td><c:out value="${inscricao.linhaPesquisaSecundaria.nome}"/></td>
	</tr>
	<c:if test="${not empty inscricao.observacoes}">
	<tr>
		<td class="dark">Observações</td>
		<td><c:out value="${inscricao.observacoes}"/></td>
	</tr>
	</c:if>
	</tbody>
	</table>

	<p class="section">2. Dados Pessoais</p>
	
	<table class="dadosUsuario">
	<tbody>
	<tr>
		<td width="180" class="dark">Nome</td>
		<td colspan="3"><c:out value="${usuario.nome}"/></td>
	</tr>
	<tr>
		<td width="180" class="dark">Nacionalidade</td>
		<td><c:out value="${usuario.nacionalidade}"/></td>
		<td width="120" class="dark">Sexo</td>
		<td><c:out value="${usuario.sexo.nome}"/></td>
	</tr>
	<tr>
		<td class="dark">Endereço</td>
		<td colspan="3"><c:out value="${usuario.endereco}"/></td>
	</tr>
	<tr>
		<td class="dark">Município</td>
		<td><c:out value="${usuario.municipio}"/></td>
		<td class="dark">Estado</td>
		<td><c:out value="${usuario.estado}"/></td>
	</tr>
	<tr>
		<td class="dark">CEP</td>
		<td colspan="3"><c:out value="${usuario.cep}"/></td>
	</tr>
	<tr>
		<td class="dark">Tel Fixo</td>
		<td><c:out value="${usuario.telefoneFixo}"/></td>
		<td class="dark">Tel Celular</td>
		<td><c:out value="${usuario.telefoneCelular}"/></td>
	</tr>
	<tr>
		<td class="dark">Data de Nascimento</td>
		<td colspan="3"><fmt:formatDate value="${usuario.dataNascimento}" pattern="dd/MM/yyyy"/></td>
	</tr>
	<tr>
		<td class="dark">Identidade</td>
		<td><c:out value="${usuario.identidade}"/>, <c:out value="${usuario.emissorIdentidade}"/></td>
		<td class="dark">CPF</td>
		<td><c:out value="${usuario.cpf}"/></td>
	</tr>
	<tr>
		<td class="dark">Título de Eleitor</td>
		<td><c:out value="${usuario.tituloEleitor}"/></td>
		<td class="dark"># POSCOMP</td>
		<td><c:out value="${usuario.inscricaoPoscomp}"/></td>
	</tr>
	<tr>
		<td class="dark">Graduação</td>
		<td colspan="3"><c:out value="${usuario.nomeCurso}"/></td>
	</tr>
	<tr>
		<td class="dark">Universidade</td>
		<td colspan="3"><c:out value="${usuario.universidade.nome}"/></td>
	</tr>
	<tr>
		<td class="dark">E-Mail</td>
		<td colspan="3"><c:out value="${usuario.email}"/></td>
	</tr>
	
	<c:if test="${not empty usuario.observacoes}">
	<tr>
		<td class="dark" valign="top">Observações</td>
		<td colspan="3"><c:out value="${usuario.observacoes}"/></td>
	</tr>
	</c:if>
	</tbody>
	</table>
	
	<p class="section">3. Dados Profissionais: Ocupação atual</p>
	
	<table class="dadosOcupacao">
	<tbody>
	<tr>
		<td class="dark">Instituição</td>
		<td><c:out value="${usuario.instituicao}"/></td>
	</tr>
	<tr>
		<td class="dark">Cargo</td>
		<td><c:out value="${usuario.cargo}"/></td>
	</tr>
	<tr>
		<td width="180" class="dark">Tempo na Empresa</td>
		<td><c:out value="${usuario.tempoEmpresa}"/> meses</td>
	</tr>
	</tbody>
	</table>
	
	<c:if test="${not empty inscricao.topicos}">
	<p class="section">4. Tópicos de Pesquisa</p>
	</c:if>
	
	<c:forEach var="topico" items="${inscricao.topicos}">
		<table class="dadosTopico">
		<tr>
			<td class="dark"><c:out value="${topico.topico.nome}"/></td>
		</tr>
		<tr>
			<td><c:out value="${topico.descricao}"/></td>
		</tr>
		</table>
	</c:forEach>
	
	<p class="declaracao">Declaro serem verdadeiras as informações acima prestadas e aceito todos os critérios da Comissão de Seleção.</p>
	<p class="dataExtenso">Rio de Janeiro, <fmt:formatDate value="<%=new Date()%>" dateStyle="long"/></p>
	<p class="assinaturaEspaco">___________________________________________</p>
	<p class="assinaturaNome">Assinatura do Candidato</p>
</body>
</html>