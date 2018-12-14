package cz.muni.fi.pa165.restapi.hateoas;

import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.restapi.controllers.MachinesRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Assembles a HATEOS-compliant representation of a category from a CategoryDTO.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Component
public class MachineResourceAssembler extends ResourceAssemblerSupport<MachineDTO, MachineResource> {


    private final static Logger log = LoggerFactory.getLogger(MachineResourceAssembler.class);
    private EntityLinks entityLinks;

    public MachineResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                    @Autowired EntityLinks entityLinks) {
        super(MachinesRestController.class, MachineResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public MachineResource toResource(MachineDTO categoryDTO) {
        long id = categoryDTO.getId();
        MachineResource categoryResource = new MachineResource(categoryDTO);
        try {
            /*Link catLink = entityLinks.linkForSingleResource(MachineDTO.class, id).withSelfRel();
            categoryResource.add(catLink);

            Link productsLink = entityLinks.linkForSingleResource(MachineDTO.class, id).slash("/products").withRel("products");
            */
            categoryResource.add(new ArrayList<>());

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return categoryResource;
    }
}
