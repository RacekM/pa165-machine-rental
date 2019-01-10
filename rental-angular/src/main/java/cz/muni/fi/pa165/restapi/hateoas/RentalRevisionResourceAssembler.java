package cz.muni.fi.pa165.restapi.hateoas;

import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;
import cz.muni.fi.pa165.restapi.controllers.RentalsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class RentalRevisionResourceAssembler extends ResourceAssemblerSupport<Map.Entry<RentalDTO, RevisionDTO>, RentalRevisionResource> {

    private final static Logger log = LoggerFactory.getLogger(RevisionResourceAssembler.class);

    public RentalRevisionResourceAssembler() {
        super(RentalsRestController.class, RentalRevisionResource.class);
    }

    @Override
    public RentalRevisionResource toResource(Map.Entry<RentalDTO, RevisionDTO> entry) {
        RentalDTO rentalDTO = entry.getKey();
        RevisionDTO revisionDTO = entry.getValue();
        String lastRevision = "no revision";
        if (revisionDTO != null)
            lastRevision = revisionDTO.getResult() ? "passed" : "failed";
        LocalDateTime revisionDate = revisionDTO != null ? revisionDTO.getDate() : null;
        RentalRevisionResource rentalResource = new RentalRevisionResource(rentalDTO, lastRevision, revisionDate);
        return hateoas(rentalResource, rentalDTO);
    }

    private RentalRevisionResource hateoas(RentalRevisionResource rentalResource, RentalDTO rentalDTO){
        try {
            rentalResource.add(linkTo(RentalsRestController.class).slash(rentalDTO.getId()).withSelfRel());

            Method deleteRental = RentalsRestController.class.getMethod("deleteRental", long.class);
            rentalResource.add(linkTo(deleteRental.getDeclaringClass(), deleteRental, rentalDTO.getId()).withRel("delete"));
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return rentalResource;
    }
}
