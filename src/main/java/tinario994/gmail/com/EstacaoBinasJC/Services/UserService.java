package tinario994.gmail.com.EstacaoBinasJC.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.UserDTO;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.UserInsertDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Role;
import tinario994.gmail.com.EstacaoBinasJC.Models.User;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RoleRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.UserRepository;
import tinario994.gmail.com.EstacaoBinasJC.Services.Componet.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private RoleRepository _roleRepository;

    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository _userRepository,
            RoleRepository _roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this._userRepository = _userRepository;
        this._roleRepository = _roleRepository;
    }

    @Transactional
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        Page<User> result = _userRepository.findAll(pageRequest);
        return result.map(UserDTO::new);
    }

    @Transactional
    public UserDTO findById(Integer id) {
        Optional<User> obj = _userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        try {
            User entity = new User();
            // Copia apenas os dados básicos
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity.setActive(true); // Por padrão, usuário está ativo

            // Adiciona role padrão ROLE_USER automaticamente
            Role defaultRole = _roleRepository.findByAuthority("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Role padrão ROLE_USER não encontrada"));

            entity.getRoles().add(defaultRole);

            entity = _userRepository.save(entity);
            return new UserDTO(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public UserDTO update(UserDTO entity, Integer id) {
        try {
            User _entity = _userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
            copyDtoToEntity(entity, _entity);
            _entity = _userRepository.save(_entity);
            return new UserDTO(_entity);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        try {
            _userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Violação de integridade de dados.");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.isActive());

        entity.getRoles().clear();

        // Se tem roles específicas no DTO, usa elas
        if (dto.getRolesDTO() != null && !dto.getRolesDTO().isEmpty()) {
            dto.getRolesDTO().forEach(roleDto -> {
                Role role = _roleRepository.findById(roleDto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada: ID " + roleDto.getId()));
                entity.getRoles().add(role);
            });
        }
        // Senão, adiciona a role padrão
        else {
            Role defaultRole = _roleRepository.findByAuthority("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Role padrão ROLE_USER não encontrada"));
            entity.getRoles().add(defaultRole);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = _userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("Email não encontrado");
        }
        logger.info("User found: " + username);
        return user;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO insertWithRoles(UserInsertDTO dto) {
        try {
            // Verificar se o e-mail já está em uso
            if (_userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso");
            }

            // Criar nova entidade de usuário
            User entity = new User();
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(passwordEncoder.encode(dto.getPassword())); // Criptografar a senha
            entity.setActive(dto.isActive()); // Atribuir o status de 'active'

            // Criação da lista de roles para adicionar à entidade
            List<Role> roles = new ArrayList<>();
            dto.getRolesDTO().forEach(roleDto -> {
                Role role = _roleRepository.findById(roleDto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada: ID " + roleDto.getId()));
                roles.add(role); // Adicionar role à lista
            });

            // Agora adiciona as roles na entidade antes de salvar
            entity.setRoles(new HashSet<>(roles)); // Set de roles para garantir unicidade

            // Salva a entidade no repositório
            entity = _userRepository.save(entity);

            // Retornar o DTO do usuário
            return new UserDTO(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir usuário com roles: " + e.getMessage());
        }
    }

}
