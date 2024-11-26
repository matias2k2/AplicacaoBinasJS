package tinario994.gmail.com.EstacaoBinasJC.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

import tinario994.gmail.com.EstacaoBinasJC.DTOS.RouteDTO;
import tinario994.gmail.com.EstacaoBinasJC.Services.RouteService;

@RestController
@RequestMapping(value = "/routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Endpoint para buscar todas as rotas com paginação
    @GetMapping
    public ResponseEntity<Page<RouteDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RouteDTO> list = routeService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    // Endpoint para buscar uma rota específica pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> findById(@PathVariable Integer id) {
        RouteDTO dto = routeService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    // Endpoint para criar uma nova rota
    @PostMapping
    public ResponseEntity<RouteDTO> insert(@Valid @RequestBody RouteDTO dto) {
        RouteDTO savedDto = routeService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    // Endpoint para atualizar uma rota existente
    @PutMapping("/{id}")
    public ResponseEntity<RouteDTO> update(@PathVariable Integer id, @Valid @RequestBody RouteDTO dto) {
        RouteDTO updatedDto = routeService.update(id, dto);
        return ResponseEntity.ok().body(updatedDto);
    }

    // Endpoint para deletar uma rota pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
