package org.zayarnyuk.football_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Player> players;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "commission_rate")
    private double commissionRate;

}
