package tinario994.gmail.com.EstacaoBinasJC.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.LocationDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Location;
import tinario994.gmail.com.EstacaoBinasJC.Models.Route;
import tinario994.gmail.com.EstacaoBinasJC.Repository.LocationRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RouteRepository;

@Service
public class LocationService {
    @Autowired
    private final LocationRepository locationRepository;

    private final RouteRepository routeRepository;

    public LocationService(LocationRepository locationRepository,RouteRepository routeRepository) {
        this.locationRepository = locationRepository;
        this.routeRepository=routeRepository;
    }

    @Transactional(readOnly = true)
    public Page<LocationDTO> findAllPaged(PageRequest pageRequest) {
        Page<Location> result = locationRepository.findAll(pageRequest);
        return result.map(LocationDTO::new);
    }

    @Transactional(readOnly = true)
    public LocationDTO findById(Integer id) {
        Location entity = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        return new LocationDTO(entity);
    }

    @Transactional
    public LocationDTO insert(LocationDTO dto) {
        Location entity = new Location();
        copyDTOToEntity(dto, entity);
        entity = locationRepository.save(entity);
        return new LocationDTO(entity);
    }

    @Transactional
    public LocationDTO update(Integer id, LocationDTO dto) {
        Location entity = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + id));
        copyDTOToEntity(dto, entity);
        entity = locationRepository.save(entity);
        return new LocationDTO(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Bicicleta não encontrada com ID: " + id);
        }
        locationRepository.deleteById(id);
    }

    private void copyDTOToEntity(LocationDTO dto, Location entity) {
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
    
        if (dto.getRouteId() != null) {
            Route route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new EntityNotFoundException("Rota não encontrada com ID: " + dto.getRouteId()));
            entity.setRoute(route);
        } else {
            throw new IllegalArgumentException("O campo routeId é obrigatório.");
        }
    }
    
}
