package cz.muni.fi.pa165.restapi.hateoas;

import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.restapi.controllers.RentalsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class RentalResourceAssembler extends ResourceAssemblerSupport<RentalDTO, RentalResource> {

    private final static Logger log = LoggerFactory.getLogger(RentalResourceAssembler.class);

    public RentalResourceAssembler(){ super(RentalsRestController.class, RentalResource.class); }


    public RentalResource toResource(RentalDTO rentalDTO) {
        long id = rentalDTO.getId();
        RentalResource rentalResource = new RentalResource(rentalDTO);
        try {
            rentalResource.add(linkTo(RentalsRestController.class).slash(rentalDTO.getId()).withSelfRel());

            Method deleteRental = RentalsRestController.class.getMethod("deleteRental", long.class);
            rentalResource.add(linkTo(deleteRental.getDeclaringClass(), deleteRental, id).withRel("delete"));
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return rentalResource;
    }


}
