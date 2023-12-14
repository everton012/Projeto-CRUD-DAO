package model.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.DbConnection;
import db.DbException;
import model.Dao.AlunoDao;
import model.entities.Aluno;

public class AlunoDaoJDBC implements AlunoDao{
	
	Scanner teclado = new Scanner(System.in);
	
	Connection conn = null;
	
	public AlunoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Aluno aluno) {
		String insert = "INSERT INTO aluno (nome, endereco, telefone) values (?, ?, ?)";
	
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pstm.setString(1, aluno.getNome());
			pstm.setString(2, aluno.getEndereco());
			pstm.setLong(3, aluno.getTelefone());
			
			int linhasAfetadas = pstm.executeUpdate();
			if(linhasAfetadas > 0) {
				ResultSet res = pstm.getGeneratedKeys();
				if(res.next()) {
					int id = res.getInt(1);
					aluno.setId(id);
				}
				else {
					throw new DbException ("Erro inseperado ! Nenhuma linha afetada");
				}
			}
			System.out.println("Aluno inserido com sucesso.");
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbConnection.closeStatement(pstm);
			DbConnection.desconectar(conn);
		}
		
	}

	@Override
	public List<Aluno> listarAlunos() {
		String find = "SELECT * FROM aluno";
		PreparedStatement pstm = null;
		ResultSet res = null;
		List<Aluno> alunos = new ArrayList<>();
		
		try {
			pstm = conn.prepareStatement(find, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = pstm.executeQuery();
			
			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				while(res.next()) {
					Aluno aluno = new Aluno();
					aluno.setId(res.getInt("id"));
					aluno.setNome(res.getString("nome"));
					aluno.setEndereco(res.getString("endereco"));
					aluno.setTelefone(res.getLong("telefone"));
					
					alunos.add(aluno);
				}
			}
			
			for(Aluno aluno : alunos) {
				System.out.println(aluno);
			}
			
			return alunos;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbConnection.closeStatement(pstm);
			DbConnection.desconectar(conn);
		}
	}
	
	@Override
	public Aluno EncontrarById(Integer id) {
		String findId = "SELECT * FROM aluno WHERE id=?";
		
		PreparedStatement pstm = null;
		ResultSet res = null;
		
		try {
			pstm = conn.prepareStatement(findId, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstm.setInt(1, id);
			res = pstm.executeQuery();
			
			Aluno aluno = new Aluno();
			
			if(res.next()) {
				aluno.setId(res.getInt("id"));
				aluno.setNome(res.getString("nome"));
				aluno.setEndereco(res.getString("endereco"));
				aluno.setTelefone(res.getLong("telefone"));
			}
			System.out.println(aluno);
			return aluno;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbConnection.closeStatement(pstm);
			DbConnection.desconectar(conn);
		}
	}

	@Override
	public void atualizar(Aluno aluno) {
		
		String upd = "UPDATE aluno SET nome = ?, endereco = ?, telefone = ? WHERE id=?";
		
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(upd);
			
			pstm.setString(1, aluno.getNome());
			pstm.setString(2, aluno.getEndereco());
			pstm.setLong(3, aluno.getTelefone());
			pstm.setInt(4, aluno.getId());
			
			pstm.executeUpdate();
			
			System.out.println("Aluno " + aluno.getNome() + " foi atualizado om sucesso.");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbConnection.closeStatement(pstm);
			DbConnection.desconectar(conn);
		}	
	}

	@Override
	public void deletar(Integer id) {
		String deletar = "DELETE FROM aluno WHERE id=?";
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(deletar);
			pstm.setInt(1, id);
			
			pstm.executeUpdate();
			
			System.out.println("Aluno deletado com sucesso.");
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbConnection.closeStatement(pstm);
			DbConnection.desconectar(conn);
		}
		
		
	}

}
