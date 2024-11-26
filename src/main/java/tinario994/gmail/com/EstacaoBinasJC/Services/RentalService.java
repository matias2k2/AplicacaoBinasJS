package tinario994.gmail.com.EstacaoBinasJC.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.RentalDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;
import tinario994.gmail.com.EstacaoBinasJC.Models.Rental;
import tinario994.gmail.com.EstacaoBinasJC.Models.User;
import tinario994.gmail.com.EstacaoBinasJC.Repository.BicycleRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RentalRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.UserRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BicycleRepository bicycleRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository,
            BicycleRepository bicycleRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bicycleRepository = bicycleRepository;
    }

    @Transactional
    public Page<RentalDTO> findAllPaged(Pageable pageable) {
        Page<Rental> rentals = rentalRepository.findAll(pageable);
        return rentals.map(RentalDTO::new); // Converte a página de entidades para DTOs
    }

    @Transactional
    public List<RentalDTO> findAll() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream().map(RentalDTO::new).toList();
    }

    @Transactional
    public RentalDTO findById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado com ID: " + id));
        return new RentalDTO(rental);
    }

    @Transactional
    public RentalDTO insert(RentalDTO dto) {
        Rental entity = new Rental();
        copyDtoToEntity(dto, entity);
        entity = rentalRepository.save(entity);
        return new RentalDTO(entity);
    }

    @Transactional
    public RentalDTO update(Integer id, RentalDTO dto) {
        Rental entity = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        entity = rentalRepository.save(entity);
        return new RentalDTO(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!rentalRepository.existsById(id)) {
            throw new EntityNotFoundException("Aluguel não encontrado com ID: " + id);
        }
        rentalRepository.deleteById(id);
    }

    private void copyDtoToEntity(RentalDTO dto, Rental entity) {
        entity.setRentalStart(dto.getRentalStart());
        entity.setRentalEnd(dto.getRentalEnd());

        // Associar usuário ao aluguel
        if (dto.getUser() != null && dto.getUser().getId() != null) {
            User user = userRepository.findById(dto.getUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Usuário não encontrado com ID: " + dto.getUser().getId()));
            entity.setUser(user);
        } else {
            entity.setUser(null); // Remover associação se necessário
        }

        // Associar bicicleta ao aluguel
        if (dto.getBicycle() != null && dto.getBicycle().getId() != null) {
            Bicycle bicycle = bicycleRepository.findById(dto.getBicycle().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Bicicleta não encontrada com ID: " + dto.getBicycle().getId()));
            entity.setBicycle(bicycle);
        } else {
            entity.setBicycle(null); // Remover associação se necessário
        }
    }
}
