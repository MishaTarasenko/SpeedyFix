package ukma.speedyfix.service;

public interface MyService<E, V, I> {

    E getById(I id);

    I create(V view);

    boolean update(V view);

    boolean delete(I id);
}
