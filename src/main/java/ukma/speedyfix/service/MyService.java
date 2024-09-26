package ukma.speedyfix.service;

import java.util.List;
import java.util.Map;

public interface MyService<E, V, I> {

    E getById(I id);

    List<E> getList(Map<String, Object> criteria);

    I create(V view);

    boolean update(V view);

    boolean delete(I id);
}
