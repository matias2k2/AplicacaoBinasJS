package tinario994.gmail.com.EstacaoBinasJC.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.StationDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;
import tinario994.gmail.com.EstacaoBinasJC.Models.Station;
import tinario994.gmail.com.EstacaoBinasJC.Repository.BicycleRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.StationRepository;

@Service
public class StationService {

    private final StationRepository stationRepository;
    private final BicycleRepository bicycleRepository;

    @Autowired
    public StationService(StationRepository stationRepository, BicycleRepository bicycleRepository) {
        this.stationRepository = stationRepository;
        this.bicycleRepository = bicycleRepository;
    }

    @Transactional(readOnly = true)
    public Page<StationDTO> findAllPaged(PageRequest pageRequest) {
        Page<Station> result = stationRepository.findAll(pageRequest);
        return result.map(StationDTO::new);
    }

    @Transactional(readOnly = true)
    public StationDTO findById(Integer id) {
        Station entity = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estação não encontrada com ID: " + id));
        return new StationDTO(entity);
    }

    @Transactional
    public StationDTO insert(StationDTO dto) {
        Station entity = new Station();
        copyDtoToEntity(dto, entity);
        entity = stationRepository.save(entity);
        return new StationDTO(entity);
    }

    @Transactional
    public StationDTO update(Integer id, StationDTO dto) {
        Station entity = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estação não encontrada com ID: " + id));
        copyDtoToEntity(dto, entity);
        entity = stationRepository.save(entity);
        return new StationDTO(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!stationRepository.existsById(id)) {
            throw new EntityNotFoundException("Estação não encontrada com ID: " + id);
        }
        stationRepository.deleteById(id);
    }

    private void copyDtoToEntity(StationDTO dto, Station entity) {
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("DTO: " + dto.toString());
        System.out.println("Entidade antes de salvar: " + entity.toString());

        if (dto.getBicycles() != null && !dto.getBicycles().isEmpty()) {
            dto.getBicycles().forEach(bicycleDTO -> {
                Bicycle bicycle = bicycleRepository.findById(bicycleDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Bicicleta não encontrada com ID: " + bicycleDTO.getId()));
                entity.getBicycles().add(bicycle);
            });
        }
    }

    @Transactional
    public StationDTO addBicycle(Integer stationId, Integer bicycleId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Estação não encontrada"));

        Bicycle bicycle = bicycleRepository.findById(bicycleId)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada"));

        if (bicycle.getCurrentStation() != null) {
            throw new IllegalStateException("Bicicleta já está associada a uma estação");
        }

        bicycle.setCurrentStation(station);
        station.getBicycles().add(bicycle);

        station = stationRepository.save(station);
        return new StationDTO(station);
    }

    @Transactional
    public StationDTO removeBicycle(Integer stationId, Integer bicycleId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Estação não encontrada"));

        Bicycle bicycle = bicycleRepository.findById(bicycleId)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada"));

        if (!station.getBicycles().contains(bicycle)) {
            throw new IllegalStateException("Bicicleta não está associada a esta estação");
        }

        bicycle.setCurrentStation(null);
        station.getBicycles().remove(bicycle);

        station = stationRepository.save(station);
        return new StationDTO(station);
    }
}
