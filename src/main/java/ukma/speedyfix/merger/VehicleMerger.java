package ukma.speedyfix.merger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.repositories.CustomerRepository;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class VehicleMerger {
    private final CustomerRepository repository;

    public void mergeCreate(VehicleEntity entity, VehicleView view) {
        if (view.getBrand() != null) {
            entity.setBrand(view.getBrand());
        }
        if (view.getModel() != null) {
            entity.setModel(view.getModel());
        }
        if (view.getYearOfRelease() != null) {
            entity.setYearOfRelease(view.getYearOfRelease());
        }
        if (view.getEngineType() != null) {
            entity.setEngineType(view.getEngineType());
        }
        if (view.getDisplacement() != null) {
            entity.setDisplacement(view.getDisplacement());
        }
        if (view.getTransmissionType() != null) {
            entity.setTransmissionType(view.getTransmissionType());
        }
        if (view.getWheelRadius() != null) {
            entity.setWheelRadius(view.getWheelRadius());
        }
        if (view.getRegistrationNumber() != null) {
            entity.setRegistrationNumber(view.getRegistrationNumber());
        }
        if (view.getOwnerId() != null) {
            entity.setOwner(
                    repository.findById(view.getOwnerId())
                            .orElseThrow(()->new NoSuchEntityException("Customer with id: " + view.getOwnerId() + " not found!"))
            );
        }
    }

    public void mergeUpdate(VehicleEntity entity, VehicleView view) {
        if (view.getBrand() != null) {
            entity.setBrand(view.getBrand());
        }
        if (view.getModel() != null) {
            entity.setModel(view.getModel());
        }
        if (view.getYearOfRelease() != null) {
            entity.setYearOfRelease(view.getYearOfRelease());
        }
        if (view.getEngineType() != null) {
            entity.setEngineType(view.getEngineType());
        }
        if (view.getDisplacement() != null) {
            entity.setDisplacement(view.getDisplacement());
        }
        if (view.getTransmissionType() != null) {
            entity.setTransmissionType(view.getTransmissionType());
        }
        if (view.getWheelRadius() != null) {
            entity.setWheelRadius(view.getWheelRadius());
        }
        if (view.getRegistrationNumber() != null) {
            entity.setRegistrationNumber(view.getRegistrationNumber());
        }
    }
}
