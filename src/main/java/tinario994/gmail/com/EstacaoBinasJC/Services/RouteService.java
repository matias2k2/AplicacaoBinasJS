package tinario994.gmail.com.EstacaoBinasJC.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tinario994.gmail.com.EstacaoBinasJC.DTOS.RouteDTO;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;
import tinario994.gmail.com.EstacaoBinasJC.Models.Location;
import tinario994.gmail.com.EstacaoBinasJC.Models.Route;
import tinario994.gmail.com.EstacaoBinasJC.Models.User;
import tinario994.gmail.com.EstacaoBinasJC.Repository.BicycleRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RouteRepository;
import tinario994.gmail.com.EstacaoBinasJC.Repository.UserRepository;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final BicycleRepository bicycleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository, BicycleRepository bicycleRepository, UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.bicycleRepository = bicycleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Page<RouteDTO> findAllPaged(PageRequest pageRequest) {
        Page<Route> result = routeRepository.findAll(pageRequest);
        return result.map(RouteDTO::new);
    }

    @Transactional
    public RouteDTO findById(Integer id) {
        Route entity = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rota não encontrada com ID: " + id));
        return new RouteDTO(entity);
    }

    @Transactional
    public RouteDTO insert(RouteDTO dto) {
        Route entity = new Route();
        copyDTOToEntity(dto, entity);
        entity = routeRepository.save(entity);
        return new RouteDTO(entity);
    }

    @Transactional
    public RouteDTO update(Integer id, RouteDTO dto) {
        Route entity = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rota não encontrada com ID: " + id));
        copyDTOToEntity(dto, entity);
        entity = routeRepository.save(entity);
        return new RouteDTO(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!routeRepository.existsById(id)) {
            throw new EntityNotFoundException("Rota não encontrada com ID: " + id);
        }
        routeRepository.deleteById(id);
    }

    private void copyDTOToEntity(RouteDTO dto, Route entity) {
        // Configurar bicicleta
        if (dto.getBicycle() != null && dto.getBicycle().getId() != null) {
            Bicycle bicycle = bicycleRepository.findById(dto.getBicycle().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Bicicleta não encontrada com ID: " + dto.getBicycle().getId()));
            entity.setBicycle(bicycle);
        }

        // Configurar usuário
        if (dto.getUserRoute() != null && dto.getUserRoute().getId() != null) {
            User user = userRepository.findById(dto.getUserRoute().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getUserRoute().getId()));
            entity.setUserRoute(user);
        }

        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setDistance(dto.getDistance());
        entity.setPointsEarned(dto.getPointsEarned());

        // Configurar trajetória
        if (dto.getTrajectory() != null && !dto.getTrajectory().isEmpty()) {
            entity.getTrajectory().clear();
            dto.getTrajectory().forEach(locationDTO -> {
                Location location = new Location();
                location.setLatitude(locationDTO.getLatitude());
                location.setLongitude(locationDTO.getLongitude());
                location.setRoute(entity); // Configurar a rota associada
                entity.getTrajectory().add(location);
            });
        }
    }
}
