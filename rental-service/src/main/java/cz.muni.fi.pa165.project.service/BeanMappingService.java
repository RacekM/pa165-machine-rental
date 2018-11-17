package cz.muni.fi.pa165.project.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * BeanMappingService interface
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);

    Mapper getMapper();
}
