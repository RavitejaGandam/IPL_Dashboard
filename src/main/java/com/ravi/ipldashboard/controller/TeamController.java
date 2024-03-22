package com.ravi.ipldashboard.controller;

import com.ravi.ipldashboard.model.Team;
import com.ravi.ipldashboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private TeamRepository teamRepository;
    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        return this.teamRepository.findByTeamName(teamName);
    }


}
