package br.unirio.inscricaoppgi.model;

import java.util.Date;

import lombok.Data;
import br.unirio.simplemvc.gae.datastore.DataObject;
import br.unirio.simplemvc.servlets.IUser;
import br.unirio.simplemvc.utils.DateUtils;

/**
 * Classe que representa um usuário no sistema
 * 
 * @author Marcio
 */
public @Data class Usuario implements IUser, DataObject
{
	private int id;
	private TipoUsuario tipoUsuario;
	private String nome;
	private String email;
	private String senhaCodificada;
	private String nacionalidade;
	private Sexo sexo;
	private Date dataNascimento;
	private String identidade;
	private String emissorIdentidade;
	private String cpf;
	private String tituloEleitor;
	private String inscricaoPoscomp;
	private String endereco;
	private String complemento;
	private String estado;
	private String municipio;
	private String cep;
	private String telefoneFixo;
	private String telefoneCelular;
	private String nomeCurso;
	private Universidade universidade;
	private int anoConclusao;
	private String instituicao;
	private String cargo;
	private int tempoEmpresa;
	private String observacoes;
	private boolean ativo;
	private boolean forcaResetSenha;
	private boolean deveTrocarSenha;

	public Usuario()
	{
		this.id = -1;
		this.tipoUsuario = TipoUsuario.CANDIDATO;
		this.nome = "";
		this.email = "";
		this.senhaCodificada = "";
		this.nacionalidade = "";
		this.sexo = null;
		this.dataNascimento = null;
		this.identidade = "";
		this.emissorIdentidade = "";
		this.cpf = "";
		this.tituloEleitor = "";
		this.inscricaoPoscomp = "";
		this.endereco = "";
		this.complemento = "";
		this.estado = "";
		this.municipio = "";
		this.cep = "";
		this.telefoneFixo = "";
		this.telefoneCelular = "";
		this.nomeCurso = "";
		this.universidade = Universidade.OUTRA;
		this.anoConclusao = 0;
		this.instituicao = "";
		this.cargo = "";
		this.tempoEmpresa = 0;
		this.ativo = true;
		this.setForcaResetSenha(false);
		this.deveTrocarSenha = true;
	}
	
	@Override
	public String getName()
	{
		return nome;
	}

	@Override
	public boolean isActive()
	{
		return ativo;
	}

	@Override
	public boolean mustChangePassword()
	{
		return deveTrocarSenha;
	}

	@Override
	public boolean checkLevel(String nivel)
	{
		return (tipoUsuario.getCodigo().compareToIgnoreCase(nivel) == 0);
	}
	
	public int getIdade()
	{
		Date d = new Date();
		int diaHoje = DateUtils.getDay(d);
		int mesHoje = DateUtils.getMonth(d);
		int anoHoje = DateUtils.getYear(d);

		int diaNascimento = DateUtils.getDay(dataNascimento);
		int mesNascimento = DateUtils.getMonth(dataNascimento);
		int anoNascimento = DateUtils.getYear(dataNascimento);
		
		if (mesHoje > mesNascimento || (mesHoje == mesNascimento && diaHoje >= diaNascimento))
			return anoHoje - anoNascimento;
		
		return anoHoje - anoNascimento - 1;
	}
}