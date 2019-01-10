package cz.muni.fi.pa165.project.service;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * BeanMappingService implementation
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        objects.forEach(object -> mappedCollection.add(dozer.map(object, mapToClass)));
        return mappedCollection;
    }

    @Override
    public <T, V> Map<T, V> mapTo(Map<?, ?> objects, Class<T> mapToClassKey, Class<V> mapToClassValue) {
        System.out.println("service called" + objects + "\n");
        Map<T, V> mappedCollection = new HashMap<>();
        for (Map.Entry<?, ?> e : objects.entrySet()) {
            if (e.getValue() != null)
                mappedCollection.put(dozer.map(e.getKey(), mapToClassKey), dozer.map(e.getValue(), mapToClassValue));
            else
                mappedCollection.put(dozer.map(e.getKey(), mapToClassKey), null);

        }
        return mappedCollection;
    }

    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u, mapToClass);
    }

    public Mapper getMapper() {
        return dozer;
    }
}