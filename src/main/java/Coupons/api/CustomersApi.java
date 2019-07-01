package Coupons.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.CustomerController;

@RestController
@RequestMapping("/customers")
public class CustomersApi {
	
	@Autowired
	private CustomerController customerController; 
	
	
	@PostMapping("/unsecured")
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException {
		//add new customer to DB
		customerController.createCustomer(customer);
	}
	
	@PutMapping
	public void updateCustomer(@RequestBody Customer customer,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
		customerController.updateCustomer(customer, userData);
	}
	
	@GetMapping("/{customerId}")
	public Customer getCustomer(@PathVariable("customerId") long customerId,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
		return customerController.getCustomerByID(customerId, userData);
	}
	
	@DeleteMapping("/{customerId}")
	public void deleteCoupon(@PathVariable("customerId") long customerId,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");

		customerController.deleteCustomer(customerId, userData);
	}
	
	@GetMapping("/name/{customerId}")
	public String getCustomerName(@PathVariable("customerId") long customerId, HttpServletRequest request)
			throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return customerController.getCustomerName(customerId, userData);

	}
	
	@GetMapping
	public List<Customer> getAllCustomers(HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return customerController.getAllCustomers(userData);

	}
}
