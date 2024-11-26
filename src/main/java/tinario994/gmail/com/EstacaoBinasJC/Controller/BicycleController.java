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
import tinario994.gmail.com.EstacaoBinasJC.DTOS.BicycleDTO;
import tinario994.gmail.com.EstacaoBinasJC.Services.BicycleService;
@RestController
@RequestMapping(value = "/bicycles") // Melhor prática: plural e minúsculo
public class BicycleController {
   
    private final BicycleService bicycleService;

    @Autowired
    public BicycleController(BicycleService bicycleService) {
        this.bicycleService = bicycleService;
    }

    @GetMapping
    public ResponseEntity<Page<BicycleDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BicycleDTO> list = bicycleService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BicycleDTO> findById(@PathVariable Integer id) {
        BicycleDTO dto = bicycleService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<BicycleDTO> insert(@Valid @RequestBody BicycleDTO dto) {
        BicycleDTO savedDto = bicycleService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDto.getId()).toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BicycleDTO> update(@PathVariable Integer id, @Valid @RequestBody BicycleDTO dto) {
        BicycleDTO updatedDto = bicycleService.update(id, dto);
        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bicycleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/assign-station/{stationId}")
    public ResponseEntity<BicycleDTO> assignToStation(
            @PathVariable Integer id, 
            @PathVariable Integer stationId) {
        BicycleDTO dto = bicycleService.assignToStation(id, stationId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}/toggle-availability")
    public ResponseEntity<BicycleDTO> toggleAvailability(@PathVariable Integer id) {
        BicycleDTO dto = bicycleService.toggleAvailability(id);
        return ResponseEntity.ok().body(dto);
    }

}
