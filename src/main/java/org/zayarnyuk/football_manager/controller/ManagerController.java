package org.zayarnyuk.football_manager.controller;

import org.springframework.web.bind.annotation.*;
import org.zayarnyuk.football_manager.dto.PlayerDTO;
import org.zayarnyuk.football_manager.dto.TeamDTO;
import org.zayarnyuk.football_manager.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping(value = "/transfer")
    @ResponseBody
    public void create(@RequestBody PlayerDTO playerDTO, TeamDTO team) {
        managerService.transferPlayer(playerDTO.getPlayerId(), team.getId());
    }


}
