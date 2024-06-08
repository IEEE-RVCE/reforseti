package org.ieeervce.api.siterearnouveau.service;

import java.util.function.Supplier;

import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.exception.DataExistsException;
import org.ieeervce.api.siterearnouveau.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Fetch user by username, from DB table
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {
	private static final Supplier<UsernameNotFoundException> EXCEPTION_SUPPLIER = () -> new UsernameNotFoundException(
			"Username not found");
	static final String USER_ALREADY_EXISTS = "User already exists";
	private final UsersRepository usersRepository;

	public AuthUserDetailsService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public AuthUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Integer userid;
		try {
			userid = Integer.valueOf(username);
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("Not a valid username");
		}
		User user = usersRepository.findById(userid).orElseThrow(EXCEPTION_SUPPLIER);

		return new AuthUserDetails(user);
	}
	public User createIfNotExists(User user) throws DataExistsException {
		if(usersRepository.existsById(user.getUserId())){
			throw new DataExistsException(USER_ALREADY_EXISTS);
		}
		return usersRepository.save(user);
	}
}
