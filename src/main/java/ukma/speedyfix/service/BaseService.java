package ukma.speedyfix.service;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService<E, I> implements IService<E, I> {

    @Autowired
    BaseValidator<E> validator;
}
