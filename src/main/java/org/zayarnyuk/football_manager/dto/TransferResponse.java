package org.zayarnyuk.football_manager.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TransferResponse extends TransferRequest {
    private String errorMessage;
    private boolean success;

}
