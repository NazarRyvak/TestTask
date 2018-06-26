package packageMain.console;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import org.json.JSONException;

import packageMain.converter.Converter;
import packageMain.entity.Purchase;
//import packageMain.entity.enums.Currency;
import packageMain.query.Query;

public class WorkWithConsole {

	private static Scanner sc = new Scanner(System.in);

	private static void savePurchaseToDB(EntityManager em, String data) throws MalformedURLException, IOException {
		Purchase purchase = new Purchase();

		String arr[] = data.split(" ");
		if (arr.length != 4) {
			System.out.println("You entered incorrect  data");
			return;
		}
		purchase.setDate(setLocalDate(arr[0]));
		purchase.setName(arr[1]);
		try {
			purchase.setPrice(new BigDecimal(arr[2]));
		} catch (IllegalArgumentException e) {
			System.out.println("You enter incorrect type of price");
		}
		try {
			//if we can to convert our currency exist
			Converter.course(arr[3]);
			purchase.setCurrency(arr[3]);
		} catch (JSONException e) {
			System.out.println("You enter incorrect type of currency");
		}

		if (purchase.getDate() == null || purchase.getName() == null || purchase.getCurrency() == null
				|| purchase.getPrice() == null) {
			System.out.println("Please enter correct data");
			return;
		} else {
			em.persist(purchase);
			System.out.println("Succesful operation");
		}
	}

	private static LocalDate setLocalDate(String dateParam) {
		LocalDate date = null;

		String dates[] = dateParam.split("-");

		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[2]);

		try {
			if (dates[1].length() == 1 && dates[2].length() == 1) {
				date = LocalDate.parse(year + "-0" + month + "-0" + day);
			} else if (dates[1].length() == 1) {
				date = LocalDate.parse(year + "-0" + month + "-" + day);
			} else if (dates[2].length() == 1) {
				date = LocalDate.parse(year + "-" + month + "-0" + day);
			} else {
				date = LocalDate.parse(dates[0] + "-" + dates[1] + "-" + dates[2]);
			}
		} catch (DateTimeParseException e) {
			System.out.println("You enter incorrected DATE");
		}
		if (date == null) {
			return null;
		} else if (date.compareTo(LocalDate.now()) > 0) {
			System.out.println("This date will be in future");
			return null;
		} else {
			return date;
		}
	}

	public static void consoleMenu(EntityManager em) throws JSONException, IOException {

		while (true) {
			System.out.println("Enter your operation:");
			String str = sc.nextLine();
			String operation;
			String data;
			if (str.contains(" ")) {
				operation = str.substring(0, str.indexOf(" "));
				data = str.substring(str.indexOf(" ")).trim();
			} else {
				operation = str;
				data = null;
			}

			switch (operation) {
			case "add":
				savePurchaseToDB(em, data);
				break;
			case "list":
				if (data==null) {
					Query.listPurchase(em);
				}else {
					System.out.println("You entered incorrect command");
				}
				break;
			case "clear":
				Query.deletePurchaseByDate(em, setLocalDate(data));
				break;
			case "total":
				Converter.converter(data, em);
				break;
			case "out":
				break;
			default:
				System.out.println("You entered incorrect command");
				break;
			}
			if (operation.equals("out")&&data!=null) {
				System.out.println("You entered incorrect command");
			}else if (operation.equals("out")&&data==null) {
				System.out.println("Goodbye!");
				break;
			}
		}
	}
}
