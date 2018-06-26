package packageMain;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import packageMain.console.WorkWithConsole;

public class App {
	public static void main(String[] args) throws IOException {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = managerFactory.createEntityManager();
		em.getTransaction().begin();

		WorkWithConsole.consoleMenu(em);

		em.getTransaction().commit();
		em.close();
		managerFactory.close();
	}
}
