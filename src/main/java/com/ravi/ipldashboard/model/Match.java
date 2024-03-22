package com.ravi.ipldashboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Match {
    @Id
    private Long ID;
    private String City;
    private LocalDate Date;
    private String Season;
    private String MatchNumber;
    private String Team1;
    private String Team2;
    private String Venue;
    private String TossWinner;
    private String TossDecision;
    private String SuperOver;
    private String WinningTeam;
    private String WonBy;
    private String Margin;
    private String Player_of_Match;
    private String Team1Players;
    private String Team2Players;
    private String Umpire1;
    private String Umpire2;

}