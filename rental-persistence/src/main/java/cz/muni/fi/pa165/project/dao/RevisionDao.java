package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Revision;

import java.util.List;

/**
 * Dao interface for entity revision
 *
 * @author Martin Sisak (445384@mail.muni.cz)
 */
public interface RevisionDao {

    void create(Revision revision);

    void update(Revision revision);

    Revision findById(Long id);

    List<Revision> findAll();

    void delete(Revision revision);

}
