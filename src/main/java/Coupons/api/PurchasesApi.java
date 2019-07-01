package Coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Purchase;
import Coupons.JavaBeans.UserData;

import Coupons.Logic.PurchasesController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {
	
	@Autowired
	private PurchasesController purchasesController;
	
	@PostMapping
	public void purchaseCoupon(@RequestBody Purchase purchase, HttpServletRequest request) {
		UserData userData = (UserData) request.getAttribute("userData");
		this.purchasesController.purchaseCoupon(purchase, userData);
	}

	/**
	 * @param id Receive an id
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@DeleteMapping("/{id}")
	public void deletePurchase(@PathVariable("id") long id,  HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");

		purchasesController.deletePurchase(id, userData);

	}

	/**
	 * @param customerId Receive a customer id
	 * @param request    Receive a httpServletRequest
	 * @return This function return purchase amount
	 * @throws ApplicationException This function can throw an applicationException
	 */
	

	/**
	 * @param request Receive a httpServletRequest
	 * @return This function return a purchase list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping
	public List<Purchase> getAllPurchases(HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return purchasesController.getAllPurchases(userData);

	}

	/**
	 * @param customerId Receive a customer id
	 * @param request    Receive a httpServletRequest
	 * @return This function return a purchase list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/customer")
	public List<Purchase> getCustomerPurchases(@RequestParam("customerId") long customerId, HttpServletRequest request)
			throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return purchasesController.getCustomerPurchases(customerId, userData);

	}
	
	@GetMapping("/amount")
	public int getPurchaseAmount(@RequestParam("customerId") long customerId, HttpServletRequest request)
			throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return purchasesController.getCustomerPurchaseAmount(customerId, userData);

	}

}
