package org.zayarnyuk.football_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zayarnyuk.football_manager.dto.TransferRequest;
import org.zayarnyuk.football_manager.dto.TransferResponse;
import org.zayarnyuk.football_manager.service.ManagerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;


    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(value = "/transfer")
    @ResponseBody
    public TransferResponse transfer(@RequestBody TransferRequest transferRequest) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setTeamId(transferRequest.getTeamId());
        transferResponse.setPlayerId(transferRequest.getPlayerId());
        try {
            managerService.transferPlayer(transferRequest);
            transferResponse.setSuccess(true);
        } catch (Exception e) {
            transferResponse.setSuccess(false);
            transferResponse.setErrorMessage(e.getMessage());
        }
        return transferResponse;
    }


}
