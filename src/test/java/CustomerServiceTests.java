import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import pl.edu.ug.inf.entities.Customer;
import pl.edu.ug.inf.services.CustomerService;
import pl.edu.ug.inf.services.CustomerServiceInterface;

import java.util.List;


public class CustomerServiceTests {

	private CustomerServiceInterface cs;

	@Before
	public void prepare(){
		cs = new CustomerService(DataProducer.getTestData(10));
	}

	@Test
	public void testFindByName() {

		List<Customer> res = cs.findByName("Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
	}

}
