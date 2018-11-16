package cz.muni.fi.pa165.project.service.configuration;

import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.service.MachineServiceImpl;
import cz.muni.fi.pa165.project.service.facade.MachineFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {MachineServiceImpl.class, MachineFacadeImpl.class})
public class ServiceConfiguration {


    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Machine.class, MachineDTO.class);
        }
    }

}
