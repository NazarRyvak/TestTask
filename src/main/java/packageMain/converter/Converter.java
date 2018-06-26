package packageMain.converter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import packageMain.entity.Purchase;
import packageMain.query.Query;

public class Converter {

	private static final String KEY = "1eab4be76646296c07b34f1c0f08a60f";

	public static void converter(String str, EntityManager em) throws MalformedURLException, IOException {
		try {
			BigDecimal sum = new BigDecimal(0);
			List<Purchase> listP = Query.getListPurchase(em);
			Iterator<Purchase> iterator = listP.iterator();
			while (iterator.hasNext()) {
				Purchase p = iterator.next();
				if (p.getCurrency().toString().equals(str)) {
					sum = sum.add(p.getPrice());
				} else {
					BigDecimal courseFrom = course(p.getCurrency().toString());
					BigDecimal courseTo = course(str).divide(courseFrom,2,BigDecimal.ROUND_HALF_UP);
					sum = sum.add(p.getPrice().multiply(courseTo));
				}
			}
			System.out.println(String.format("%.2f", sum) + " " + str);
		} catch (JSONException e) {
			System.out.println("You entered incorrected currency to convert");
		}
	}
	
	public static BigDecimal course(String currency) throws JSONException, MalformedURLException, IOException {
		JSONObject json = new JSONObject(IOUtils.toString(
				new URL("http://data.fixer.io/api/latest?access_key=" + KEY + "&base=EUR"
						+ "&symbols=" + currency + "&format=1"),
				Charset.forName("UTF-8")));
		json.get("rates");
		JSONObject json1 = (JSONObject) json.get("rates");
		BigDecimal course = new BigDecimal( json1.get(currency).toString());
		return course;
	}
}
