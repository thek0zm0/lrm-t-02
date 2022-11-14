package hfoods.demo.tests;

import hfoods.demo.dto.InformationDTO;
import hfoods.demo.entities.Information;
import hfoods.demo.entities.enums.ActivityStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Helper {

    public static Information umaInformation(Long id) {
        return Information.builder()
                .id(id)
                .sex('M')
                .activityStatus(ActivityStatus.MEDIUM)
                .age(20)
                .createdDate(LocalDate.now().minusMonths(6).toString())
                .weight(90.0)
                .height(1.80)
                .build();
    }

    public static InformationDTO umaInformationDTO(Long id) {
        return InformationDTO.builder()
                .id(id)
                .sex('M')
                .activityStatus(ActivityStatus.MEDIUM)
                .age(20)
                .createdDate(LocalDate.now().minusMonths(6).toString())
                .weight(90.0)
                .height(1.80)
                .build();
    }

}
