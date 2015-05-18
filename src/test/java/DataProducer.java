import pl.edu.ug.inf.entities.Customer;
import pl.edu.ug.inf.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * This is just a proposal for test data generator. You can build your own solution!
 */
public class DataProducer {
	public static List<Customer> getTestData(int count) {
		List<Customer> result = new ArrayList<Customer>();

		for (int i = 0; i < count; i++) {
			Customer c = new Customer(i, "Customer: " + i);
			for (int j = count - i; j > 3; j--) {
				c.addProduct(new Product(j, "Product: " + j, (double) j * 0.1));
			}
			result.add(c);
		}
		return result;

	}
}
