package Coupons.DB;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Coupons.JavaBeans.Purchase;

@Repository
public interface IPurchasesDAO extends CrudRepository<Purchase, Long>{

	/**
	 *  returns true if coupon  with specified ID belonging to customer with specified ID exists
	 *
	 * @param  coupondId the ID of the coupon to be searched
	 * @param  customerId the ID of the customer to be searched
	 * @return		true if coupon belongs to customer
	 */
	
	public List<Purchase> findByCustomerId(long customerId);
}
