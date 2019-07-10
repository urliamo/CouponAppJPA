package Coupons.DB;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Customer;

@Repository
public interface ICustomersDAO extends CrudRepository<Customer, Long>  {

	@Query("SELECT CASE WHEN (COUNT(c) > 0) THEN TRUE ELSE FALSE END FROM Coupon c WHERE c.id = :coupon_id AND c.eligibile = TRUE")
	public boolean isCustomerEligible(@Param("customer_id") long customerId);

}
