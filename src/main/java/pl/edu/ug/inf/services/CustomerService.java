package pl.edu.ug.inf.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
		return customers.stream().filter( c -> {
			try {
				Field f = c.getClass().getDeclaredField(fieldName);
				f.setAccessible(true);
				return f.get(c).equals(value);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				return false;
			}
		} ).collect(Collectors.toList());
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
		return customers.stream().filter( c -> c.getBoughtProducts().size() == 0 ).collect(Collectors.toList());
	}

	@Override
	public void addProductToAllCustomers(Product p) {
		customers.stream().forEach( c -> c.getBoughtProducts().add(p));

	}

	@Override
	public double avgOrders(boolean includeEmpty) {
		if(includeEmpty){
			return customers.stream().mapToDouble( c -> c.getBoughtProducts().stream().mapToDouble( Product::getPrice ).sum() ).sum() / customers.size();
		}else{
			return customers.stream().filter( c -> c.getBoughtProducts().size() > 0 ).mapToDouble( cs -> cs.getBoughtProducts().stream().mapToDouble( Product::getPrice ).sum() ).sum() / customers.stream().filter( c -> c.getBoughtProducts().size() > 0).collect(Collectors.toList()).size();
		}
	}

	@Override
	public boolean wasProductBought(Product p) {
		return customers.stream().filter(c -> c.getBoughtProducts().stream().filter(pr -> pr.equals(p)).count() > 0).collect(Collectors.toList()).size() > 0;
	}

	@Override
	public List<Product> mostPopularProduct() {
		//todo
        Map<Product, Long> res = customers.stream().flatMap( c -> c.getBoughtProducts().stream()).collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        List<Product> result = new ArrayList<>();
        Long maxValue = null;
        for(Map.Entry<Product, Long> p : res.entrySet()){
            if(maxValue == null || maxValue < p.getValue()){
                maxValue = p.getValue();
                result.clear();
                result.add(p.getKey());
            }else if(maxValue == p.getValue()){
                result.add(p.getKey());
            }
        }
        return result;
	}

	@Override
	public int countBuys(Product product) {
		return customers.stream().mapToInt( c -> c.getBoughtProducts().stream().filter( p -> p.equals(product) ).collect(Collectors.toList()).size() ).sum();
	}

	@Override
	public int countCustomersWhoBought(Product product) {
		return customers.stream().filter(c -> c.getBoughtProducts().contains(product) ).collect(Collectors.toList()).size();
	}

	public List<Customer> getCustomers() {
		return customers;
	}
}
