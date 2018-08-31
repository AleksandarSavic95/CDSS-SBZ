package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.sickness.Symptom;
import ftn.sbz.cdssserver.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/symptoms")
public class SymptomController {

    private final SymptomService symptomService;

    @Autowired
    public SymptomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(symptomService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    public ResponseEntity<Page<Symptom>> findAll(@RequestParam Integer number,
                                                  @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(number, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Symptom> symptomsPage = symptomService.findAll(pageRequest);
        return new ResponseEntity<>(symptomsPage, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        Symptom found = symptomService.findById(id);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Symptom symptom) {
        final Symptom created = symptomService.create(symptom);
        if (created == null)
            return new ResponseEntity<>("name taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Symptom symptom) {
        Symptom updated = symptomService.update(id, symptom);
        if (updated == null) {
            return new ResponseEntity<>("non-existing or name taken!",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        symptomService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
