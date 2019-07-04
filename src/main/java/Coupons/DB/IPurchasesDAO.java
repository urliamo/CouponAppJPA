package Coupons.DB;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Coupons.Enums.Category;
import Coupons.JavaBeans.Purchase;

@Repository
public interface IPurchasesDAO extends CrudRepository<Purchase, Long>{

	/**
	 * @param companyId Receive a company id
	 * @return This function return true if exist
	 */
	public boolean existsByIdAndCustomerId(long purchaseID, long customerId);
	
	/**
	 * @param customerId long  customer id
	 * @return returns a list of all purchases
	 */
	public List<Purchase> findByCustomerId(long customerId);

	/**
	 * @param customerId long customer id
	 * @param category   Category(enum) of a category
	 * @return  returns a list of purchase by category
	 */
	public List<Purchase> findByCustomerIdAndCouponCategory(long customerId, Category category);

	/**
	 * @param customerId long customer id
	 * @param maxPrice   double maximum price
	 * @return returns a list of purchases by max price
	 */
	public List<Purchase> findByCustomerIdAndCouponPriceLessThanEqual(long customerId, double maxPrice);
}
