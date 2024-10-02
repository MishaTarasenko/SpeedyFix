package ukma.speedyfix.merger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.view.CustomerView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.repositories.UserRepository;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CustomerMerger {

    private final UserRepository repository;

    public void merge(CustomerEntity entity, CustomerView view) {
        if (view.getUserId() != null) {
            entity.setUser(repository.findById(view.getUserId()).orElseThrow(() -> new NoSuchEntityException("User with id: " + view.getUserId() + " not found")));
        }
    }
}
