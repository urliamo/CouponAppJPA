package Coupons.DB;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Coupons.JavaBeans.Company;



@Repository
public interface ICompaniesDAO extends CrudRepository<Company, Long> {
	

	public boolean existsByName(String name);

}