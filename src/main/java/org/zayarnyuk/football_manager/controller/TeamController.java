package org.zayarnyuk.football_manager.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zayarnyuk.football_manager.dto.TeamDTO;
import org.zayarnyuk.football_manager.service.TeamService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;


    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public TeamDTO create(@RequestBody TeamDTO team) {
        return teamService.create(team);
    }


    @GetMapping("/readAll")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public List<TeamDTO> read() {
        final List<TeamDTO> teams = teamService.getAllTeams();
        return teams;
    }

    @GetMapping(value = "/read/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamDTO> read(@PathVariable(name = "id") long id) {
        final TeamDTO team = teamService.getTeamById(id);

        return team != null
                ? ResponseEntity.ok(team)
                : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/edit")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseBody
    public TeamDTO update( @RequestBody TeamDTO team) {
        return teamService.update(team);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        teamService.deleteTeamById(id);
        return ResponseEntity.ok().build();
    }



}
