package Coupons.DB;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Customer;

@Repository
public interface ICustomersDAO extends CrudRepository<Customer, Long>  {

	@Query("SELECT CASE WHEN (COUNT(c) > 0) THEN TRUE ELSE FALSE END FROM Customer c WHERE c.id = :customer_id AND c.isEligibile = TRUE")
	public boolean isCustomerEligible(@Param("customer_id") long customerId);
	
	@Query("SELECT c FROM Customer c WHERE c.id != :customer_id AND EXISTS (select p from Purchase p where p.customer = c.id)")
	public List<Customer> getOpponents(@Param("customer_id") long customerId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Customer set isEligibile = :eligibile WHERE id = :customer_id")
	public void setCustomerEligibile(@Param("eligibile") Boolean eligibile, @Param("customer_id") long customerId);

	@Transactional
	@Modifying
	@Query("UPDATE Customer set isEligibile = TRUE")
	public void setAllCustomersEligibile();

}
