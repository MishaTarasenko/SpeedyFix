package ukma.speedyfix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukma.speedyfix.domain.response.InfoResponse;
import ukma.speedyfix.service.info.StoService;

@RestController
@RequestMapping("/api/info")
public class StoController {

    private final StoService stoService;

    public StoController(StoService stoService) {
        this.stoService = stoService;
    }

    @GetMapping
    public ResponseEntity<InfoResponse> checkStoStatus() {
        return ResponseEntity.ok(stoService.getInfo());
    }
}