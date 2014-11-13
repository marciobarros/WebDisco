package br.unirio.crud.actions;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.unirio.crud.config.Configuracao;
import br.unirio.crud.dao.DAOFactory;
import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ResultType;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.utils.Crypto;

/**
 * Classe com a��es de login e tratamento de usu�rios
 * 
 * @author Marcio Barros
 */
public class ActionLogin extends Action
{
	/**
	 * Per�odo de validade do token de troca de senha
	 */
	private static final int VALIDATE_TOKEN_SENHA = 72;
	
	/**
	 * A��o de login
	 */
	@DisableUserVerification
	@Error("/jsp/login/login.jsp")
	@Success("/login/homepage.do")
	public String login() throws ActionException
	{
		if (testLogged() != null)
			return SUCCESS;

		String email = getParameter("email");
		String password = getParameter("pwd");

		if (email == null || password == null)
			return ERROR;

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "Usu�rio e senha incorretos.");
		check(usuario.isActive(), "O usu�rio est� desativado no sistema.");
		check(!usuario.isDeveTrocarSenha(), "O sistema indica que voc� precisa fazer um reset de senha. Isto pode ter ocorrido porque voc� pediu o reset e ainda n�o recebemos a confirma��o ou porque houve tr�s tentativas mal-sucedidas de acesso � sua conta. Se necess�rio, clique na op��o \"Esqueceu sua senha?\" para enviarmos novamente o e-mail de reset de senha.");

		String hashSenha = Configuracao.getAmbienteHomologacao() ? password : Crypto.hash(password);

		if (usuario.getSenhaCodificada().compareTo(hashSenha) != 0)
		{
			DAOFactory.getUsuarioDAO().registraLoginFalha(usuario.getId());
			return addError("Usu�rio e senha incorretos.");
		}

		DateTime dataUltimoLogin = DAOFactory.getUsuarioDAO().pegaDataUltimoLogin(usuario.getId());

		if (dataUltimoLogin != null)
		{
			DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
			addNotice("Seu �ltimo login no sistema foi realizado em " + sdf.print(dataUltimoLogin) + " hr.");
		}

		DAOFactory.getUsuarioDAO().registraLoginSucesso(usuario.getId());
		setCurrentUser(usuario);
		return SUCCESS;
	}

	/**
	 * A��o de ida para a homepage
	 */
	@Error("/login/login.do")
	@Success("/jsp/homepage.jsp")
	public String homepage() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * A��o de logout
	 */
	@DisableUserVerification
	@Any(type=ResultType.Redirect, value="/login/login.do")
	public String logout()
	{
		invalidateCurrentUser();
		return SUCCESS;
	}

	/**
	 * A��o para preparar para a troca de senha
	 */
	@Error("/login/login.do")
	@Success("/jsp/login/trocaSenha.jsp")
	public String preparaTrocaSenha() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * A��o de troca de senha
	 */
	@Success(type=ResultType.Redirect, value="/login/homepage.do")
	@Error("/jsp/login/trocaSenha.jsp")
	public String trocaSenha() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();

		String senhaAtual = getParameter("oldpassword");
		check(senhaAtual != null, "Entre com sua senha antiga.");

		String hashSenha = Configuracao.getAmbienteHomologacao() ? senhaAtual : Crypto.hash(senhaAtual);
		check(usuario.getSenhaCodificada().compareTo(hashSenha) == 0, "A senha antiga est� incorreta.");

		String novaSenha = getParameter("newpassword");
		check(novaSenha != null, "Entre com sua nova senha.");
		check(senhaAceitavel(novaSenha), "A nova senha deve conter pelo menos 8 caracteres, uma letra e um n�mero.");

		String novaSenha2 = getParameter("newpassword2");
		check(novaSenha2 != null, "Repita sua nova senha.");

		check(novaSenha.length() != 0, "A nova senha n�o pode ser em branco.");
		check(novaSenha.compareTo(novaSenha2) == 0, "Sua senha nova e a repeti��o est�o diferentes.");

		String hashNovaSenha = Configuracao.getAmbienteHomologacao() ? novaSenha : Crypto.hash(novaSenha);
		usuario.setSenhaCodificada(hashNovaSenha);

		DAOFactory.getUsuarioDAO().atualizaSenha(usuario.getId(), hashNovaSenha);
		return addRedirectNotice("senha.trocada");
	}

	/**
	 * Verifica se uma senha � aceit�vel, checando se ela tem pelo menos 8 caracteres, uma letra e um n�mero
	 */
	private boolean senhaAceitavel(String senha)
	{
		return (senha.length() >= 8) && senha.matches(".*[a-zA-Z].*") && senha.matches(".*[0-9].*");
	}

	/**
	 * Prepara o formul�rio de envio de token por esquecimento de senha
	 */
	@DisableUserVerification
	@Error("/login/login.do")
	@Success("/jsp/login/enviaToken.jsp")
	public String preparaEnvioToken() throws ActionException
	{
		return SUCCESS;
	}

	/**
	 * A��o de envio de token por esquecimento de senha
	 */
	@DisableUserVerification
	@Error("/jsp/login/esqueceuSenha.jsp")
	@Success("/jsp/login/apresentaToken.jsp")
	public String envioToken() throws ActionException
	{
		String email = getParameter("email");
		check(email != null, "Entre com seu e-mail.");
		setAttribute("email", email);

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "Usuario n�o reconhecido.");

		String token = geraTokenTrocaSenha();
		DAOFactory.getUsuarioDAO().armazenaTokenTrocaSenha(usuario.getId(), token);

		String url = Configuracao.getHostname() + "/login/verificaTokenSenha.do?token=" + token + "&email=" + usuario.getEmail();
		setAttribute("url", url);
		return SUCCESS;
		
