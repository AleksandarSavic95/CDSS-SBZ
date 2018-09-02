package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.dto.SicknessDto;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.service.RuleService;
import ftn.sbz.cdssserver.service.SicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sicknesses")
public class SicknessController {

    private final SicknessService sicknessService;

    private final RuleService ruleService;

    @Autowired
    public SicknessController(SicknessService sicknessService, RuleService ruleService) {
        this.sicknessService = sicknessService;
        this.ruleService = ruleService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(sicknessService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    public ResponseEntity<Page<Sickness>> findAll(@RequestParam Integer number,
                                                  @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(number, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Sickness> sicknessPage = sicknessService.findAll(pageRequest);
        return new ResponseEntity<>(sicknessPage, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        Sickness found = sicknessService.findById(id);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid SicknessDto sicknessDto) {
        final Sickness created = sicknessService.create(sicknessDto.createSickness());
        if (created == null)
            return new ResponseEntity<>("name taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody SicknessDto sicknessDto) {
        Sickness updated = sicknessService.update(id, sicknessDto.createSickness());
        if (updated == null)
            return new ResponseEntity<>("non-existing or name taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        sicknessService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('DOCTOR')")
//    @PostMapping("/connectedSicknesses")
//    public ResponseEntity getConnectedSicknesses(@RequestBody List<Symptom> symptomList) {
//        final List<PossibleSickness> sicknesses = ruleService.getConnectedSicknesses(symptomList);
//        return new ResponseEntity<>(sicknesses, HttpStatus.OK);
//    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/find")
    public ResponseEntity findSicknessByName(@RequestParam String name) {
        final Sickness found = ruleService.findSicknessByName(name);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
