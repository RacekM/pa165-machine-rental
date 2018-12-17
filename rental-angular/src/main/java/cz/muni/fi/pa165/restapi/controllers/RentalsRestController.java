package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.project.dto.RentalCreateDTO;
import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.facade.RentalFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import cz.muni.fi.pa165.restapi.hateoas.RentalResource;
import cz.muni.fi.pa165.restapi.hateoas.RentalResourceAssembler;
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
 * SpringMVC controller for managing REST requests for the rental resources.
 *
 * @author Martin Sisak, 445384
 */
@RestController
@ExposesResourceFor(RentalDTO .class)
@RequestMapping("/rentals")
public class RentalsRestController {

    private final static Logger log = LoggerFactory.getLogger(RentalsRestController.class);
    private RentalFacade rentalFacade;
    private RentalResourceAssembler rentalResourceAssembler;

    public RentalsRestController(
            @Autowired RentalFacade rentalFacade,
            @Autowired RentalResourceAssembler rentalResourceAssembler
    ){
        this.rentalFacade = rentalFacade;
        this.rentalResourceAssembler = rentalResourceAssembler;
    }

    /**
     * Produces list of all rentals in JSON.
     *
     * @return list of rentals
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<RentalResource>> rentals(@RequestParam(value = "machine", required = false) Long machine) {
        log.debug("rest rentals()");
        List<RentalDTO> allRentals = machine == null ? rentalFacade.getAllRentals() :
                rentalFacade.getRentalsByMachine(machine);
        Resources<RentalResource> rentalResources = new Resources<>(
                rentalResourceAssembler.toResources(allRentals),
                linkTo(RentalsRestController.class).withSelfRel(),
                linkTo(RentalsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(rentalResources, HttpStatus.OK);
    }


    /**
     * Produces rental detail.
     *
     * @param id rental identifier
     * @return rental detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<RentalResource> rental(@PathVariable("id") long id) {
        log.debug("rest rental({})", id);
        RentalDTO rentalDTO = rentalFacade.getRentalById(id);
        if (rentalDTO == null) throw new ResourceNotFoundException("rental " + id + " not found");
        RentalResource rentalResource = rentalResourceAssembler.toResource(rentalDTO);
        return new HttpEntity<>(rentalResource);
    }


    /**
     * Remove rental from system.
     *
     * @param id rental identifier
     * @return rental detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteRental(@PathVariable("id") long id) {
        log.debug("rest deleteRental({})", id);
        try {
            rentalFacade.deleteRental(id);
        } catch (IllegalArgumentException ex) {
            log.error("rental " + id + " not found");
            throw new ResourceNotFoundException("rental " + id + " not found");
        } catch (Throwable ex) {
            log.error("Cannot delete rental " + id + " :" + ex.getMessage());
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }


    /**
     * Creates a new rental.
     *
     * @param rentalCreateDTO DTO object containing rental data
     * @return newly created rental
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<RentalResource> createRental(@RequestBody @Valid RentalCreateDTO rentalCreateDTO, BindingResult bindingResult) {
        log.debug("rest createRental()");
        if (bindingResult.hasErrors()) {
            log.error("Failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = rentalFacade.createRental(rentalCreateDTO);
        RentalResource resource = rentalResourceAssembler.toResource(rentalFacade.getRentalById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }



}