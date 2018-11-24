package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RevisionCreateDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.facade.RevisionFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juraj Vandor
 */

public class RevisionFacadeImpl implements RevisionFacade {
    @Inject
    private RevisionService revisionService;

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private MachineService machineService;

    @Override
    public RevisionDTO getRevisionById(Long revisionId) {
        Revision revision = revisionService.findById(revisionId);
        return (revision == null) ? null : beanMappingService.mapTo(revision, RevisionDTO.class);
    }

    @Override
    public List<RevisionDTO> getAllRevisions() {
        List<Revision> res = revisionService.findAll();
        return beanMappingService.mapTo(res, RevisionDTO.class);
    }

    @Override
    public List<RevisionDTO> getRevisionsOfMachine(MachineDTO machineDTO) {
        Machine machine = beanMappingService.mapTo(machineDTO, Machine.class);
        return beanMappingService.mapTo(revisionService.findMachineRevisions(machine), RevisionDTO.class);
    }

    @Override
    public Long createRevision(RevisionCreateDTO revisionCreateDTO) {
        Revision revision = new Revision();
        revision.setResult(revisionCreateDTO.getResult());
        revision.setDate(revisionCreateDTO.getDate());
        revision.setMachine(machineService.findById(revisionCreateDTO.getMachine().getId()));
        revisionService.create(revision);
        return revision.getId();
    }

    @Override
    public void deleteRevision(Long revisionId) {
        Revision revision = revisionService.findById(revisionId);
        if (revision != null) {
            revisionService.remove(revision);
        }
    }

    @Override
    public RevisionDTO getLastMachineRevision(MachineDTO machineDTO) {
        Revision revision = revisionService.getLastMachineRevision(beanMappingService.mapTo(machineDTO, Machine.class));
        return (revision == null) ? null : beanMappingService.mapTo(revision, RevisionDTO.class);
    }
}
