package ftn.sbz.cdssserver.controller;


import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private MonitoringService monitoringService;

    @Autowired
    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping
    public ResponseEntity getAllOnIntensiveCare() {
        return new ResponseEntity(monitoringService.getAllOnIntensiveCare(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/patients/{id}")
    public ResponseEntity putToIntensiveCare(@PathVariable long id, @RequestBody Sickness sickness) {
        System.out.println("SICKNESS: " + sickness);
        String responseMessage = monitoringService.putPatientToIntensiveCare(id, sickness);
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping("/patients/{id}")
    public ResponseEntity releaseFromIntensiveCare(@PathVariable long id) {
        String responseMessage = monitoringService.releasePatientFromIntensiveCare(id);
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }
}
