package cz.muni.fi.pa165.restapi.hateoas;

import cz.muni.fi.pa165.project.dto.RevisionDTO;
import cz.muni.fi.pa165.restapi.controllers.MachinesRestController;
import cz.muni.fi.pa165.restapi.controllers.RevisionRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Juraj Vandor
 */
@Component
public class RevisionResourceAssembler extends ResourceAssemblerSupport<RevisionDTO, RevisionResource> {


    private final static Logger log = LoggerFactory.getLogger(RevisionResourceAssembler.class);

    public RevisionResourceAssembler() {
        super(RevisionRestController.class, RevisionResource.class);
    }

    @Override
    public RevisionResource toResource(RevisionDTO revisionDTO) {
        long id = revisionDTO.getId();
        RevisionResource revisionResource = new RevisionResource(revisionDTO);
        try {
            revisionResource.add(linkTo(MachinesRestController.class).slash(revisionDTO.getId()).withSelfRel());

            Method deleteRevision = RevisionRestController.class.getMethod("deleteRevision", long.class);
            revisionResource.add(linkTo(deleteRevision.getDeclaringClass(), deleteRevision, id).withRel("delete"));
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return revisionResource;
    }
}
