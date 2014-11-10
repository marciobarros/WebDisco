package br.unirio.inscricaoppgi.dao;

import br.unirio.inscricaoppgi.gae.datastore.AbstractDAO;
import br.unirio.inscricaoppgi.model.Sexo;
import br.unirio.inscricaoppgi.model.TipoUsuario;
import br.unirio.inscricaoppgi.model.Universidade;
import br.unirio.inscricaoppgi.model.Usuario;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

/**
 * Classe que cuida da persistência de usuários
 * 
 * @author Marcio
 */
public class UsuarioDAO extends AbstractDAO<Usuario>
{
	/**
	 * Inicializa a classe
	 */
	protected UsuarioDAO()
	{
		super("Usuario");
	}

	/**
	 * Carrega os dados de um usuário
	 */
	@Override
	protected Usuario load(Entity e)
	{
		Usuario usuario = new Usuario();
		usuario.setId((int)e.getKey().getId());
		usuario.setTipoUsuario(TipoUsuario.get(getStringProperty(e, "tipoUsuario", TipoUsuario.CANDIDATO.getCodigo())));
		usuario.setNome(getStringProperty(e, "nome", ""));
		usuario.setEmail(getStringProperty(e, "email", ""));
		usuario.setSenhaCodificada(getStringProperty(e, "senha", ""));
		usuario.setNacionalidade(getStringProperty(e, "nacionalidade", ""));
		usuario.setSexo(Sexo.get(getStringProperty(e, "sexo", Sexo.MASCULUNO.getCodigo())));
		usuario.setDataNascimento(getDateProperty(e, "dataNascimento"));
		usuario.setIdentidade(getStringProperty(e, "identidade", ""));
		usuario.setEmissorIdentidade(getStringProperty(e, "emissorIdentidade", ""));
		usuario.setCpf(getStringProperty(e, "cpf", ""));
		usuario.setTituloEleitor(getStringProperty(e, "tituloEleitor", ""));
		usuario.setInscricaoPoscomp(getStringProperty(e, "inscricaoPoscomp", ""));
		usuario.setEndereco(getStringProperty(e, "endereco", ""));
		usuario.setComplemento(getStringProperty(e, "complemento", ""));
		usuario.setEstado(getStringProperty(e, "estado", ""));
		usuario.setMunicipio(getStringProperty(e, "municipio", ""));
		usuario.setCep(getStringProperty(e, "cep", ""));
		usuario.setTelefoneFixo(getStringProperty(e, "telefoneFixo", ""));
		usuario.setTelefoneCelular(getStringProperty(e, "telefoneCelular", ""));
		usuario.setNomeCurso(getStringProperty(e, "nomeCurso", ""));
		usuario.setUniversidade(Universidade.get(getStringProperty(e, "universidade", Universidade.UNIRIO.getCodigo())));
		usuario.setAnoConclusao(getIntProperty(e, "anoConclusao", 2000));
		usuario.setInstituicao(getStringProperty(e, "instituicao", ""));
		usuario.setCargo(getStringProperty(e, "cargo", ""));
		usuario.setTempoEmpresa(getIntProperty(e, "tempoEmpresa", 0));
		usuario.setObservacoes(getTextProperty(e, "observacoes", ""));
		usuario.setAtivo(getBooleanProperty(e, "ativo", false));
		usuario.setForcaResetSenha(getBooleanProperty(e, "forcaTrocaSenha", false));
		usuario.setDeveTrocarSenha(getBooleanProperty(e, "deveTrocarSenha", true));
		return usuario;
	}

	/**
	 * Salva os dados de um usuário
	 */
	@Override
	protected void save(Usuario usuario, Entity e)
	{
		e.setProperty("tipoUsuario", usuario.getTipoUsuario().getCodigo());
		e.setProperty("nome", usuario.getNome());
		e.setProperty("email", usuario.getEmail());
		e.setProperty("senha", usuario.getSenhaCodificada());
		e.setProperty("nacionalidade", usuario.getNacionalidade());
		e.setProperty("sexo", usuario.getSexo().getCodigo());
		e.setProperty("dataNascimento", usuario.getDataNascimento());
		e.setProperty("identidade", usuario.getIdentidade());
		e.setProperty("emissorIdentidade", usuario.getEmissorIdentidade());
		e.setProperty("cpf", usuario.getCpf());
		e.setProperty("tituloEleitor", usuario.getTituloEleitor());
		e.setProperty("inscricaoPoscomp", usuario.getInscricaoPoscomp());
		e.setProperty("endereco", usuario.getEndereco());
		e.setProperty("complemento", usuario.getComplemento());
		e.setProperty("estado", usuario.getEstado());
		e.setProperty("municipio", usuario.getMunicipio());
		e.setProperty("cep", usuario.getCep());
		e.setProperty("telefoneFixo", usuario.getTelefoneFixo());
		e.setProperty("telefoneCelular", usuario.getTelefoneCelular());
		e.setProperty("nomeCurso", usuario.getNomeCurso());
		e.setProperty("universidade", usuario.getUniversidade().getCodigo());
		e.setProperty("anoConclusao", usuario.getAnoConclusao());
		e.setProperty("instituicao", usuario.getInstituicao());
		e.setProperty("cargo", usuario.getCargo());
		e.setProperty("tempoEmpresa", usuario.getTempoEmpresa());
		e.setProperty("observacoes", new Text(usuario.getObservacoes()));
		e.setProperty("ativo", usuario.isAtivo());
		e.setProperty("forcaTrocaSenha", usuario.isForcaResetSenha());
		e.setProperty("deveTrocarSenha", usuario.isDeveTrocarSenha());
	}

	/**
	 * Carrega um usuário, dado seu e-mail
	 */
	public Usuario getUsuarioEmail(String email)
	{
		return get("email", email);
	}

	/**
	 * Carrega um usuário, dado seu CPF
	 */
	public Usuario getUsuarioCPF(String cpf)
	{
		return get("cpf", cpf);
	}
}