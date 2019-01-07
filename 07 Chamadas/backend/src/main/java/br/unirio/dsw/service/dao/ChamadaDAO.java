package br.unirio.dsw.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.springframework.stereotype.Service;

import br.unirio.dsw.model.Chamada;
import br.unirio.dsw.utils.DateUtils;

/**
 * Classe responsavel pela persistencia de chamadas
 * 
 * @author Marcio Barros
 */
@Service("chamadaDAO")
public class ChamadaDAO extends AbstractDAO
{
	/**
	 * Carrega os dados de uma chamada a partir do resultado de uma consulta
	 */
	private Chamada carrega(ResultSet rs) throws SQLException
	{
		Chamada chamada = new Chamada();
		chamada.setId(rs.getInt("id"));
		chamada.setIdUnidade(rs.getInt("idUnidade"));
		chamada.setNome(rs.getString("nome"));
		chamada.setSigla(rs.getString("sigla"));
		chamada.setDataAberturaInscricoes(DateUtils.toDateTime(rs.getTimestamp("dataAbertura")));
		chamada.setDataEncerramentoInscricoes(DateUtils.toDateTime(rs.getTimestamp("dataEncerramento")));
		chamada.setCancelada(rs.getInt("cancelada") != 0);
		chamada.setEncerrada(rs.getInt("encerrada") != 0);
		chamada.carregaRepresentacaoJsonCampos(rs.getString("campos"));
		chamada.carregaRepresentacaoJsonAnexos(rs.getString("anexos"));
		return chamada;
	}

	/**
	 * Carrega uma chamada, dado seu identificador
	 */
	public Chamada carregaChamadaId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Chamada WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Chamada chamada = rs.next() ? carrega(rs) : null;
			c.close();
			return chamada;

		} catch (SQLException e)
		{
			log("ChamadaDAO.carregaChamadaId: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Adiciona uma chamada no sistema
	 */
	public boolean criaChamada(Chamada chamada)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			String campos = chamada.geraRepresentacaoJsonCampos();
			String anexos = chamada.geraRepresentacaoJsonAnexos();
			
			CallableStatement cs = c.prepareCall("{call ChamadaInsere(?, ?, ?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, chamada.getIdUnidade());
			cs.setString(2, chamada.getNome());
			cs.setString(3, chamada.getSigla());
			cs.setTimestamp(4, new Timestamp(chamada.getDataAberturaInscricoes().getMillis()));
			cs.setTimestamp(5, new Timestamp(chamada.getDataEncerramentoInscricoes().getMillis()));
			cs.setString(6, campos);
			cs.setString(7, anexos);			
			cs.registerOutParameter(8, Types.INTEGER);
			cs.execute();

			chamada.setId(cs.getInt(8));
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("ChamadaDAO.criaChamada: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Atualiza os dados de uma chamada no sistema
	 */
	public boolean atualizaChamada(Chamada chamada)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			String campos = chamada.geraRepresentacaoJsonCampos();
			String anexos = chamada.geraRepresentacaoJsonAnexos();
			
			CallableStatement cs = c.prepareCall("{call ChamadaAtualiza(?, ?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, chamada.getId());
			cs.setString(2, chamada.getNome());
			cs.setString(3, chamada.getSigla());
			cs.setTimestamp(4, new Timestamp(chamada.getDataAberturaInscricoes().getMillis()));
			cs.setTimestamp(5, new Timestamp(chamada.getDataEncerramentoInscricoes().getMillis()));
			cs.setString(6, campos);
			cs.setString(7, anexos);			
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("ChamadaDAO.atualizaChamada: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Remove uma chamada do sistema
	 */
	public boolean removeChamada(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call ChamadaRemove(?)}");
			cs.setInt(1, id);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("ChamadaDAO.removeChamada: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Encerra uma chamada
	 */
	public boolean encerraChamada(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call ChamadaEncerra(?)}");
			cs.setInt(1, id);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("ChamadaDAO.encerraChamada: " + e.getMessage());
			return false;
		}
	}
}