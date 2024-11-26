package tinario994.gmail.com.EstacaoBinasJC.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.BicycleDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;
import tinario994.gmail.com.EstacaoBinasJC.Models.Rental;
import tinario994.gmail.com.EstacaoBinasJC.Models.Station;
import tinario994.gmail.com.EstacaoBinasJC.Repository.BicycleRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RentalRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.StationRepository;

@Service
public class BicycleService {

    private final BicycleRepository bicycleRepository;
    private final StationRepository stationRepository;
    private final RentalRepository rentalRepository;
    @Autowired
    public BicycleService(BicycleRepository bicycleRepository, StationRepository stationRepository,RentalRepository rentalRepositor) {
        this.bicycleRepository = bicycleRepository;
        this.stationRepository = stationRepository;
        this.rentalRepository = rentalRepositor;
    }

    @Transactional(readOnly = true)
    public Page<BicycleDTO> findAllPaged(PageRequest pageRequest) {
        Page<Bicycle> result = bicycleRepository.findAll(pageRequest);
        return result.map(BicycleDTO::new);
    }

    @Transactional(readOnly = true)
    public BicycleDTO findById(Integer id) {
        Bicycle entity = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        return new BicycleDTO(entity);
    }

    @Transactional
    public BicycleDTO insert(BicycleDTO dto) {
        Bicycle entity = new Bicycle();
        copyDTOToEntity(dto, entity);
        entity = bicycleRepository.save(entity);
        return new BicycleDTO(entity);
    }

    @Transactional
    public BicycleDTO update(Integer id, BicycleDTO dto) {
        Bicycle entity = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        copyDTOToEntity(dto, entity);
        entity = bicycleRepository.save(entity);
        return new BicycleDTO(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!bicycleRepository.existsById(id)) {
            throw new EntityNotFoundException("Bicicleta não encontrada com ID: " + id);
        }
        bicycleRepository.deleteById(id);
    }
    private void copyDTOToEntity(BicycleDTO dto, Bicycle entity) {
        entity.setMarca(dto.getMarca());
        entity.setColor(dto.getColor());
        entity.setBleBeaconId(dto.getBleBeaconId());
    
        // Verificar se currentStation está presente no DTO
        if (dto.getCurrentStation() != null && dto.getCurrentStation().getId() != null) {
            Station entityStation = stationRepository.findById(dto.getCurrentStation().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Estação não encontrada com ID: " + dto.getCurrentStation().getId()));
            entity.setCurrentStation(entityStation);
        } else {
            // Se currentStation for null, remova qualquer associação
            entity.setCurrentStation(null);
        }
    
        // Limpar e processar rentals
        entity.getRentals().clear();
        if (dto.getRentals() != null && !dto.getRentals().isEmpty()) {
            dto.getRentals().forEach(rentalDTO -> {
                if (rentalDTO.getId() != null) {
                    Rental rental = rentalRepository.findById(rentalDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Aluguel não encontrado com ID: " + rentalDTO.getId()));
                    entity.getRentals().add(rental);
                }
            });
        }
    }
    
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

@Transactional
public BicycleDTO toggleAvailability(Integer id) {
    Bicycle bicycle = bicycleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
    bicycle = bicycleRepository.save(bicycle);
    return new BicycleDTO(bicycle);
}

}
