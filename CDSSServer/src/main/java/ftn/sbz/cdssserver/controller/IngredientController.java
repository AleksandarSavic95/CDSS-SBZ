package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.service.IngredientService;
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
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    public ResponseEntity<Page<Ingredient>> findAll(@RequestParam Integer number,
                                                  @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(number, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Ingredient> ingredientsPage = ingredientService.findAll(pageRequest);
        return new ResponseEntity<>(ingredientsPage, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        Ingredient found = ingredientService.findById(id);
        if (found == null)
            return new ResponseEntity<>("not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Ingredient ingredient) {
        final Ingredient created = ingredientService.create(ingredient);
        if (created == null)
            return new ResponseEntity<>("name taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient updated = ingredientService.update(id, ingredient);
        if (updated == null) {
            return new ResponseEntity<>("non-existing or name taken!",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        ingredientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
