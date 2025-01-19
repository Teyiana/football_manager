package org.zayarnyuk.football_manager.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PlayerDTO {

    private long playerId;
    private String playerName;
    private Long teamId;
    private Date dateOfBirth;
    private Date StartWorkDate;

}
