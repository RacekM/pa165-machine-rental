package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;
import cz.muni.fi.pa165.project.facade.UserFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of {@link UserFacade}
 *
 * @author Martin Sisak, 445384
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;


    @Override
    public UserDTO getUserById(Long customerId) {
        User user = userService.findById(customerId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public Long registerUser(UserDTO userDTO, String plainPassword) {
        User user = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(user, plainPassword);
        return user.getId();
    }

    @Override
    public void deleteUser(Long customerId) {
        User user = userService.findById(customerId);
        if (user != null){
            userService.remove(user);
        }
    }

    @Override
    public void updateUser(UserDTO userDto){
        User user = beanMappingService.mapTo(userDto, User.class);
        userService.update(user);
    }

    public List<UserDTO> getAllByUserType(UserType userType) {
        List<User> users = userService.getAllByCustomerType(userType);
        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userService.findByUsername(username);
        return (user == null) ? null : beanMappingService.mapTo (user, UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO u) {
        User user = userService.findByUsername(u.getUsername());
        if (user != null){
            return userService.authenticate(user, u.getPassword());
        }
        return false;
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        return userService.isAdmin(beanMappingService.mapTo(userDTO, User.class));
    }
}
