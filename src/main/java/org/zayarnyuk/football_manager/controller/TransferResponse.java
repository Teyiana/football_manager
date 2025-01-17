package org.zayarnyuk.football_manager.controller;


import lombok.Data;

@Data
public class TransferResponse {
    private Error error;

    private long transferId;

    public enum Error {
        OK,
        INVALID_DATE_OF_BIRTH,
        INVALID_PLAYER_NAME,
        INVALID_PLAYER_EXPERIENCE_MONTHS
    }

    public static TransferResponse success(long transferId) {
        TransferResponse response = new TransferResponse();
        response.error = Error.OK;
        response.transferId = transferId;
        return response;
    }


    public static TransferResponse failed(Error error) {
        TransferResponse response = new TransferResponse();
        response.error = error;
        return response;
    }


//    public static TransferResponse invalidDateOfBirth() {
//        TransferResponse response = new TransferResponse();
//        response.error = Error.INVALID_DATE_OF_BIRTH;
//        return response;
//    }
//
//    public static TransferResponse invalidPlayerName() {
//        TransferResponse response = new TransferResponse();
//        response.error = Error.INVALID_PLAYER_NAME;
//        return response;
//    }


}
