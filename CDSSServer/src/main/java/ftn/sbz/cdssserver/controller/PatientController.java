package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleAllergies;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.security.SecurityUtils;
import ftn.sbz.cdssserver.service.KieSessionService;
import ftn.sbz.cdssserver.service.PatientService;
import ftn.sbz.cdssserver.service.RuleService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/page")
    public ResponseEntity<Page<Patient>> findAll(@RequestParam Integer number,
                                                  @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(number, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Patient> patientPage = patientService.findAll(pageRequest);
        return new ResponseEntity<>(patientPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Patient found = patientService.findById(id);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/medicalCard") // /medicalCard?number="MCN"
    public ResponseEntity findByMedicalCardNumber(@RequestParam("number") String cardNumber) {
        final Patient found = patientService.findByMedicalCardNumber(cardNumber);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Patient patient) {
        final Patient created = patientService.create(patient);
        if (created == null)
            return new ResponseEntity<>("Medical card number taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Patient patient) {
        Patient updated = patientService.update(id, patient);
        if (updated == null) {
            return new ResponseEntity<>("non-existing patient!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
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
        if (sicknesses == null) {
            return new ResponseEntity<>("non-existing patient!", HttpStatus.BAD_REQUEST);
        }
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/rules")
    public ResponseEntity sessionFromRuleService() {
        return new ResponseEntity<>(ruleService.getRulesAmount(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/{id}/checkAllergies")
    public ResponseEntity checkAllergies(@PathVariable long id, @RequestBody List<Medicine> medicine) {
        final PossibleAllergies possibleAllergies = ruleService.checkAllergies(id, medicine);
        if (possibleAllergies== null) {
            return new ResponseEntity<>("non-existing patient!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(possibleAllergies, HttpStatus.OK);
    }
//
//    @PreAuthorize("hasAuthority('DOCTOR')")
//    @GetMapping("/generateReports")
//    public ResponseEntity generateReports() {
//        final PatientReport report = ruleService.generateReports();
//        return new ResponseEntity<>(report, HttpStatus.OK);
//    }
}
