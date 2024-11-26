package tinario994.gmail.com.EstacaoBinasJC.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

import tinario994.gmail.com.EstacaoBinasJC.DTOS.MessageDTO;
import tinario994.gmail.com.EstacaoBinasJC.Services.MessageService;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Endpoint para buscar todas as mensagens com paginação
    @GetMapping
    public ResponseEntity<Page<MessageDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MessageDTO> list = messageService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    // Endpoint para buscar uma mensagem específica pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> findById(@PathVariable Integer id) {
        MessageDTO dto = messageService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    // Endpoint para criar uma nova mensagem
    @PostMapping
    public ResponseEntity<MessageDTO> insert(@Valid @RequestBody MessageDTO dto) {
        MessageDTO savedDto = messageService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    // Endpoint para atualizar uma mensagem existente
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> update(@PathVariable Integer id, @Valid @RequestBody MessageDTO dto) {
        MessageDTO updatedDto = messageService.update(id, dto);
        return ResponseEntity.ok().body(updatedDto);
    }

    // Endpoint para deletar uma mensagem pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
