package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.service.vehicle.VehicleService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Integer> createVehicle(@RequestBody VehicleView vehicle) {
        log.info("Creating vehicle: {}", vehicle);
        return ResponseEntity.ok(vehicleService.create(vehicle));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteVehicle(@PathVariable Integer id) {
        log.info("Deleting vehicle with id: {}", id);
        return ResponseEntity.ok(vehicleService.delete(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateVehicle(@RequestBody VehicleView vehicle) {
        log.info("Updating vehicle: {}", vehicle);
        return ResponseEntity.ok(vehicleService.update(vehicle));
    }

    @GetMapping
    public ResponseEntity<List<VehicleEntity>> findAll() {
        return ResponseEntity.ok(vehicleService.getList(null));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<VehicleEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

}
