package com.ravi.ipldashboard.controller;

import com.ravi.ipldashboard.model.Team;
import com.ravi.ipldashboard.repository.MatchRepository;
import com.ravi.ipldashboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;
    @Autowired
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository= matchRepository;
    }
    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName,5));
        return team;
    }


}
