package model.Dao;

import java.util.List;

import model.entities.Aluno;

public interface AlunoDao {
	
	void inserir(Aluno aluno);
	List<Aluno> listarAlunos();
	void atualizar(Aluno aluno);
	Aluno EncontrarById(Integer id);
	void deletar(Integer id);

}
