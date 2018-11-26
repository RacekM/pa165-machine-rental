package cz.muni.fi.pa165.project.service;

import com.github.dozermapper.core.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * BeanMappingService interface
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T, V> Map<T, V> mapTo(Map<?, ?> objects, Class<T> mapToClassKey, Class<V> mapToClassValue);

    <T> T mapTo(Object u, Class<T> mapToClass);

    Mapper getMapper();
}
