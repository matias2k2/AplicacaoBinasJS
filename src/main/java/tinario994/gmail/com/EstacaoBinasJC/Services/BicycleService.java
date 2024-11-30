package tinario994.gmail.com.EstacaoBinasJC.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.BicycleDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;
import tinario994.gmail.com.EstacaoBinasJC.Models.Station;
import tinario994.gmail.com.EstacaoBinasJC.Repository.BicycleRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RentalRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.StationRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.UserRepository;



@Service
public class BicycleService {

    private final BicycleRepository bicycleRepository;
    private final StationRepository stationRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    @Autowired
    public BicycleService(BicycleRepository bicycleRepository, StationRepository stationRepository,
                          RentalRepository rentalRepository, UserRepository userRepository) {
        this.bicycleRepository = bicycleRepository;
        this.stationRepository = stationRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    // Busca paginada de bicicletas
    @Transactional
    public Page<BicycleDTO> findAllPaged(PageRequest pageRequest) {
        Page<Bicycle> result = bicycleRepository.findAllWithStations(pageRequest);
        return result.map(BicycleDTO::new); // Converte para DTO
    }

    // Busca bicicleta por ID
    @Transactional
    public BicycleDTO findById(Integer id) {
        Bicycle entity = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        return new BicycleDTO(entity);
    }
 
    // Insere uma nova bicicleta
    @Transactional
    public BicycleDTO insert(BicycleDTO dto) {
        Bicycle entity = new Bicycle();
        copyDTOToEntity(dto, entity);
        entity = bicycleRepository.save(entity);
        return new BicycleDTO(entity);
    }

    // Atualiza bicicleta existente
    @Transactional
    public BicycleDTO update(Integer id, BicycleDTO dto) {
        Bicycle entity = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        copyDTOToEntity(dto, entity);
        entity = bicycleRepository.save(entity);
        return new BicycleDTO(entity);
    }

    // Deleta uma bicicleta pelo ID
    @Transactional
    public void delete(Integer id) {
        if (!bicycleRepository.existsById(id)) {
            throw new EntityNotFoundException("Bicicleta não encontrada com ID: " + id);
        }
        bicycleRepository.deleteById(id);
    }

    // Copia dados do DTO para a entidade Bicycle
    private void copyDTOToEntity(BicycleDTO dto, Bicycle entity) {
        entity.setMarca(dto.getMarca());
        entity.setColor(dto.getColor());
        entity.setBleBeaconId(dto.getBleBeaconId());

        // Atualiza a estação associada, se existir no DTO
        if (dto.getCurrentStation() != null && dto.getCurrentStation().getId() != null) {
            Station station = stationRepository.findById(dto.getCurrentStation().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Estação não encontrada com ID: " + dto.getCurrentStation().getId()));
            entity.setCurrentStation(station);
        } else {
            entity.setCurrentStation(null);
        }
    }

    // Atribui uma bicicleta a uma estação
    @Transactional
    public BicycleDTO assignToStation(Integer id, Integer stationId) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Estação não encontrada com ID: " + stationId));
        bicycle.setCurrentStation(station);
        bicycle = bicycleRepository.save(bicycle);
        return new BicycleDTO(bicycle);
    }

    // Alterna a disponibilidade de uma bicicleta
    @Transactional
    public BicycleDTO toggleAvailability(Integer id) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        // Aqui você pode implementar a lógica de alternância, como mudar um status
        // Exemplificando com uma flag boolean (disponível ou não):
        // bicycle.setAvailable(!bicycle.isAvailable());
        bicycle = bicycleRepository.save(bicycle);
        return new BicycleDTO(bicycle);
    }

    // Lista bicicletas com suas estações associadas
    @Transactional
    public List<BicycleDTO> findByIdWithStation() {
        List<Bicycle> bicycles = bicycleRepository.findAll(); // Modifique para sua consulta específica
        return bicycles.stream()
                .map(BicycleDTO::new)
                .collect(Collectors.toList());
    }
}
