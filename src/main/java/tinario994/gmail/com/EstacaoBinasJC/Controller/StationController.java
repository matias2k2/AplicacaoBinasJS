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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.StationDTO;
import tinario994.gmail.com.EstacaoBinasJC.Services.StationService;

@RestController
@RequestMapping(value = "/stations") // Melhor prática: plural e minúsculo
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<Page<StationDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<StationDTO> list = stationService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDTO> findById(@PathVariable Integer id) {
        StationDTO dto = stationService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<StationDTO> insert(@Valid @RequestBody StationDTO dto) {
        StationDTO savedDto = stationService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDto.getId()).toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationDTO> update(@PathVariable Integer id, @Valid @RequestBody StationDTO dto) {
        StationDTO updatedDto = stationService.update(id, dto);
        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{stationId}/add-bicycle/{bicycleId}")
    public ResponseEntity<StationDTO> addBicycle(
            @PathVariable Integer stationId,
            @PathVariable Integer bicycleId) {
        StationDTO updatedDto = stationService.addBicycle(stationId, bicycleId);
        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping("/{stationId}/remove-bicycle/{bicycleId}")
    public ResponseEntity<StationDTO> removeBicycle(
            @PathVariable Integer stationId,
            @PathVariable Integer bicycleId) {
        StationDTO updatedDto = stationService.removeBicycle(stationId, bicycleId);
        return ResponseEntity.ok().body(updatedDto);
    }

}
