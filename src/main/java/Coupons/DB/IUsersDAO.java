package Coupons.DB;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.User;


//import com.avi.coupons.utils.JdbcUtils;
@Repository
public interface IUsersDAO extends CrudRepository<User, Long>{
	

	public boolean existsByUserName(String userName);
	public boolean existsByEmail(String email);

	/**
	 * @param userName Receive an user name
	 * @param password Receive an password
	 * @return This function return true if user exists
	 */
	public User findByUserNameAndPassword(String userName, String password);

}
