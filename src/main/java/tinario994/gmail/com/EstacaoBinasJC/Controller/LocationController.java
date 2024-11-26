package tinario994.gmail.com.EstacaoBinasJC.Controller;

import java.net.URI;
import org.springframework.web.bind.annotation.RequestBody;

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

import jakarta.validation.Valid;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.LocationDTO;
import tinario994.gmail.com.EstacaoBinasJC.Services.LocationService;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;

    }

    @GetMapping
    public ResponseEntity<Page<LocationDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LocationDTO> list = locationService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> findById(@PathVariable Integer id) {
        LocationDTO dto = locationService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<LocationDTO> insert(@Valid @RequestBody LocationDTO dto) {
        LocationDTO savedDto = locationService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> update(@PathVariable Integer id, @Valid @RequestBody LocationDTO dto) {
        LocationDTO updatedDto = locationService.update(id, dto);
        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
