package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.project.dto.MachineCreateDTO;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.facade.MachineFacade;
import cz.muni.fi.pa165.project.facade.UserFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import cz.muni.fi.pa165.restapi.hateoas.MachineResource;
import cz.muni.fi.pa165.restapi.hateoas.MachineResourceAssembler;
import cz.muni.fi.pa165.restapi.hateoas.UserResource;
import cz.muni.fi.pa165.restapi.hateoas.UserResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * SpringMVC controller for managing REST requests for the user resources.
 *
 * @author Matus Racek (445411@mail.muni.cz)
 */
@RestController
@ExposesResourceFor(UserDTO.class)
@RequestMapping("/users")
public class UsersRestController {
    private final static Logger log = LoggerFactory.getLogger(MachinesRestController.class);
    private UserFacade userFacade;
    private UserResourceAssembler userResourceAssembler;

    public UsersRestController(
            @Autowired UserFacade userFacade,
            @Autowired UserResourceAssembler userResourceAssembler
    ) {
        this.userFacade = userFacade;
        this.userResourceAssembler = userResourceAssembler;
    }

    /**
     * Produces list of all users in JSON.
     *
     * @return list of users
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<UserResource>> users() {
        log.debug("rest users()");
        List<UserDTO> allUsers = userFacade.getAllUsers();
        Resources<UserResource> userResources = new Resources<>(
                userResourceAssembler.toResources(allUsers),
                linkTo(UsersRestController.class).withSelfRel());
                //linkTo(UsersRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(userResources, HttpStatus.OK);
    }


    /**
     * Produces user detail.
     *
     * @param id user identifier
     * @return user detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<UserResource> user(@PathVariable("id") long id) {
        log.debug("rest user({})", id);
        UserDTO userDTO = userFacade.getUserById(id);
        if (userDTO == null) throw new ResourceNotFoundException("user " + id + " not found");
        UserResource userResource = userResourceAssembler.toResource(userDTO);
        return new HttpEntity<>(userResource);
    }


    /**
     * Remove user from system.
     *
     * @param id user identifier
     * @return user detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteUser(@PathVariable("id") long id) {
        log.debug("rest deleteUser({})", id);
        try {
            userFacade.deleteUser(id);
        } catch (IllegalArgumentException ex) {
            log.error("User " + id + " not found");
            throw new ResourceNotFoundException("User " + id + " not found");
        } catch (Throwable ex) {
            log.error("Cannot delete user " + id + " :" + ex.getMessage());
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

    /**
     * Creates a new user.
     *
     * @param userDTO DTO object containing user data
     * @return newly created user
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<UserResource> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        log.debug("rest createUser()");
        if (bindingResult.hasErrors()) {
            log.error("Failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = userFacade.registerUser(userDTO, userDTO.getPasswordHash());
        UserResource resource = userResourceAssembler.toResource(userFacade.getUserById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }



}
