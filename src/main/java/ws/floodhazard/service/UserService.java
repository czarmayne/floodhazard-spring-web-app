package ws.floodhazard.service;


import ws.floodhazard.integration.entity.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
