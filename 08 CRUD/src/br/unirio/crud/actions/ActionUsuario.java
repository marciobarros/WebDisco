package br.unirio.crud.actions;

import java.util.List;

import br.unirio.crud.dao.DAOFactory;
import br.unirio.crud.dao.usuario.OrdenacaoUsuario;
import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ResultType;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.dao.OrderingType;
import br.unirio.simplemvc.json.JSONArray;
import br.unirio.simplemvc.json.JSONObject;

/**
 * Ações relacionadas a manutenção dos usuários
 * 
 * @author marcio.barros
 */
public class ActionUsuario extends Action
{
	/**
	 * Ação que apresenta a página principal de manutenção dos usuários
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/usuario/lista.jsp")
	public String prepara() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * Lista os usuários de acordo com um filtro
	 */
	@Ajax
	public String lista() throws ActionException
	{
		checkLogged();
		
		int numeroPagina = getIntParameter("jtStartIndex", 0) / 25;
		int tamanhoPagina = getIntParameter("jtPageSize", 25); 
		String filtroNome = getParameter("filtroNome", "");
		String filtroEmail = getParameter("filtroEmail", "");
		String filtroEstado = getParameter("filtroEstado", "");
		
		List<Usuario> usuarios = DAOFactory.getUsuarioDAO().lista(numeroPagina, tamanhoPagina, OrdenacaoUsuario.Nome, OrderingType.Ascending, filtroNome, filtroEmail, filtroEstado);
		int numeroUsuarios = DAOFactory.getUsuarioDAO().conta(filtroNome, filtroEmail, filtroEstado);
		JSONArray resultado = new JSONArray();

		for (Usuario usuario : usuarios)
		{
			resultado.add(new JSONObject()
				.add("id", usuario.getId())
				.add("nome", usuario.getNome())
				.add("email", usuario.getEmail())
				.add("estado", usuario.getEstado().getSigla())
				.add("municipio", usuario.getMunicipio()));
		}

		JSONObject root = new JSONObject()
			.add("Result", "OK")
			.add("Records", resultado)
			.add("TotalRecordCount", numeroUsuarios);

		return ajaxUnwrapped(root);
	}

	/**
	 * Ação para criar um novo usuário
	 */
	@Error("/jsp/usuario/lista.jsp")
	@Success("/jsp/usuario/form.jsp")
	public String novo() throws ActionException
	{
		checkLogged();
		Usuario usuario = new Usuario();
		setAttribute("usuario", usuario);
		return SUCCESS;
	}

	/**
	 * Ação para editar um usuário
	 */
	@Error("/jsp/usuario/lista.jsp")
	@Success("/jsp/usuario/form.jsp")
	public String edita() throws ActionException
	{
		checkLogged();
		
		int id = getIntParameter("id", -1);
		check(id > 0, "Selecione o identificador do usuário desejado.");

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioId(id);
		check(usuario != null, "O usuário selecionado não foi encontrado no sistema.");
		setAttribute("usuario", usuario);
		
		return SUCCESS;
	}

	/**
	 * Ação para salvar os dados de um usuário 
	 */
	@Error("/jsp/usuario/form.jsp")
	@Success(type=ResultType.Redirect, value="/jsp/usuario/lista.jsp")
	public String salva() throws ActionException
	{
		checkLogged();

		// Captura os dados do formulário
		Usuario usuario = new Usuario();
		usuario.setId(getIntParameter("id", 0));
		usuario.setNome(getParameter("nome", ""));
		usuario.setEmail(getParameter("email", usuario.getEmail()));
		usuario.setEndereco(getParameter("endereco", ""));
		usuario.setComplemento(getParameter("complemento", ""));
		usuario.setEstado(Estado.get(getParameter("estado", "")));
		usuario.setMunicipio(getParameter("municipio", ""));
		usuario.setCep(getParameter("cep", ""));
		usuario.setTelefoneFixo(getParameter("telefoneFixo", ""));
		usuario.setTelefoneCelular(getParameter("telefoneCelular", ""));
		setAttribute("usuario", usuario);

		// Verifica as regras de negócio
		checkNonEmpty(usuario.getNome(), "O nome do usuário não pode ser vazio");
		checkLength(usuario.getNome(), 80, "O nome do usuário");

		checkNonEmpty(usuario.getEmail(), "O e-mail do usuário não pode ser vazio");
		checkLength(usuario.getEmail(), 80, "O e-mail do usuário");
		checkEmail(usuario.getEmail(), "O e-mail do usuário não está seguindo um formato válido");

		checkNonEmpty(usuario.getEndereco(), "O endereço não pode ser vazio");
		checkLength(usuario.getEndereco(), 80, "O endereço");

		checkLength(usuario.getComplemento(), 80, "O complemento do endereço");

		check(usuario.getEstado() != null, "Selecione o estado do usuário");

		checkNonEmpty(usuario.getMunicipio(), "Selecione o município do usuário");
		checkLength(usuario.getMunicipio(), 80, "O município do usuário");

		checkNonEmpty(usuario.getCep(), "O CEP do usuário não pode ser vazio");
		checkLength(usuario.getCep(), 10, "O CEP do usuário");

		checkNonEmpty(usuario.getTelefoneFixo(), "O telefone fixo do usuário não pode ser vazio");
		checkLength(usuario.getTelefoneFixo(), 20, "O telefone fixo do usuário");
		checkPhone(usuario.getTelefoneFixo(), "O telefone fixo do usuário está com um formato inválido");

		checkNonEmpty(usuario.getTelefoneCelular(), "O telefone celular do usuário não pode ser vazio");
		checkLength(usuario.getTelefoneCelular(), 20, "O telefone celular do usuário");
		checkPhone(usuario.getTelefoneCelular(), "O telefone celular do usuário está com um formato inválido");

		Usuario usuario2 = DAOFactory.getUsuarioDAO().getUsuarioEmail(usuario.getEmail());
		check(usuario2 == null || usuario2.getId() == usuario.getId(), "Já existe um usuário com esse e-mail");

		if (usuario.getId() == -1)
			check(DAOFactory.getUsuarioDAO().adiciona(usuario), "Ocorreu um erro durante o salvamento dos dados do usuário.");
		else
			check(DAOFactory.getUsuarioDAO().atualiza(usuario), "Ocorreu um erro durante o salvamento dos dados do usuário.");
		
		return addRedirectNotice("usuario.atualizado");
	}
	
	/**
	 * Ação AJAX para remover um usuário
	 */
	@Ajax
	public String remove() throws ActionException
	{
		checkLogged();
		
		int id = getIntParameter("id", -1);
		check(id > 0, "Selecione o identificador do usuário desejado.");

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioId(id);
		check(usuario != null, "O usuário selecionado não foi encontrado no sistema.");
		
		check(DAOFactory.getUsuarioDAO().remove(usuario.getId()), "Ocorreu um erro ao remover o usuário.");
		return ajaxSuccess();
	}
}