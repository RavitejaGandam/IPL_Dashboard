package com.ravi.ipldashboard.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MatchInput {
        private Long ID;
        private String City;
        private String Date;
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
