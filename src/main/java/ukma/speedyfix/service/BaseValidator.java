package ukma.speedyfix.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import ukma.speedyfix.exception.ValidationException;

import java.util.Arrays;
import java.util.Set;


public abstract class BaseValidator<E> implements MyValidator<E> {

    private Validator validator;
    private final Class<E> persistentClass;

    public BaseValidator(Class<E> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public void validForCreate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations != null && !violations.isEmpty()) {
            throw new ValidationException(persistentClass.getName() + Arrays.toString(violations.toArray()));
        }
    }

    @Override
    public void validForUpdate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations != null && !violations.isEmpty()) {
            throw new ValidationException(persistentClass.getName() + "Invalid update: " + violations);
        }
    }

    @Override
    public void validForDelete(E entity) {

    }

    @Override
    public void validForView(E entity) {

    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
