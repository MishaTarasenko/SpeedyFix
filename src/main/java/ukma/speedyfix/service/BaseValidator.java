package ukma.speedyfix.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import ukma.speedyfix.domain.entity.UserEntity;

import java.util.Arrays;
import java.util.Set;


public abstract class BaseValidator<E> implements IValidator<E> {

    private Validator validator;

    @Override
    public void validForCreate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations != null && !violations.isEmpty()) {
            throw new ValidationException(UserEntity.class.getName() + Arrays.toString(violations.toArray()));
        }
    }

    @Override
    public void validForUpdate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations != null && !violations.isEmpty()) {
            throw new ValidationException(UserEntity.class.getName() + "errors.EntityCreateException.validate");
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
