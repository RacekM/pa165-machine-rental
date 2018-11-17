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

    MachineDTO getMachineById(Long machineId);

    List<MachineDTO> getAllMachines();

    Long createMachine(MachineCreateDTO machineCreateDTO);

    void deleteMachine(Long machineId);

    //void changeMachineName(NewMachineNameDTO newMachineNameDTO);
    //void changeMachineRentPrice(NewMachinePriceDTO newMachinePrice);

}
