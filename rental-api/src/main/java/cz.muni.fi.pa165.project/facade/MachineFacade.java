package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.MachineCreateDTO;
import cz.muni.fi.pa165.project.dto.MachineDTO;

import java.util.List;

/**
 * MachineFacade interface
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
public interface MachineFacade {

    /**
     * Finds machine by id number.
     *
     * @param machineId id number of the machine to find
     * @return Found machine if data storage contains it, null otherwise.
     */
    MachineDTO getMachineById(Long machineId);

    /**
     * Gets all machines.
     *
     * @return All machines.
     */
    List<MachineDTO> getAllMachines();

    /**
     * Creates new machine.
     *
     * @param machineCreateDTO new machine to create
     */
    Long createMachine(MachineCreateDTO machineCreateDTO);

    /**
     * Deletes machine.
     *
     * @param machineId id of machine to remove
     */
    void deleteMachine(Long machineId);

}
