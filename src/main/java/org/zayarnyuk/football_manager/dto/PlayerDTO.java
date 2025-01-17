package org.zayarnyuk.football_manager.dto;



import lombok.Data;

import java.sql.Date;

@Data
public class PlayerDTO {

    private long playerId;
    private String playerName;
    private Long teamId;
    private Date dateOfBirth;
    private Date StartWorkDate;

}
