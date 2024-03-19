package com.ravi.ipldashboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Match {
    @Id
    private Long Id;
    private String city;
    private LocalDate date;
    private String season;
    private String matchNumber;
    private String team1;
    private String team2;
    private String venue;
    private String tossWinner;
    private String tossDecision;
    private String superOver;
    private String winningTeam;
    private String wonBy;
    private String margin;
    private String playerOfMatch;
    private String team1Players;
    private String team2Players;
    private String umpire1;
    private String umpire2;

}