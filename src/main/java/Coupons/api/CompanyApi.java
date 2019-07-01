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
import Coupons.JavaBeans.Company;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.CompanyController;

@RestController
@RequestMapping("/Companies")
public class CompanyApi {
	
	@Autowired
	private CompanyController companyController; 
	
	@PostMapping
	public long createCompany(@RequestBody Company company,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
		return companyController.addCompany(company, userData);
	}
	
	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId") long id,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
	companyController.deleteCompany(id, userData);
	}
	
	@PutMapping
	public void updateCompany(@RequestBody Company company,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
		companyController.updateCompany(company, userData);	
	}
	
	@GetMapping("/{companyId}")
	public Company getCoupon(@PathVariable("companyId") long id,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
		Company company = companyController.getCompany(id, userData);
		return company;
	}
	
	@GetMapping
	public List<Company> getAllCompanies(HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return companyController.getAllCompanies(userData);

	}
	
	
	
}
