package cz.muni.fi.pa165.restapi.hateoas;

import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.restapi.controllers.MachinesRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Assembles a HATEOS-compliant representation of a machine from a MachineDTO.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
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
    public MachineResource toResource(MachineDTO machineDTO) {
        long id = machineDTO.getId();
        MachineResource machineResource = new MachineResource(machineDTO);
        try {
            machineResource.add(linkTo(MachinesRestController.class).slash(machineDTO.getId()).withSelfRel());

            Method deleteMachine = MachinesRestController.class.getMethod("deleteMachine", long.class);
            machineResource.add(linkTo(deleteMachine.getDeclaringClass(), deleteMachine, id).withRel("delete"));
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return machineResource;
    }
}
