package Coupons.DB;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Coupons.Enums.Category;
import Coupons.JavaBeans.Coupon;

@Repository
public interface ICouponsDAO {

	/**
	 * @param companyId Receive a company id
	 * @param title     Receive a title
	 * @return This function return true if exist
	 */
	public boolean existsByCompanyIdAndTitle(long companyId, String title);

	/**
	 * @param couponId  Receive a coupon id
	 * @param companyId Receive a company id
	 * @return This function return true if exist
	 */
	public boolean existsByIdAndCompanyId(long couponId, long companyId);

	/**
	 * @param companyId Receive a company id
	 * @return This function return list of coupons by some values
	 */
	public List<Coupon> findByCompanyId(long companyId);

	/**
	 * @param companyId Receive a company id
	 * @param category  Receive a category
	 * @return This function return list of coupons by some values
	 */
	public List<Coupon> findByCompanyIdAndCategory(long companyId, Category category);

	/**
	 * @param companyId Receive a company id
	 * @param maxPrice  Receive a max price
	 * @return This function return list of coupons by some values
	 */
	public List<Coupon> findByCompanyIdAndPriceLessThanEqual(long companyId, double maxPrice);

	/**
	 * @param customerId Receive a customer id
	 * @return This function return list of coupons by some values
	 */
	public List<Coupon> findByPurchasesCustomerId(long customerId);

	/**
	 * @param customerId Receive a customer id
	 * @param category   Receive a category
	 * @return This function return list of coupons by some values
	 */
	public List<Coupon> findByPurchasesCustomerIdAndCategory(long customerId, Category category);

	/**
	 * @param customerId Receive a customer id
	 * @param maxPrice   Receive a map price
	 * @return This function return list of coupons by some values
	 */
	public List<Coupon> findByPurchasesCustomerIdAndPriceLessThanEqual(long customerId, double maxPrice);

	/**
	 * @param couponId Receive a coupon id
	 * @return This function return true if coupon valid to purchase
	 */
	@Query("SELECT CASE WHEN (COUNT(c) > 0) THEN TRUE ELSE FALSE END FROM Coupon c WHERE c.id = :coupon_id AND endDate > CURDATE()")
	public boolean isCouponValid(@Param("coupon_id") long couponId);

	/**
	 * This function is a thread that clean invalid coupons
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Coupon c WHERE endDate < CURDATE()")
	public void deleteExpiredCoupon();

}