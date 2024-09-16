package ukma.speedyfix.service;

public interface IValidator<E> {

    void validForCreate(E entity);

    void validForUpdate(E entity);

    void validForView(E entity);

    void validForDelete(E entity);
}
