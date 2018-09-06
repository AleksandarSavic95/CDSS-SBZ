package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportsService reportsService;


    @GetMapping("/chronically-sick")
    public ResponseEntity getChronicallySickPatients() {
        return new ResponseEntity(reportsService.getChronicallySickPatients(), HttpStatus.OK);
    }

    @GetMapping("/addicts")
    public ResponseEntity getAddicts() {
        return new ResponseEntity(reportsService.getAddicts(), HttpStatus.OK);
    }

    @GetMapping("/weak-immunity")
    public ResponseEntity getWeakImmunityPatients() {
        return new ResponseEntity(reportsService.getWeakImmunityPatients(), HttpStatus.OK);
    }
}
