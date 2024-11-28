package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.VehicleResponse;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.service.vehicle.VehicleService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping("/user/api/vehicle")
    public ResponseEntity<Integer> createVehicle(@RequestBody VehicleView vehicle) {
        log.info("Creating vehicle: {}", vehicle);
        return ResponseEntity.ok(vehicleService.create(vehicle));
    }

    @DeleteMapping(path = "/user/api/vehicle/{id}")
    public ResponseEntity<Boolean> deleteVehicle(@PathVariable Integer id) {
        log.info("Deleting vehicle with id: {}", id);
        return ResponseEntity.ok(vehicleService.delete(id));
    }

    @PutMapping("/user/api/vehicle")
    public ResponseEntity<Boolean> updateVehicle(@RequestBody VehicleView vehicle) {
        log.info("Updating vehicle: {}", vehicle);
        return ResponseEntity.ok(vehicleService.update(vehicle));
    }

    @GetMapping("/admin/api/vehicle")
    public ResponseEntity<List<VehicleResponse>> findAll() {
        return ResponseEntity.ok(vehicleService.getListResponse());
    }

    @GetMapping(path = "/auth/api/vehicle/{id}")
    public ResponseEntity<VehicleResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(vehicleService.getResponseById(id));
    }

    @GetMapping(path = "/auth/api/vehicle/owner/{id}")
    public ResponseEntity<List<VehicleResponse>> findByOwner(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(vehicleService.getVehiclesByCustomerId(id));
    }
}
