package br.unirio.dsw.view;

import lombok.Data;

/**
 * Formulário para edição de unidade
 * 
 * @author Marcio
 */
public @Data class FormUnidade 
{
	private int id;
	private String sigla;
	private String nome;
//	private List<GestorUnidade> gestores;
}