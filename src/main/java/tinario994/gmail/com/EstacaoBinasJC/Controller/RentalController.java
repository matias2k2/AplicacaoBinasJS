package tinario994.gmail.com.EstacaoBinasJC.Controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.RentalDTO;
import tinario994.gmail.com.EstacaoBinasJC.Services.RentalService;


@RestController
@RequestMapping("/Rental")
public class RentalController {
    
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Page<RentalDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RentalDTO> list = rentalService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> findById(@PathVariable Integer id) {
        RentalDTO dto = rentalService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<RentalDTO> insert(@Valid @RequestBody RentalDTO dto) {
        RentalDTO savedDto = rentalService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> update(@PathVariable Integer id, @Valid @RequestBody RentalDTO dto) {
        RentalDTO updatedDto = rentalService.update(id, dto);
        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
