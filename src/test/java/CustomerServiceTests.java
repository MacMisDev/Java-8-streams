import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import pl.edu.ug.inf.entities.Customer;
import pl.edu.ug.inf.entities.Product;
import pl.edu.ug.inf.services.CustomerService;
import pl.edu.ug.inf.services.CustomerServiceInterface;

import java.util.List;
import java.util.stream.Collectors;


public class CustomerServiceTests {

	private CustomerServiceInterface cs;
	private List<Customer> list;

	@Before
	public void prepare(){
		list = DataProducer.getTestData(10);
		cs = new CustomerService(list);
	}

	@Test
	public void testFindByName() {

		List<Customer> res = cs.findByName("Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
	}

	@Test
    public void testCustomersWhoBoughtMoreThan(){
        List<Customer> res = cs.customersWhoBoughtMoreThan(4);

        assertNotNull("Result can't be null", res);
        assertEquals(3, res.size());
    }

    @Test
    public void testCustomersWhoSpentMoreThan(){
		List<Customer> res = cs.customersWhoSpentMoreThan(2);

		assertNotNull("Result can't be null", res);
		assertEquals(4, res.size());
	}

	@Test
	public void testCustomersWithNoOrders(){
		List<Customer> res = cs.customersWithNoOrders();

		assertNotNull("Result can't be null", res);
		assertEquals(3, res.size());
	}

	@Test
	public void testAddProductToAllCustomers(){
		int boughtProducts = list.stream().mapToInt( c -> c.getBoughtProducts().size() ).sum();
		cs.addProductToAllCustomers(new Product(10, "Product : 10", 0.57));
		int boughtProductsAfterPromotion = cs.getCustomers().stream().mapToInt(c -> c.getBoughtProducts().size()).sum();

		assertEquals(boughtProducts+10, boughtProductsAfterPromotion);
	}

	@Test
	public void testAvgOrders(){
		double avgOrder = list.stream().mapToDouble( c -> c.getBoughtProducts().stream().mapToDouble( Product::getPrice ).sum() ).sum() / list.size();
		double res = cs.avgOrders(true);

		assertEquals(avgOrder, res, 0.01);

		double avgOrderWithFalse = list.stream().filter( c -> c.getBoughtProducts().size() > 0 ).mapToDouble( cs -> cs.getBoughtProducts().stream().mapToDouble( Product::getPrice ).sum() ).sum() / list.stream().filter( c -> c.getBoughtProducts().size() > 0).collect(Collectors.toList()).size();
		double resFalse = cs.avgOrders(false);

		assertEquals(avgOrderWithFalse, resFalse, 0.01);

	}






}