//		String corpo = "<p>Voc� est� recebendo este e-mail porque pediu a reinicializa\u00E7\u00E3o da senha de acesso ao ";
//		corpo += "sistema do PPGI. Clique <a href='" + url + "'>aqui</a> para acessar a p�gina de troca de senha.</p>";
//		boolean envioOK = GerenciadorEmail.getInstance().envia(usuario.getNome(), usuario.getEmail(), "Reinicializacao de senha de acesso ao sistema do PPGI", corpo);
//		check(envioOK, "Ocorreu um erro ao enviar um e-mail com sua senha");
//		return addRedirectNotice("pedido.reinicializacao.email");
	}

	/**
	 * Gera um token para troca de senha
	 */
	private String geraTokenTrocaSenha()
	{
		StringBuilder sb = new StringBuilder();
		Random r = Crypto.createSecureRandom();

		for (int i = 0; i < 256; i++)
		{
			char c = (char) ('A' + r.nextInt(26));
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * A��o que recebe e verifica a validade do token de troca de senha
	 */
	@DisableUserVerification
	@Error("/login/login.do")
	@Success("/jsp/login/resetSenha.jsp")
	public String verificaTokenSenha() throws ActionException
	{
		String token = getParameter("token", "");
		checkNonEmpty(token, "O token de reset de senha n�o foi encontrado.");

		String email = getParameter("email", "");
		checkNonEmpty(email, "O e-mail do usu�rio que requisitou o reset de senha n�o foi encontrado.");

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "O usu�rio que requisitou o reset de senha n�o est� registrado no sistema.");

		boolean valido = DAOFactory.getUsuarioDAO().verificaTokenTrocaSenha(usuario.getId(), token, VALIDATE_TOKEN_SENHA);
		check(valido, "O token de troca de senha n�o � v�lido");

		setAttribute("token", token);
		setAttribute("email", email);
		return SUCCESS;
	}
	
	/**
	 * Executa uma troca de senha baseada em reinicializa��o
	 */
	@DisableUserVerification
	@Success(type=ResultType.Redirect, value="/login/login.do")
	@Error("/jsp/login/resetSenha.jsp")
	public String executaResetSenha() throws ActionException
	{
		String token = getParameter("token", "");
		checkNonEmpty(token, "O token de reset de senha n�o foi encontrado.");

		String email = getParameter("email", "");
		checkNonEmpty(email, "O e-mail do usu�rio que requisitou o reset de senha n�o foi encontrado.");

		setAttribute("token", token);
		setAttribute("email", email);

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "O usu�rio que requisitou o reset de senha n�o est� registrado no sistema.");

		boolean valido = DAOFactory.getUsuarioDAO().verificaTokenTrocaSenha(usuario.getId(), token, VALIDATE_TOKEN_SENHA);
		check(valido, "O token de troca de senha n�o � v�lido");

		String novaSenha = getParameter("newpassword");
		check(novaSenha != null, "Entre com sua nova senha.");
		check(senhaAceitavel(novaSenha), "A nova senha deve conter pelo menos 8 caracteres, uma letra e um n�mero.");

		String novaSenha2 = getParameter("newpassword2");
		check(novaSenha2 != null, "Repita sua nova senha.");
		check(novaSenha.compareTo(novaSenha2) == 0, "Sua senha nova e a repeti��o est�o diferentes.");

		String hashNovaSenha = Configuracao.getAmbienteHomologacao() ? novaSenha : Crypto.hash(novaSenha);
		usuario.setSenhaCodificada(hashNovaSenha);

		DAOFactory.getUsuarioDAO().atualizaSenha(usuario.getId(), hashNovaSenha);
		return addRedirectNotice("senha.trocada");
	}

	/**
	 * A��o que cria um novo usu�rio
	 */
	@DisableUserVerification
	@Any("/jsp/login/novo.jsp")
	public String novo()
	{
		Usuario usuario = new Usuario();
		setAttribute("item", usuario);
		return SUCCESS;
	}

	/**
	 * A��o que salva os dados de um novo usu�rio
	 */
	@DisableUserVerification
	@Success(type=ResultType.Redirect, value="/login/login.do")
	@Error("/jsp/login/novo.jsp")
	public String salva() throws ActionException
	{
		// Captura os dados do formul�rio
		Usuario usuario = new Usuario();
		usuario.setNome(getParameter("nome", ""));
		usuario.setEmail(getParameter("email", usuario.getEmail()));
		usuario.setEndereco(getParameter("endereco", ""));
		usuario.setComplemento(getParameter("complemento", ""));
		usuario.setEstado(Estado.get(getParameter("estado", "")));
		usuario.setMunicipio(getParameter("municipio", ""));
		usuario.setCep(getParameter("cep", ""));
		usuario.setTelefoneFixo(getParameter("telefoneFixo", ""));
		usuario.setTelefoneCelular(getParameter("telefoneCelular", ""));
		setAttribute("item", usuario);
		
		// Captura a senha
		String senha1 = getParameter("password", "");
		String senha2 = getParameter("password2", "");

		// Salva a nova senha
		String hashNovaSenha = Configuracao.getAmbienteHomologacao() ? senha1 : Crypto.hash(senha1);
		usuario.setSenhaCodificada(hashNovaSenha);

		// Verifica as regras de neg�cio
		checkNonEmpty(usuario.getNome(), "O nome do usu�rio n�o pode ser vazio");
		checkLength(usuario.getNome(), 80, "O nome do usu�rio");

		checkNonEmpty(usuario.getEmail(), "O e-mail do usu�rio n�o pode ser vazio");
		checkLength(usuario.getEmail(), 80, "O e-mail do usu�rio");
		checkEmail(usuario.getEmail(), "O e-mail do usu�rio n�o est� seguindo um formato v�lido");
		
		check(senhaAceitavel(senha1), "A senha selecionada � muito fraca. Entre com uma senha mais forte.");
		check(senha1.compareToIgnoreCase(senha2) == 0, "A repeti��o da senha est� diferente da senha original.");

		checkNonEmpty(usuario.getEndereco(), "O endere�o n�o pode ser vazio");
		checkLength(usuario.getEndereco(), 80, "O endere�o");

		checkLength(usuario.getComplemento(), 80, "O complemento do endere�o");

		check(usuario.getEstado() != null, "Selecione o estado do usu�rio");

		checkNonEmpty(usuario.getMunicipio(), "Selecione o munic�pio do usu�rio");
		checkLength(usuario.getMunicipio(), 80, "O munic�pio do usu�rio");

		checkNonEmpty(usuario.getCep(), "O CEP do usu�rio n�o pode ser vazio");
		checkLength(usuario.getCep(), 10, "O CEP do usu�rio");

		checkNonEmpty(usuario.getTelefoneFixo(), "O telefone fixo do usu�rio n�o pode ser vazio");
		checkLength(usuario.getTelefoneFixo(), 20, "O telefone fixo do usu�rio");
		checkPhone(usuario.getTelefoneFixo(), "O telefone fixo do usu�rio est� com um formato inv�lido");

		checkNonEmpty(usuario.getTelefoneCelular(), "O telefone celular do usu�rio n�o pode ser vazio");
		checkLength(usuario.getTelefoneCelular(), 20, "O telefone celular do usu�rio");
		checkPhone(usuario.getTelefoneCelular(), "O telefone celular do usu�rio est� com um formato inv�lido");

		Usuario usuario2 = DAOFactory.getUsuarioDAO().getUsuarioEmail(usuario.getEmail());
		check(usuario2 == null || usuario2.getId() == usuario.getId(), "J� existe um usu�rio com esse e-mail");

		DAOFactory.getUsuarioDAO().adiciona(usuario);
		return addRedirectNotice("usuario.cadastrado.sucesso");
	}
}