package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.facade.RentalFacade;
import cz.muni.fi.pa165.project.facade.UserFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import cz.muni.fi.pa165.restapi.hateoas.RentalResource;
import cz.muni.fi.pa165.restapi.hateoas.RentalResourceAssembler;
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
    private final static Logger log = LoggerFactory.getLogger(UsersRestController.class);
    private UserFacade userFacade;
    private RentalFacade rentalFacade;
    private UserResourceAssembler userResourceAssembler;
    private RentalResourceAssembler rentalResourceAssembler;

    public UsersRestController(
            @Autowired UserFacade userFacade,
            @Autowired RentalFacade rentalFacade,
            @Autowired UserResourceAssembler userResourceAssembler,
            @Autowired RentalResourceAssembler rentalResourceAssembler
    ) {
        this.userFacade = userFacade;
        this.rentalFacade = rentalFacade;
        this.userResourceAssembler = userResourceAssembler;
        this.rentalResourceAssembler = rentalResourceAssembler;
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
        linkTo(UsersRestController.class).slash("/create").withRel("create");
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


    /**
     * Produces user detail.
     *
     * @return user detail
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public HttpEntity<UserResource> authenticateUser(@RequestBody @Valid UserAuthenticateDTO userAuthenticateDTO, BindingResult bindingResult) {
        log.debug("rest authenticateUser(()");
        if (bindingResult.hasErrors()) {
            log.error("Failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        UserResource user = null;
        if (userFacade.authenticate(userAuthenticateDTO)) {
            user = userResourceAssembler.toResource(userFacade.findUserByUsername(userAuthenticateDTO.getUsername()));
            user.setPasswordHash("");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * Produces user detail.
     *
     * @param id user identifier
     * @return user detail
     */
    @RequestMapping(value = "/{id}/rentals", method = RequestMethod.GET)
    public HttpEntity<Resources<RentalResource>> userRentals(@PathVariable("id") long id) {
        log.debug("rest user({}) rentals", id);
        UserDTO userDTO = userFacade.getUserById(id);
        if (userDTO == null) throw new ResourceNotFoundException("user " + id + " not found");

        List<RentalDTO> allRentals = rentalFacade.getRentalsByUser(userDTO.getId());
        Resources<RentalResource> rentalResources = new Resources<>(
                rentalResourceAssembler.toResources(allRentals),
                linkTo(RentalsRestController.class).withSelfRel(),
                linkTo(RentalsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(rentalResources, HttpStatus.OK);
    }

}
