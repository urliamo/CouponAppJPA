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
import org.springframework.web.bind.annotation.RestController;

import Coupons.Logic.UsersController;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.LoginData;
import Coupons.JavaBeans.LoginForm;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;


@RestController
@RequestMapping("/users")
public class UsersApi {

	@Autowired
	private UsersController usersController;

	/**
	 * @param user User with user to create
	 * @param request httpServletRequest incoming request
	 * @return long with Id of created user
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@PostMapping
	public long createUser(@RequestBody User user, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return usersController.createUser(user, userData);

	}

	/**
	 * @param userId  long ID of User
	 * @param request httpServletRequest incoming request
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") long userId, HttpServletRequest request)
			throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		usersController.deleteUser(userId, userData);

	}

	/**
	 * @param user    Receive an user
	 * @param request httpServletRequest incoming request
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@PutMapping
	public void updateUser(@RequestBody User user, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		usersController.updateUser(user, userData);

	}

	/**
	 * @param userId  long ID of User
	 * @param request httpServletRequest incoming request
	 * @return String with user name
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/name/{userId}")
	public String getUserName(@PathVariable("userId") long userId, HttpServletRequest request)
			throws ApplicationException {

		UserData UserData = (UserData) request.getAttribute("userData");

		return usersController.getUserName(userId, UserData);

	}

	/**
	 * @param userId  long ID of User
	 * @param request httpServletRequest incoming request
	 * @return User containining user from DB
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") long userId, HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");

		return usersController.getUser(userId, userData);

	}

	/**
	 * @param request httpServletRequest incoming request
	 * @return User containining user from DB list
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@GetMapping
	public List<User> getAllUsers(HttpServletRequest request) throws ApplicationException {

		UserData userData = (UserData) request.getAttribute("userData");
		List<User> userList = usersController.getAllUsers(userData);
		return userList;

	}

	/**
	 * @param login Receive a login (contain user name and password)
	 * @return This function return a client type
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@PostMapping("/login/unsecured")
	public LoginData login(@RequestBody LoginForm login) throws ApplicationException {

		return usersController.login(login);

	}

}
