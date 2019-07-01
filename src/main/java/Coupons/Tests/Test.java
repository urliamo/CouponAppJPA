package Coupons.Tests;

import java.time.LocalDate;
import java.util.ArrayList;

import Coupons.Enums.ClientType;
import Coupons.JavaBeans.Company;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Customer;
import Coupons.Logic.CompanyController;
import Coupons.Logic.CouponController;
import Coupons.Logic.CustomerController;

public class Test {

	
	public static void TestAll() {
		try {
//			Coupons.Logic.LoginManager lManager = new Coupons.Logic.LoginManager();
//		 Thread expiredCouponRemover = new Thread(new Coupons.Jobs.CoupanExpirationDailyJob());
//			System.out.println("expired coupon thread started");
//
//		 ArrayList<Coupon> companyCoupons = new ArrayList<Coupon>();
//		 ArrayList<Company> companies = new ArrayList<Company>();
//		 ArrayList<Customer> customers = new ArrayList<Customer>();
//
//		 Coupons.Logic.CompanyController companyController = (CompanyController) lManager.login("admin@admin.com", "admin", ClientType.Administrator);
//			System.out.println("admin login successfull");
//
//		 Customer testCustomer = new Customer("McClain", "Jhon", 1);
//
//		 Company testCompany = new Company("testCom", "test@company.com", 1);
//
//		 companyController.addCompany(testCompany);
//			System.out.println("company added");
//
//		 companyController.addCustomer(testCustomer);
//			System.out.println("customer added");
//
//		 companies = companyController.getAllCompanies();
//		 customers = companyController.getAllCustomers();
//		 testCustomer = customers.get(0);
//		 testCompany = companies.get(0);
//		 testCustomer.setEmail("adress@change.com");
//		 testCompany.setName("comTest");
//		 companyController.updateCompany(testCompany);
//			System.out.println("company updated");
//
//		 companyController.updateCustomer(testCustomer);
//			System.out.println("customer updated");
//
//		 testCompany= companyController.getCompany(1);
//		 testCustomer = companyController.getCustomer(testCustomer.getId());
//		 companyController.deleteCompany(testCompany);
//		 companyController.deleteCustomer(testCustomer);
//		 companyController.addCompany(testCompany);
//		 companyController.addCustomer(testCustomer);
//		 
//		 Logic.CouponController comFacade = (CouponController) lManager.login("test@company.com","pass", ClientType.Company);
//		 Coupon coupon = new Coupon("test Coupon", "/images/Testimage.png", "testCoupon", 1, 2,LocalDate.of(2018,9,12), LocalDate.of(2020,1,1), testCompany.getId(), 1, 10.5);
//		 
//		 comFacade.addCoupon(coupon);
//		 testCompany = comFacade.getCompany();
//		 companyCoupons = comFacade.getCompanyCoupons();
//		 companyCoupons = comFacade.getCompanyCoupons(20.2);
//		 companyCoupons = comFacade.getCompanyCoupons(1);
//		 coupon.setAmount(5);
//		 comFacade.updateCoupon(coupon);
//		 
//		 Logic.CustomerController cusFacade = (CustomerController) lManager.login("adress@change.com", "YippieKiYay", ClientType.Customer);
//		 cusFacade.purchaseCoupon(coupon);
//		 companyCoupons = cusFacade.getCustomerCoupons();
//		 companyCoupons = cusFacade.getCustomerCoupons(20.5);
//		 companyCoupons = cusFacade.getCustomerCoupons(1);
//		 comFacade.deleteCoupon(coupon);
//		 expiredCouponRemover.interrupt();
		 
	}
		catch (Exception ex) {

            System.out.println("Error: " + ex.getMessage());
        }
	}
}
