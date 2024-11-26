package tinario994.gmail.com.EstacaoBinasJC.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tinario994.gmail.com.EstacaoBinasJC.DTOS.MessageDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Message;

import tinario994.gmail.com.EstacaoBinasJC.Repository.MessageRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository,UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository=userRepository;
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO> findAllPaged(PageRequest pageRequest) {
        Page<Message> result = messageRepository.findAll(pageRequest);
        return result.map(MessageDTO::new);
    }

    @Transactional(readOnly = true)
    public MessageDTO findById(Integer id) {
        Message entity = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensagem não encontrada com ID: " + id));
        return new MessageDTO(entity);
    }

    @Transactional
    public MessageDTO insert(MessageDTO dto) {
        Message entity = new Message();
        copyDTOToEntity(dto, entity);
        entity = messageRepository.save(entity);
        return new MessageDTO(entity);
    }

    @Transactional
    public MessageDTO update(Integer id, MessageDTO dto) {
        Message entity = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensagem não encontrada com ID: " + id));
        copyDTOToEntity(dto, entity);
        entity = messageRepository.save(entity);
        return new MessageDTO(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!messageRepository.existsById(id)) {
            throw new EntityNotFoundException("Mensagem não encontrada com ID: " + id);
        }
        messageRepository.deleteById(id);
    }

    private void copyDTOToEntity(MessageDTO dto, Message entity) {
        entity.setContent(dto.getContent());
        entity.setTimestamp(dto.getTimestamp());
        entity.setType(dto.getType());
        entity.setPoints(dto.getPoints());
    
        // Atualizar lista de sender
        entity.setSender(dto.getSender().stream().map(userDto -> {
            return userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: ID " + userDto.getId()));
        }).collect(Collectors.toList()));
    
        // Atualizar lista de receiver
        entity.setReceiver(dto.getReceiver().stream().map(userDto -> {
            return userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: ID " + userDto.getId()));
        }).collect(Collectors.toList()));
    }
    
}
