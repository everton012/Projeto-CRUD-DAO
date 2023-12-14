package model.service;

import java.util.Scanner;

import model.Dao.AlunoDao;
import model.Dao.DaoFactory;
import model.entities.Aluno;

public class MenuEscolha {
	
	static Scanner teclado = new Scanner(System.in);
	
	public static void Menu() {
		System.out.println("---------- Gerenciamento de Alunos ----------");
		System.out.println("Escolha uma opção.");
		System.out.println("1- Inserir novo aluno(a).");
		System.out.println("2- Listar todos os alunos.");
		System.out.println("3- Procurar aluno(a) específico.");
		System.out.println("4- Atualizar dados do aluno(a)");
		System.out.println("5- Deletear aluno(a).");
		System.out.println("6- sair.");
		
		int opcao = Integer.parseInt(teclado.nextLine());
		
			switch(opcao) {
			case 1: 
				while (opcao != 0 && opcao < 5 ) {
					System.out.println("Informe o nome do novo aluno: ");
					String nome = teclado.nextLine();
					
					System.out.println("Informe o endereço do novo aluno:");
					String endereco = teclado.nextLine();
					
					System.out.println("Informe o telefone do novo aluno: ");
					long telefone = teclado.nextLong();
					
					Aluno aluno = new Aluno(null, nome, endereco, telefone);
					
					AlunoDao alunoDao = DaoFactory.createAlunoDao();
					alunoDao.inserir(aluno);
					
					System.out.println("Quer inserir mais um aluno ? 1: sim ou 6: sair");
					opcao = teclado.nextInt();
					
					teclado.nextLine();
				}
				break;
				
			case 2:
				DaoFactory.createAlunoDao().listarAlunos();
				break;
				
			case 3:
				System.out.println("Informe o id do aluno: ");
				int id = teclado.nextInt();
				
				DaoFactory.createAlunoDao().EncontrarById(id);
				break;
			
			case 4:
				System.out.println("Você está atualizando o aluno ");
				System.out.println("Informe o id do aluno: ");
				int id1 = teclado.nextInt();
				teclado.nextLine();
				
				System.out.println("Informe o nome do aluno");
				String nome = teclado.nextLine();
				
				System.out.println("Informe o endereço do aluno: ");
				String endereco = teclado.nextLine();
				
				System.out.println("Informe o telefone do aluno: ");
				Long telefone = teclado.nextLong();
				
				Aluno aluno = new Aluno(id1, nome, endereco, telefone);
				
				AlunoDao alunoDao = DaoFactory.createAlunoDao();
				alunoDao.atualizar(aluno);
				break;
			
			case 5:
				System.out.println("Informe o id do aluno a ser deletado: ");
				id = teclado.nextInt();
				
				DaoFactory.createAlunoDao().deletar(id);
				break;
				default:
					opcao = 6;
			}
	}

}
