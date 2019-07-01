package Coupons.DB;

import org.springframework.stereotype.Repository;
import Coupons.JavaBeans.User;


//import com.avi.coupons.utils.JdbcUtils;
@Repository
public interface IUsersDAO {
	

	public boolean existsByUserName(String userName);

	/**
	 * @param userName Receive an user name
	 * @param password Receive an password
	 * @return This function return true if user exists
	 */
	public User findByUserNameAndPassword(String userName, String password);

}
