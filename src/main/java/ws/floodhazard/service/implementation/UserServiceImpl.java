package ws.floodhazard.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ws.floodhazard.integration.entity.Role;
import ws.floodhazard.integration.entity.User;
import ws.floodhazard.integration.repository.RoleRepository;
import ws.floodhazard.integration.repository.UserRepository;
import ws.floodhazard.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		log.debug("EMAIL {}", email);
		return userRepository.getUserByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		log.debug("USER {}", user.toString());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);

        Role userRole = roleRepository.findByRole("ADMIN");
        if(Objects.isNull(userRole)) {
			userRole = roleRepository.save(Role.builder().id(1).role("ADMIN").build());
        }
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

		log.debug("SAVE USER {}", user.toString());
		userRepository.save(user);
	}

}
