package packageMain.query;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import packageMain.entity.Purchase;

public class Query {

	public static void listPurchase(EntityManager em) {
		List<Purchase> pList = em.createQuery("select p from Purchase p  order by p.date asc", Purchase.class)
				.getResultList();
		Iterator<Purchase> iterator = pList.iterator();
		LocalDate date = null;
		while (iterator.hasNext()) {
			Purchase p = iterator.next();
			if (date == null) {
				System.out.println(p.getDate());
				System.out.println(p.getName() + " " + p.getPrice() + " " + p.getCurrency());
			} else if (date.compareTo(p.getDate()) == 0) {
				System.out.println(p.getName() + " " + p.getPrice() + " " + p.getCurrency());
			} else {
				System.out.println(p.getDate());
				System.out.println(p.getName() + " " + p.getPrice() + " " + p.getCurrency());
			}
			date = p.getDate();
		}
	}

	public static void deletePurchaseByDate(EntityManager em, LocalDate date) {
		int count = em.createQuery("delete from Purchase  p where p.date=:pattern").setParameter("pattern", date)
				.executeUpdate();
		System.out.println("Deleted:" + count);
		listPurchase(em);
	}

	public static List<Purchase> getListPurchase(EntityManager em) {
		return em.createQuery("select p from Purchase p", Purchase.class).getResultList();
	}

}
