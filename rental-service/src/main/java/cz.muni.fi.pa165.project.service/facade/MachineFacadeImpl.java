package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.MachineCreateDTO;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.facade.MachineFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.MachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of {@link MachineFacade}
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

@Service
@Transactional
public class MachineFacadeImpl implements MachineFacade {

    @Inject
    private MachineService machineService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public MachineDTO getMachineById(Long machineId) {
        Machine machine = machineService.findById(machineId);
        return (machine == null) ? null : beanMappingService.mapTo(machine, MachineDTO.class);
    }

    @Override
    public List<MachineDTO> getAllMachines() {
        return beanMappingService.mapTo(machineService.findAll(), MachineDTO.class);
    }

    @Override
    public Long createMachine(MachineCreateDTO machineCreateDTO) {
        Machine machine = beanMappingService.mapTo(machineCreateDTO, Machine.class);
        machineService.create(machine);
        return machine.getId();
    }

    @Override
    public void deleteMachine(Long machineId) {
        Machine machine = machineService.findById(machineId);
        if (machine != null) {
            machineService.remove(machine);
        }
    }
}
