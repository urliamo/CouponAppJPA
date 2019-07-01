package Coupons.DB;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Coupons.JavaBeans.Customer;

@Repository
public interface ICustomersDAO extends CrudRepository<Customer, Long>  {

    
}
