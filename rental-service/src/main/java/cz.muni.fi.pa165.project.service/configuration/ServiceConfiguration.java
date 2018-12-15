package cz.muni.fi.pa165.project.service.configuration;

import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.service.UserServiceImpl;
import cz.muni.fi.pa165.project.service.MachineServiceImpl;
import cz.muni.fi.pa165.project.service.RentalServiceImpl;
import cz.muni.fi.pa165.project.service.RevisionServiceImpl;
import cz.muni.fi.pa165.project.service.facade.UserFacadeImpl;
import cz.muni.fi.pa165.project.service.facade.MachineFacadeImpl;
import cz.muni.fi.pa165.project.service.facade.RentalFacadeImpl;
import cz.muni.fi.pa165.project.service.facade.RevisionFacadeImpl;
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
@ComponentScan(basePackageClasses = {MachineServiceImpl.class, MachineFacadeImpl.class,
        RevisionServiceImpl.class, RevisionFacadeImpl.class,
        UserServiceImpl.class, UserFacadeImpl.class,
        RentalServiceImpl.class, RentalFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public DozerBeanMapperFactoryBean dozerMapper() {
        return new DozerBeanMapperFactoryBean();
    }

}
