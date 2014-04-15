package br.unirio.inscricaoppgi.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import br.unirio.inscricaoppgi.config.Configuracao;
import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.inscricaoppgi.model.Sexo;
import br.unirio.inscricaoppgi.model.Universidade;
import br.unirio.inscricaoppgi.model.Usuario;
import br.unirio.inscricaoppgi.services.GerenciadorEmail;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.AnyRedirect;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.simplemvc.utils.Crypto;
import br.unirio.simplemvc.utils.Validators;

/**
 * Classe com ações de login e tratamento de usuários
 * 
 * @author Marcio Barros
 */
public class ActionLogin extends Action
{
	/**
	 * Período de validade do token de troca de senha
	 */
	private static final int VALIDATE_TOKEN_SENHA = 72;
	
	/**
	 * Ação de login
	 */
	@DisableUserVerification
	@Error("/jsp/homepage.jsp")
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

		if (usuario == null)
			return addError("Usuário e senha incorretos.");

		if (!usuario.isActive())
			return addError("O usuário está desativado no sistema.");

		if (usuario.isForcaResetSenha())
			return addError("O sistema indica que você precisa fazer um reset de senha. Isto pode ter ocorrido porque você pediu o reset e ainda não recebemos a confirmação ou porque houve três tentativas mal-sucedidas de acesso à sua conta. Se necessário, clique na opção \"Esqueceu sua senha?\" para enviarmos novamente o e-mail de reset de senha.");

		String hashSenha = Configuracao.getAmbienteHomologacao() ? password : Crypto.hash(password);

		if (usuario.getSenhaCodificada().compareTo(hashSenha) != 0)
		{
			DAOFactory.getUsuarioLoginDAO().registraLoginFalha(usuario.getId());
			return addError("Usuário e senha incorretos.");
		}

		Date dataUltimoLogin = DAOFactory.getUsuarioLoginDAO().pegaDataUltimoLogin(usuario.getId());

		if (dataUltimoLogin != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			addNotice("Seu último login no sistema foi realizado em " + sdf.format(dataUltimoLogin) + " hr.");
		}

