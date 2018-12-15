package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.project.dto.MachineCreateDTO;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.facade.MachineFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.MachineResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringMVC controller for managing REST requests for the category resources. Conforms to HATEOAS principles.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@RestController
@ExposesResourceFor(MachineDTO.class)
@RequestMapping("/machines")
public class MachinesRestController {

    private final static Logger log = LoggerFactory.getLogger(MachinesRestController.class);
    private MachineFacade machineFacade;
    //private MachineResourceAssembler machineResourceAssembler;
    private EntityLinks entityLinks;

    public MachinesRestController(
            @Autowired MachineFacade machineFacade//,
            //@Autowired MachineResourceAssembler machineResourceAssembler,
            //@SuppressWarnings("SpringJavaAutowiringInspection")
            //@Autowired EntityLinks entityLinks
    ) {
        this.machineFacade = machineFacade;
        //this.machineResourceAssembler = machineResourceAssembler;
        this.entityLinks = entityLinks;
    }

    /**
     * Produces list of all categories in JSON.
     *
     * @return list of categories
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<MachineResource>> categories() {
        log.debug("rest machines()");
        List<MachineDTO> allMachines = machineFacade.getAllMachines();
        List<MachineResource> machineResources = new ArrayList<>();
        for (MachineDTO m : allMachines) {
            machineResources.add(new MachineResource(m));
        }
        /*Resources<MachineResource> productsResources = new Resources<>(
                machineResourceAssembler.toResources(allMachines),
                linkTo(MachinesRestController.class).withSelfRel(),
                linkTo(MachinesRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(productsResources, HttpStatus.OK);*/
        return new ResponseEntity<>(machineResources, HttpStatus.OK);
    }

    /**
     * Produces category detail.
     *
     * @param id category identifier
     * @return category detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<MachineResource> category(@PathVariable("id") long id) {
        log.debug("rest machine({})", id);
        MachineDTO machineDTO = machineFacade.getMachineById(id);
        if (machineDTO == null) throw new ResourceNotFoundException("machine " + id + " not found");
        //MachineResource categoryResource = machineResourceAssembler.toResource(machineDTO);
        return new HttpEntity<>(new MachineResource(machineDTO));
    }

    /**
     * Produces a list of products in the given category.
     *
     * @param id category identifier
     * @return list of products in the category
     */
    /*
    @RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
    public HttpEntity<Resources<ProductResource>> products(@PathVariable("id") long id) {
        log.debug("rest category/{}/products()", id);
        CategoryDTO categoryDTO = machineFacade.getCategoryById(id);
        if (categoryDTO == null) throw new ResourceNotFoundException("category " + id + " not found");
        List<ProductDTO> products = productFacade.getProductsByCategory(categoryDTO.getName());
        List<ProductResource> resourceCollection = productResourceAssembler.toResources(products);
        Link selfLink = entityLinks.linkForSingleResource(CategoryDTO.class, id).slash("/products").withSelfRel();
        Resources<ProductResource> productsResources = new Resources<>(resourceCollection, selfLink);
        return new ResponseEntity<>(productsResources, HttpStatus.OK);
    }
    */

    /**
     * Creates a new category.
     *
     * @param machineCreateDTO DTO object containing category name
     * @return newly created category
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<MachineResource> createProduct(@RequestBody @Valid MachineCreateDTO machineCreateDTO, BindingResult bindingResult) {
        log.debug("rest createCategory()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = machineFacade.createMachine(machineCreateDTO);
        MachineResource resource = null;//machineResourceAssembler.toResource(machineFacade.getMachineById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}


