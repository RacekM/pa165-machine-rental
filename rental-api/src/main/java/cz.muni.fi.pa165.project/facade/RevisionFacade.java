package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.RevisionCreateDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;

import java.util.List;

/**
 * @author Juraj Vandor
 */
public interface RevisionFacade {
    RevisionDTO getRevisionById(Long revisionId);

    List<RevisionDTO> getAllRevisions();

    Long createRevision(RevisionCreateDTO revisionCreateDTO);

    void deleteRevision(Long revisionId);
}
