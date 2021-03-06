package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RevisionCreateDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;

import java.util.List;

/**
 * @author Juraj Vandor
 */
public interface RevisionFacade {
    RevisionDTO getRevisionById(Long revisionId);

    List<RevisionDTO> getAllRevisions();

    List<RevisionDTO> getRevisionsOfMachine(MachineDTO machineDTO);

    Long createRevision(RevisionCreateDTO revisionCreateDTO);

    void deleteRevision(Long revisionId);

    RevisionDTO getLastMachineRevision(MachineDTO machineDTO);
}
