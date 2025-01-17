package org.zayarnyuk.football_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "start_work_date")
    private Date startWorkDate;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


}
