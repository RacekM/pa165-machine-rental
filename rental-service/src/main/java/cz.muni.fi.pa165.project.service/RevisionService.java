package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;

import java.util.List;

/**
 * @author Juraj Vandor
 */
public interface RevisionService {
    Revision findById(Long revisionId);

    List<Revision> findAll();

    List<Revision> findMachineRevisions(Machine machine);

    void create(Revision revision);

    void remove(Revision revision);

    void update(Revision revision);

}
