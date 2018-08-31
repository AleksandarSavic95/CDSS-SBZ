package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.dto.PatientDto;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.security.SecurityUtils;
import ftn.sbz.cdssserver.service.KieSessionService;
import ftn.sbz.cdssserver.service.PatientService;
import ftn.sbz.cdssserver.service.RuleService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final RuleService ruleService;
    private KieSession kieSession;

    @Autowired
    public PatientController(PatientService patientService, RuleService ruleService, KieSession kieSession) {
        this.patientService = patientService;
        this.ruleService = ruleService;
        this.kieSession = kieSession;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/pageable")
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(patientService.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Patient patient = patientService.findById(id);
        return new ResponseEntity<>(new PatientDto(patient), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/medicalCard") // /medicalCard?number="MCN"
    public ResponseEntity findBySSN(@RequestParam String number) {
        return new ResponseEntity<>(patientService.findByMedicalCardNumber(number), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Patient patient) {
        final Patient created = patientService.create(patient);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.update(id, patient), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        patientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/{id}/possibleSicknesses")
    public ResponseEntity getPossibleSicknesses(@PathVariable long id, @RequestBody DiagnosisDto diagnosisDto) {
        final List<PossibleSickness> sicknesses = ruleService.getPossibleSicknesses(id, diagnosisDto);
        return new ResponseEntity<>(sicknesses, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/kie")
    public ResponseEntity getDoctorSession() {
        System.out.println("SecurityUtils.getUsernameOfLoggedUser(): " + SecurityUtils.getUsernameOfLoggedUser());
        return new ResponseEntity<>(
                KieSessionService.getSession(SecurityUtils.getUsernameOfLoggedUser()).getIdentifier(),
                HttpStatus.OK);
    }

    // A A A A A A A A A A A A A A A A A A A A
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ses")
    public ResponseEntity getUserSession() {
        return new ResponseEntity<>(
                kieSession.getIdentifier(),
                HttpStatus.OK);
    }
    // A A A A A A A A A A A A A A A A A A A A

//
//    @PreAuthorize("hasAuthority('DOCTOR')")
//    @PostMapping("/{id}/checkAllergies")
//    public ResponseEntity checkAllergies(@PathVariable long id, @RequestBody List<Medicine> medicine) {
//        final List<AllergyWarning> allergyWarnings = ruleService.checkAllergies(id, medicine);
//        return new ResponseEntity<>(allergyWarnings, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAuthority('DOCTOR')")
//    @GetMapping("/generateReports")
//    public ResponseEntity generateReports() {
//        final PatientReport report = ruleService.generateReports();
//        return new ResponseEntity<>(report, HttpStatus.OK);
//    }
}
