package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RevisionDao;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juraj Vandor
 */
@Service
public class RevisionServiceImpl implements RevisionService{
    @Inject
    private RevisionDao revisionDao;

    @Override
    public Revision findById(Long revisionId) {
        return revisionDao.findById(revisionId);
    }

    @Override
    public List<Revision> findAll() {
        return revisionDao.findAll();
    }

    @Override
    public List<Revision> findMachineRevisions(Machine machine) {
        return revisionDao.findByMachine(machine);
    }

    @Override
    public void create(Revision revision) {
        revisionDao.create(revision);
    }

    @Override
    public void remove(Revision revision) {
        revisionDao.delete(revision);
    }

    @Override
    public void update(Revision revision) {
        revisionDao.update(revision);
    }

    @Override
    public Revision getLastMachineRevision(Machine machine) {
        return revisionDao.findLastRevisionByMachine(machine);
    }
}
