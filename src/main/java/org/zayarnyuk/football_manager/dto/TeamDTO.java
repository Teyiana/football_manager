package org.zayarnyuk.football_manager.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
public class TeamDTO {

    private long Id;
    private String teamName;
    private BigDecimal balance;
    private double commissionRate;
    private List<PlayerDTO> players;
}
