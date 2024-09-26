package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.service.operation.OperationService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/operation")
public class OperationController {

    private final OperationService operationService;

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody OperationView view) {
        return ResponseEntity.ok(operationService.create(view));
    }
}
