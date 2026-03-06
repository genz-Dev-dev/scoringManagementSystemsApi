package com.rupp.tola.dev.scoring_management_system.exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

//	private final HttpStatus httpStatus;
//	private final String message;
//	private final String timeStamp;
//	private final int status;
	
	private final int status;
    private final String message;
    private final LocalDateTime timestamp;

}
