package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.service.MedicineService;
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
@RequestMapping("/api/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(medicineService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    public ResponseEntity findAll(@RequestParam Integer number,
                                  @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(number, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Medicine> medicinePage = medicineService.findAll(pageRequest);
        return new ResponseEntity<>(medicinePage, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        return new ResponseEntity<>(medicineService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Medicine medicine) {
        final Medicine created = medicineService.create(medicine);
        if (created == null)
            return new ResponseEntity("name taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Medicine medicine) {
        Medicine updated = medicineService.update(id, medicine);
        if (updated == null) {
            return new ResponseEntity("non-existing, name taken or bad ingredients!",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        medicineService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

