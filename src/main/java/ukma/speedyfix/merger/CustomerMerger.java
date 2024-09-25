package ukma.speedyfix.merger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.view.CustomerView;
import ukma.speedyfix.repositories.UserRepository;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CustomerMerger {

    private final UserRepository repository;

    public void merge(CustomerEntity entity, CustomerView view) {
        if (view.getId() != null) {
            entity.setId(view.getId());
        }
        if (view.getUserId() != null) {
            entity.setUser(repository.findById(view.getUserId()).orElseThrow(() -> new NoSuchElementException("User with id: " + view.getUserId() + " not found")));
        }
    }
}
