package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OwnerMapper {

    public OwnerDto entityToDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getCreatedAt(), owner.getUpdatedAt());
    }

    public Owner dtoToEntity(OwnerDto ownerDto){
        return new Owner(ownerDto.getId(),ownerDto.getName());
    }

    public List<OwnerDto> entityToDtoList(List<Owner> owners){
        List<OwnerDto> result = new LinkedList<OwnerDto>();
        for (Owner h : owners) {
            result.add(this.entityToDto(h));
        }
        return result;
    }
}


