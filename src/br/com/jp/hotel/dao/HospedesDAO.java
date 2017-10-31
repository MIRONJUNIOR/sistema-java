package br.com.jp.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jp.hotel.conexao.ConnectionFactory;
import br.com.jp.hotel.model.Hospedes;

public class HospedesDAO {

	Connection conexao = new ConnectionFactory().getConnection();

	// ESTE M�TODO ADICIONA UM NOVO H�SPEDE
	public void adicionar(Hospedes hospede) {
		String sql = "INSERT INTO hospede (nome, endereco, telefone, cpf) VALUES (?,?,?,?)";

		try {

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, hospede.getNome());
			stmt.setString(2, hospede.getEndereco());
			stmt.setString(3, hospede.getTelefone());
			stmt.setString(4, hospede.getCpf());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ESTE M�TODO LISTA TODOS OS H�SPEDES
	public List<Hospedes> listar() {

		try {

			List<Hospedes> hospedes = new ArrayList<Hospedes>();
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM hospede order by codigo");
			ResultSet rs = stmt.executeQuery(); // --> O resultado da query
												// acima � gravado dentro de rs.

			while (rs.next()) {
				// Criando novo objeto hospede
				Hospedes hospede = new Hospedes();

				hospede.setCodigo(rs.getLong("codigo"));
				hospede.setNome(rs.getString("nome"));
				hospede.setEndereco(rs.getString("endereco"));
				hospede.setTelefone(rs.getString("telefone"));
				hospede.setCpf(rs.getString("cpf"));

				// adicionando um hospede � lista de hospedes
				hospedes.add(hospede);
			}

			rs.close();
			stmt.close();
			return hospedes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// FILTRO DE H�SPEDE BUSCADO PELO NOME
	public Hospedes hospedePorNome(String nome) {
		Hospedes hospede = new Hospedes();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM hospede WHERE nome LIKE '%?%'");
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				hospede.setCodigo(rs.getLong("codigo"));
				hospede.setNome("nome");
				hospede.setEndereco("endereco");
				hospede.setCpf("cpf");
				hospede.setTelefone("telefone");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hospede;
	}

	// FILTRO DE H�SPEDE BUSCADO PELO C�DIGO
	public Hospedes buscaHospede(Long codigo) {
		Hospedes hospede = new Hospedes();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM hospede WHERE codigo = ?");
			stmt.setLong(1, codigo);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				hospede.setCodigo(rs.getLong("codigo"));
				hospede.setNome(rs.getString("nome"));
				hospede.setEndereco(rs.getString("endereco"));
				hospede.setCpf(rs.getString("cpf"));
				hospede.setTelefone(rs.getString("telefone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hospede;
	}

	// M�TODO PARA EDITAR O H�SPEDE PELO C�DIGO
	public void editar(Hospedes hospede) {

		try {
			String sql = "UPDATE hospede set nome = ?, endereco = ?, telefone = ?, cpf = ? WHERE codigo = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, hospede.getNome());
			stmt.setString(2, hospede.getEndereco());
			stmt.setString(3, hospede.getTelefone());
			stmt.setString(4, hospede.getCpf());
			stmt.setLong(5, hospede.getCodigo());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// M�TODO PARA EXCLUIR HOSPEDE
	public void excluir(Hospedes hospede) {

		try {

			String sql = "DELETE FROM hospede WHERE codigo = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setLong(1, hospede.getCodigo());
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
