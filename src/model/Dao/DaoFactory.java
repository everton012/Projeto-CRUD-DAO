package model.Dao;

import db.DbConnection;
import model.Dao.impl.AlunoDaoJDBC;

public class DaoFactory {

	public static AlunoDao createAlunoDao() {
		return new AlunoDaoJDBC(DbConnection.conectar());
	}
}
