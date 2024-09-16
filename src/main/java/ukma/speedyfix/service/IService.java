package ukma.speedyfix.service;

import java.util.List;
import java.util.Map;

public interface IService<E, I> {

    E getById(I id);

    List<E> getList(Map<String, Object> criteria);

    I create(E view);

    boolean update(E view);

    boolean delete(I id);
}
