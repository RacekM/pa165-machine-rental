package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;

import java.util.List;

/**
 * Dao interface for entity {@link Revision}
 *
 * @author Martin Sisak (445384@mail.muni.cz)
 */
public interface RevisionDao {

    /**
     * Persists revision in data storage
     *
     * @param revision revision to persist
     */
    void create(Revision revision);

    /**
     * Updates revision in data storage
     *
     * @param revision revision to update
     */
    void update(Revision revision);

    /**
     * Finds revision by its ID
     *
     * @param id ID of the given revision
     * @return revision, if found in data storage, null otherwise
     */
    Revision findById(Long id);

    /**
     * Finds all revisions in data storage
     *
     * @return list of all revisions from data storage
     */
    List<Revision> findAll();

    /**
     * Deletes revision from data storage
     *
     * @param revision revision to be deleted
     */
    void delete(Revision revision);

    /**
     * Find all revisions for one given machine
     *
     * @param machine machine for which the revisions are being found
     * @return List of revisions associated with the given machine
     */
    List<Revision> findByMachine(Machine machine);

    /**
     * Find last Revision for given machine
     * @param machine machine for which we need to look up last revision
     */
    Revision findLastRevisionByMachine(Machine machine);
}
