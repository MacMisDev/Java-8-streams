package pl.edu.ug.inf.services;

import java.util.List;
import java.util.stream.Collectors;

import pl.edu.ug.inf.entities.Product;
import pl.edu.ug.inf.entities.Customer;

public class CustomerService implements CustomerServiceInterface {

	private List<Customer> customers;

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public List<Customer> findByName(final String name) {
		return customers.stream().filter(c -> c.getName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public List<Customer> findByField(String fieldName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> customersWhoBoughtMoreThan(int number) {
		return customers.stream().filter( c -> c.getBoughtProducts().size() > number).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoSpentMoreThan(double price) {
		return customers.stream().filter( c -> c.getBoughtProducts().stream().mapToDouble(Product::getPrice).sum() > price ).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWithNoOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProductToAllCustomers(Product p) {
		// TODO Auto-generated method stub

	}

	@Override
	public double avgOrders(boolean includeEmpty) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean wasProductBought(Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> mostPopularProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countBuys(Product p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
