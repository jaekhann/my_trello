package uz.pdp.jaecoder.service;

import lombok.NonNull;
import uz.pdp.jaecoder.criteria.GenericCriteria;
import uz.pdp.jaecoder.dto.Dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <D>
 * @param <CD>
 * @param <UD>
 * @param <C>
 * @param <ID>
 */

public interface GenericCrudService<
        D extends Dto,
        CD extends Dto,
        UD extends Dto,
        C extends GenericCriteria,
        ID extends Serializable> {

    List<D> getAll(@NonNull C criteria);

    D get(@NonNull ID id);

    ID create(@NonNull CD dto);

    boolean update(@NonNull ID id, @NonNull UD dto);

    boolean deleteById(@NonNull ID id);
}
