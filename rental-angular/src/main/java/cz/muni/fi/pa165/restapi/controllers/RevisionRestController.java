package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RevisionCreateDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;
import cz.muni.fi.pa165.project.facade.RevisionFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import cz.muni.fi.pa165.restapi.hateoas.RevisionResource;
import cz.muni.fi.pa165.restapi.hateoas.RevisionResourceAssembler;
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
 * @author Juraj Vandor
 */
@RestController
@ExposesResourceFor(MachineDTO.class)
@RequestMapping("/revisions")
public class RevisionRestController {

    private final static Logger log = LoggerFactory.getLogger(RevisionRestController.class);
    private RevisionFacade revisionFacade;
    private RevisionResourceAssembler revisionResourceAssembler;

    public RevisionRestController(
            @Autowired RevisionFacade revisionFacade,
            @Autowired RevisionResourceAssembler revisionResourceAssembler
    ) {
        this.revisionFacade = revisionFacade;
        this.revisionResourceAssembler = revisionResourceAssembler;
    }

    /**
     * Produces list of all revisions in JSON.
     *
     * @return list of revisions
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<RevisionResource>> revisions() {
        log.debug("rest revisions()");
        List<RevisionDTO> allRevisions = revisionFacade.getAllRevisions();
        Resources<RevisionResource> productsResources = new Resources<>(
                revisionResourceAssembler.toResources(allRevisions),
                linkTo(RevisionRestController.class).withSelfRel(),
                linkTo(RevisionRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(productsResources, HttpStatus.OK);
    }

    /**
     * Produces revision detail.
     *
     * @param id revision identifier
     * @return revision detail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<RevisionResource> revision(@PathVariable("id") long id) {
        log.debug("rest revision({})", id);
        RevisionDTO revisionDTO = revisionFacade.getRevisionById(id);
        if (revisionDTO == null) throw new ResourceNotFoundException("revision " + id + " not found");
        RevisionResource revisionResource = revisionResourceAssembler.toResource(revisionDTO);
        return new HttpEntity<>(revisionResource);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteRevision(@PathVariable("id") long id) {
        log.debug("rest deleteRevision({})", id);
        try {
            revisionFacade.deleteRevision(id);
        } catch (IllegalArgumentException ex) {
            log.error("revision " + id + " not found");
            throw new ResourceNotFoundException("revision " + id + " not found");
        } catch (Throwable ex) {
            log.error("cannot delete revision " + id + " :" + ex.getMessage());
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

    /**
     * Creates a new revision.
     *
     * @param revisionCreateDTO DTO object containing revision data
     * @return newly created revision
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<RevisionResource> createRevision(@RequestBody @Valid RevisionCreateDTO revisionCreateDTO, BindingResult bindingResult) {
        log.debug("rest createRevision()");
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = revisionFacade.createRevision(revisionCreateDTO);
        RevisionResource resource = revisionResourceAssembler.toResource(revisionFacade.getRevisionById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}


