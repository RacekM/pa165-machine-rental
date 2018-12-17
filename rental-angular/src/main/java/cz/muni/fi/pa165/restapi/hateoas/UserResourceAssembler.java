package cz.muni.fi.pa165.restapi.hateoas;

import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.restapi.controllers.UsersRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Assembles a HATEOS-compliant representation of a user from a UserDTO.
 *
 * @author Matus Racek (445411@mail.muni.cz)
 */
@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<UserDTO, UserResource> {

    private final static Logger log = LoggerFactory.getLogger(UserResourceAssembler.class);

    public UserResourceAssembler() {
        super(UsersRestController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(UserDTO userDTO) {
        long id = userDTO.getId();
        UserResource userResource = new UserResource(userDTO);
        try {
            userResource.add(linkTo(UsersRestController.class).slash(userDTO.getId()).withSelfRel());

            Method deleteUser = UsersRestController.class.getMethod("deleteUser", long.class);
            userResource.add(linkTo(deleteUser.getDeclaringClass(), deleteUser, id).withRel("delete"));
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return userResource;
    }

}
