package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getRating(), horse.getBirthDate(), horse.getRace(),
            horse.getPhoto(), horse.getOwnerId(), horse.getCreatedAt(), horse.getUpdatedAt());
    }

    public Horse dtoToEntity(HorseDto horseDto){
        return new Horse(horseDto.getId(),horseDto.getName(), horseDto.getDescription(), horseDto.getRating(), horseDto.getBirthDate(), horseDto.getRace(), horseDto.getPhoto(), horseDto.getOwnerId());
    }

    public List<HorseDto> entityToDtoList(List<Horse> horses){
        List<HorseDto> result = new LinkedList<HorseDto>();
        for (Horse h : horses) {
            result.add(this.entityToDto(h));
        }
        return result;
    }
}
