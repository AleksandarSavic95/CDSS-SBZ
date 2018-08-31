package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.Treatment;
import ftn.sbz.cdssserver.service.TreatmentService;
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

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/page")
    public ResponseEntity<Page<Treatment>> findAll(@RequestParam Integer number,
                                                 @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(number, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<Treatment> medicinePage = treatmentService.findAll(pageRequest);
        return new ResponseEntity<>(medicinePage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        Treatment found = treatmentService.findById(id);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Treatment treatment) {
        final Treatment created = treatmentService.create(treatment);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        treatmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
