package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.project.dto.MachineCreateDTO;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.facade.MachineFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import cz.muni.fi.pa165.restapi.hateoas.MachineResource;
import cz.muni.fi.pa165.restapi.hateoas.MachineResourceAssembler;
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
 * SpringMVC controller for managing REST requests for the machine resources.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@RestController
@ExposesResourceFor(MachineDTO.class)
@RequestMapping("/machines")
public class MachinesRestController {

    private final static Logger log = LoggerFactory.getLogger(MachinesRestController.class);
    private MachineFacade machineFacade;
    private MachineResourceAssembler machineResourceAssembler;

    public MachinesRestController(
            @Autowired MachineFacade machineFacade,
            @Autowired MachineResourceAssembler machineResourceAssembler
    ) {
        this.machineFacade = machineFacade;
        this.machineResourceAssembler = machineResourceAssembler;
    }

    /**
     * Produces list of all machines in JSON.
     *
     * @return list of machines
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<MachineResource>> categories() {
        log.debug("rest machines()");
        List<MachineDTO> allMachines = machineFacade.getAllMachines();
        Resources<MachineResource> productsResources = new Resources<>(
                machineResourceAssembler.toResources(allMachines),
                linkTo(MachinesRestController.class).withSelfRel(),
                linkTo(MachinesRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(productsResources, HttpStatus.OK);
    }

    /**
     * Produces machine detail.
     *
     * @param id machine identifier
     * @return machine detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<MachineResource> category(@PathVariable("id") long id) {
        log.debug("rest machine({})", id);
        MachineDTO machineDTO = machineFacade.getMachineById(id);
        if (machineDTO == null) throw new ResourceNotFoundException("machine " + id + " not found");
        MachineResource machineResource = machineResourceAssembler.toResource(machineDTO);
        return new HttpEntity<>(machineResource);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteMachine(@PathVariable("id") long id) {
        System.out.println("DELETING MACHINE");
        log.debug("rest deleteMachine({})", id);
        try {
            machineFacade.deleteMachine(id);
        } catch (IllegalArgumentException ex) {
            log.error("machine " + id + " not found");
            throw new ResourceNotFoundException("machine " + id + " not found");
        } catch (Throwable ex) {
            log.error("cannot delete machine " + id + " :" + ex.getMessage());
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

    /**
     * Creates a new machine.
     *
     * @param machineCreateDTO DTO object containing machine name
     * @return newly created machine
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<MachineResource> createProduct(@RequestBody @Valid MachineCreateDTO machineCreateDTO, BindingResult bindingResult) {
        log.debug("rest createMachine()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = machineFacade.createMachine(machineCreateDTO);
        MachineResource resource = machineResourceAssembler.toResource(machineFacade.getMachineById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}


