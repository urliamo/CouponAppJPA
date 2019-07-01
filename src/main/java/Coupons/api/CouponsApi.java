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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.CouponController;
import Coupons.Enums.Category;
import Coupons.Exceptions.ApplicationException;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {
	
	@Autowired
	private CouponController couponController;

	/**
	 * @param coupon  Receive a coupon
	 * @param request Receive a httpServletRequest
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		couponController.addCoupon(coupon, userData);

	}

	/**
	 * @param couponId  Receive a coupon id
	 * @param companyId Receive a company id
	 * @param request   Receive a httpServletRequest
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId, @RequestParam("companyId") long companyId,
			HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		couponController.deleteCoupon(couponId, userData);

	}

	/**
	 * @param coupon  Receive a coupon
	 * @param request Receive a httpServletRequest
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		couponController.updateCoupon(coupon, userData);

	}

	/**
	 * @param request Receive a httpServletRequest
	 * @return This function return coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {


		return couponController.getAllCoupons();

	}

	/**
	 * @param couponId Receive a coupon id
	 * @return This function return a coupon
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/{couponId}")
	public Coupon getCoupon(@PathVariable long couponId) throws ApplicationException {

		return couponController.getCoupon(couponId);

	}

	/**
	 * @param companyId Receive a company id
	 * @param request   Receive a httpServletRequest
	 * @return This function return a coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/company")
	public List<Coupon> getCompanyCouponsByCompanyId(@RequestParam("companyId") long companyId,
			HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return couponController.getCompanyCoupons(companyId, userData);

	}

	/**
	 * @param companyId Receive a company id
	 * @param Category  Receive a Categories
	 * @param request   Receive a httpServletRequest
	 * @return This function return a coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/company/category")
	public List<Coupon> getCompanyCouponsByCategories(@RequestParam("companyId") long companyId,
			@RequestParam("category") Category Category, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return couponController.getCompanyCouponsByCategory(companyId, Category, userData);

	}

	/**
	 * @param companyId Receive a company id
	 * @param maxPrice  Receive a max price
	 * @param request   Receive a httpServletRequest
	 * @return This function return a coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/company/price")
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("companyId") long companyId,
			@RequestParam("maxPrice") double maxPrice, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return couponController.getCompanyCouponsByMaxPrice(companyId, maxPrice, userData);

	}

	/**
	 * @param customerId Receive a customer id
	 * @param request    Receive a httpServletRequest
	 * @return This function return a coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/customer")
	public List<Coupon> getCustomerCouponsByCustomerId(@RequestParam("customerId") long customerId,
			HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return couponController.getCustomerCoupons(customerId, userData);

	}

	/**
	 * @param customerId Receive a customer id
	 * @param Category   Receive a Categories
	 * @param request    Receive a httpServletRequest
	 * @return This function return a coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/customer/category")
	public List<Coupon> getCustomerCouponsByCategory(@RequestParam("customerId") long customerId,
			@RequestParam("category") Category Category, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return couponController.getCustomerCouponsByCategory(customerId, Category, userData);

	}

	/**
	 * @param customerId Receive a customer id
	 * @param maxPrice   Receive a max price
	 * @param request    Receive a httpServletRequest
	 * @return This function return a coupon list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/customer/price")
	public List<Coupon> getCustomerCouponsByMaxPrice(@RequestParam("customerId") long customerId,
			@RequestParam("maxPrice") double maxPrice, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return couponController.getCustomerCouponsByMaxPrice(customerId, maxPrice, userData);

	}

}