		DAOFactory.getUsuarioLoginDAO().registraLoginSucesso(usuario.getId());
		setCurrentUser(usuario);
		return SUCCESS;
	}

	/**
	 * Ação de ida para a homepage e login
	 */
	@Error("/login/login.do")
	@Success("/jsp/homepage.jsp")
	public String homepage() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * Ação de logout
	 */
	@DisableUserVerification
	@AnyRedirect("/login/homepage.do")
	public String logout()
	{
		invalidateCurrentUser();
		return SUCCESS;
	}

	/**
	 * Ação para preparar para a troca de senha
	 */
	@DisableUserVerification
	@Error("/login/login.do")
	@Success("/jsp/login/trocaSenha.jsp")
	public String preparaTrocaSenha() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * Ação de troca de senha
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/homepage.do")
	@Error("/jsp/login/trocaSenha.jsp")
	public String trocaSenha() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();

		String senhaAtual = getParameter("oldpassword");
		check(senhaAtual != null, "Entre com sua senha antiga.");

		String hashSenha = Configuracao.getAmbienteHomologacao() ? senhaAtual : Crypto.hash(senhaAtual);
		check(usuario.getSenhaCodificada().compareTo(hashSenha) == 0, "A senha antiga está incorreta.");

		String novaSenha = getParameter("newpassword");
		check(novaSenha != null, "Entre com sua nova senha.");
		check(senhaAceitavel(novaSenha), "A nova senha deve conter pelo menos 8 caracteres, uma letra e um número.");

		String novaSenha2 = getParameter("newpassword2");
		check(novaSenha2 != null, "Repita sua nova senha.");

		check(novaSenha.length() != 0, "A nova senha não pode ser em branco.");
		check(novaSenha.compareTo(novaSenha2) == 0, "Sua senha nova e a repetição estão diferentes.");

		String hashNovaSenha = Configuracao.getAmbienteHomologacao() ? novaSenha : Crypto.hash(novaSenha);
		usuario.setSenhaCodificada(hashNovaSenha);
		usuario.setDeveTrocarSenha(false);
		usuario.setForcaResetSenha(false);
		
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("senha.trocada");
	}

	/**
	 * Verifica se uma senha é aceitável, checando se ela tem pelo menos 8
	 * caracteres, uma letra e um número
	 */
	private boolean senhaAceitavel(String senha)
	{
		return (senha.length() >= 8) && senha.matches(".*[a-zA-Z].*") && senha.matches(".*[0-9].*");
	}

	/**
	 * Ação de envio de email para reset de senha
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/homepage.do")
	@Error("/jsp/login/esqueceuSenha.jsp")
	public String enviaSenha() throws ActionException
	{
		String email = getParameter("email");
		check(email != null, "Entre com seu e-mail.");
		setAttribute("email", email);

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "Usuario não reconhecido.");

		String token = geraTokenTrocaSenha();
		DAOFactory.getUsuarioTokenSenhaDAO().armazenaTokenTrocaSenha(usuario.getId(), token);

		String url = Configuracao.getHostname() + "/login/preparaResetSenha.do?token=" + token + "&email=" + usuario.getEmail();
		String corpo = "<p>Você está recebendo este e-mail porque pediu a reinicializa\u00E7\u00E3o da senha de acesso ao ";
		corpo += "sistema do PPGI. Clique <a href='" + url + "'>aqui</a> para acessar a página de troca de senha.</p>";
		boolean envioOK = GerenciadorEmail.getInstance().envia(usuario.getNome(), usuario.getEmail(), "Reinicializacao de senha de acesso ao sistema do PPGI", corpo);
		check(envioOK, "Ocorreu um erro ao enviar um e-mail com sua senha");

		return addRedirectNotice("pedido.reinicializacao.email");
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
	 * Prepara o formulário de reset de senha
	 */
	@DisableUserVerification
	@Success("/jsp/login/resetSenha.jsp")
	@Error("/jsp/login/esqueceuSenhaForm.jsp")
	public String preparaResetSenha() throws ActionException
	{
		String token = getParameter("token", "");
		checkNonEmpty(token, "O token de reset de senha não foi encontrado.");

		String email = getParameter("email", "");
		checkNonEmpty(email, "O e-mail do usuário que requisitou o reset de senha não foi encontrado.");

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "O usuário que requisitou o reset de senha não está registrado no sistema.");

		boolean valido = DAOFactory.getUsuarioTokenSenhaDAO().verificaTokenTrocaSenha(usuario.getId(), token, VALIDATE_TOKEN_SENHA);
		check(valido, "O token de troca de senha não é válido");

		setAttribute("token", token);
		setAttribute("email", email);
		return SUCCESS;
	}

	/**
	 * Executa uma troca de senha baseada em reinicialização
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/homepage.do")
	@Error("/jsp/login/resetSenha.jsp")
	public String executaResetSenha() throws ActionException
	{
		String token = getParameter("token", "");
		checkNonEmpty(token, "O token de reset de senha não foi encontrado.");

		String email = getParameter("email", "");
		checkNonEmpty(email, "O e-mail do usuário que requisitou o reset de senha não foi encontrado.");

		setAttribute("token", token);
		setAttribute("email", email);

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "O usuário que requisitou o reset de senha não está registrado no sistema.");

		boolean valido = DAOFactory.getUsuarioTokenSenhaDAO().verificaTokenTrocaSenha(usuario.getId(), token, VALIDATE_TOKEN_SENHA);
		check(valido, "O token de troca de senha não é válido");

		String novaSenha = getParameter("newpassword");
		check(novaSenha != null, "Entre com sua nova senha.");
		check(senhaAceitavel(novaSenha), "A nova senha deve conter pelo menos 8 caracteres, uma letra e um número.");

		String novaSenha2 = getParameter("newpassword2");
		check(novaSenha2 != null, "Repita sua nova senha.");
		check(novaSenha.compareTo(novaSenha2) == 0, "Sua senha nova e a repetição estão diferentes.");

		usuario.setSenhaCodificada(Crypto.hash(novaSenha));
		usuario.setDeveTrocarSenha(false);
		usuario.setForcaResetSenha(false);
		
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("senha.trocada");
	}

	/**
	 * Envia um e-mail informando a senha do usuário
	 */
	private boolean enviaSenhaUsuario(Usuario usuario, String senha)
	{
		String corpo = "<p>Para acessar o sistema de inscrições do PPGI use seu e-mail como login e a senha <b>" + senha + "</b>. ";
		corpo += "O sistema lhe pedirá uma nova senha no primeiro login. Esta senha também poderá ser trocada posteriormente no ";
		corpo += "sistema.</p>";

		return GerenciadorEmail.getInstance().envia(usuario.getNome(), usuario.getEmail(), "Sua senha de acesso ao sistema do PPGI", corpo);
	}

	/**
	 * Ação para a criação de um novo usuário
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
	 * Ação para a edição de um usuário
	 */
	@Error("/login/homepage.jsp")
	@Success("/jsp/login/novo.jsp")
	public String edita() throws ActionException
	{
		int id = getIntParameter("id", -1);
		Usuario usuario = DAOFactory.getUsuarioDAO().get(id);
		check(usuario != null, "O usuário selecionado não está registrado no sistema.");
		setAttribute("item", usuario);
		return SUCCESS;
	}

	/**
	 * Gera aleatoriamente a senha de um novo usuário
	 */
	private String criaSenhaAleatoria()
	{
		Random r = Crypto.createSecureRandom();
		String senha = "";

		for (int i = 0; i < 3; i++)
			senha = senha + (char) ('A' + r.nextInt(26));

		for (int i = 0; i < 6; i++)
			senha = senha + (char) ('0' + r.nextInt(10));

		return senha;
	}

	/**
	 * Ação de salvamento dos dados de um indivíduo migrado para operador
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/login/novo.jsp")
	public String salva() throws ActionException
	{
		// Pega o identificador do usuário
		int id = getIntParameter("id", -1);

		// Captura ou cria o usuário
		Usuario usuario = (id == -1) ? new Usuario() : DAOFactory.getUsuarioDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", usuario);

		// Captura os dados do formulário
		usuario.setNome(getParameter("nome", ""));
		usuario.setEmail(getParameter("email", usuario.getEmail()));
		usuario.setEndereco(getParameter("endereco", ""));
		usuario.setComplemento(getParameter("complemento", ""));
		usuario.setEstado(getParameter("estado", ""));
		usuario.setMunicipio(getParameter("municipio", ""));
		usuario.setCep(getParameter("cep", ""));
		usuario.setNacionalidade(getParameter("nacionalidade", ""));
		usuario.setSexo(Sexo.get(getParameter("sexo", "")));
		usuario.setDataNascimento(getDateParameter("dataNascimento"));
		usuario.setCpf(getParameter("cpf", ""));
		usuario.setIdentidade(getParameter("identidade", ""));
		usuario.setEmissorIdentidade(getParameter("emissorIdentidade", ""));
		usuario.setTituloEleitor(getParameter("tituloEleitor", ""));
		usuario.setInscricaoPoscomp(getParameter("inscricaoPoscomp", ""));
		usuario.setTelefoneFixo(getParameter("telefoneFixo", ""));
		usuario.setTelefoneCelular(getParameter("telefoneCelular", ""));
		usuario.setNomeCurso(getParameter("nomeCurso", ""));
		usuario.setUniversidade(Universidade.get(getParameter("universidade", "")));
		usuario.setAnoConclusao(getIntParameter("anoConclusao", 0));
		usuario.setInstituicao(getParameter("instituicao", ""));
		usuario.setCargo(getParameter("cargo", ""));
		usuario.setTempoEmpresa(getIntParameter("tempoEmpresa", 0));
		usuario.setObservacoes(getParameter("observacoes", ""));

		// Verifica as regras de negócio
		checkNonEmpty(usuario.getNome(), "O nome do usuário não pode ser vazio");
		checkLength(usuario.getNome(), 80, "O nome do usuário");

		checkNonEmpty(usuario.getEmail(), "O e-mail do usuário não pode ser vazio");
		checkLength(usuario.getEmail(), 80, "O e-mail do usuário");
		checkEmail(usuario.getEmail(), "O e-mail do usuário não está seguindo um formato válido");

		checkNonEmpty(usuario.getEndereco(), "O endereço não pode ser vazio");
		checkLength(usuario.getEndereco(), 80, "O endereço");

		checkLength(usuario.getComplemento(), 80, "O complemento do endereço");

		checkNonEmpty(usuario.getEstado(), "Selecione o estado do usuário");
		checkLength(usuario.getEstado(), 2, "O estado do usuário");

		checkNonEmpty(usuario.getMunicipio(), "Selecione o município do usuário");
		checkLength(usuario.getMunicipio(), 80, "O município do usuário");

		checkNonEmpty(usuario.getCep(), "O CEP do usuário não pode ser vazio");
		checkLength(usuario.getCep(), 10, "O CEP do usuário");

		checkNonEmpty(usuario.getNacionalidade(), "A nacionalidade do usuário não pode ser vazia");
		checkLength(usuario.getNacionalidade(), 20, "A nacionalidade do usuário");

		check(usuario.getSexo() != null, "Selecione o sexo do usuário");
		check(usuario.getDataNascimento() != null, "Selecione a data de nascimento do usuário");

		checkNonEmpty(usuario.getIdentidade(), "A identidade do usuário não pode ser vazia");
		checkLength(usuario.getIdentidade(), 20, "A identidade do usuário");

		checkNonEmpty(usuario.getEmissorIdentidade(), "O emissor da identidade do usuário não pode ser vazio");
		checkLength(usuario.getEmissorIdentidade(), 20, "O emissor da identidade do usuário");

		checkNonEmpty(usuario.getCpf(), "O CPF do usuário não pode ser vazio");
		check(Validators.validaCPF(usuario.getCpf()), "O CPF do usuário não é válido");

		checkNonEmpty(usuario.getTituloEleitor(), "O título de eleitor do usuário não pode ser vazio");
		checkLength(usuario.getTituloEleitor(), 20, "O título de eleitor do usuário");

		checkLength(usuario.getInscricaoPoscomp(), 20, "A inscrição do POSCOMP do usuário");

		checkNonEmpty(usuario.getTelefoneFixo(), "O telefone fixo do usuário não pode ser vazio");
		checkLength(usuario.getTelefoneFixo(), 20, "O telefone fixo do usuário");
		checkPhone(usuario.getTelefoneFixo(), "O telefone fixo do usuário está com um formato inválido");

		checkNonEmpty(usuario.getTelefoneCelular(), "O telefone celular do usuário não pode ser vazio");
		checkLength(usuario.getTelefoneCelular(), 20, "O telefone celular do usuário");
		checkPhone(usuario.getTelefoneCelular(), "O telefone celular do usuário está com um formato inválido");

		checkNonEmpty(usuario.getNomeCurso(), "O nome do curso do usuário não pode ser vazio");
		checkLength(usuario.getNomeCurso(), 40, "O nome do curso do usuário");

		check(usuario.getUniversidade() != null, "Selecione a universidade onde o usuário fez seu curso");

		Usuario usuario2 = DAOFactory.getUsuarioDAO().getUsuarioEmail(usuario.getEmail());
		check(usuario2 == null || usuario2.getId() == usuario.getId(), "Já existe um usuário com esse e-mail");

		Usuario usuario3 = DAOFactory.getUsuarioDAO().getUsuarioCPF(usuario.getCpf());
		check(usuario3 == null || usuario3.getId() == usuario.getId(), "Já existe um usuário com esse CPF");

		if (usuario.getId() <= 0)
		{
			String senha = criaSenhaAleatoria();
			usuario.setSenhaCodificada(Configuracao.getAmbienteHomologacao() ? senha : Crypto.hash(senha));
			
			if (!Configuracao.getAmbienteHomologacao())
				check(enviaSenhaUsuario(usuario, senha), "Ocorreram problemas no envio dos dados para o seu e-mail");
		}

		// Salva os dados do usuário
		DAOFactory.getUsuarioDAO().put(usuario);
		
		if (id == -1)
			return addRedirectNotice("usuario.cadastrado.sucesso");
		else
			return addRedirectNotice("usuario.salvo.sucesso");
	}
}