package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.dto.response.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPasswordRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @Operation(summary = "Send reset password otp for changing password.")
    @PostMapping("/send-otp")
    public ResponseEntity<SingleResponse<Map<String , Object>>> sendOtp(@RequestBody Map<String , String> email) {
        var response = authService.sendOtpResetPassword(email.get("email"));
        return ResponseEntity.ok(SingleResponse.success("Password reset email sent. Check your inbox.", response));
    }

    @Operation(summary = "Verify 6 digit number from email for changing new password.")
    @PostMapping("/verify-otp")
    public ResponseEntity<SingleResponse<Map<String , Object>>> verifyOtp(HttpServletRequest servletRequest,@Valid @RequestBody VerifyOtpRequest request) {
        String jwtToken = servletRequest.getHeader("Authorization");
        String token = jwtToken.substring("Bearer ".length());
        var response = authService.verifyOtpResetPassword(token ,request);
        return ResponseEntity.ok(SingleResponse.success("Reset Password successful!", response));
    }

    @Operation(summary = "Update new password before verify opt from email.")
    @PostMapping("/reset-password")
    public ResponseEntity<SingleResponse<UserResponse>> resetPassword(HttpServletRequest request,@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        String jwtToken =  request.getHeader("Authorization");
        String token = jwtToken.substring("Bearer ".length());
        var response = authService.resetPassword(token , resetPasswordRequest);
        return ResponseEntity.ok(SingleResponse.success("Reset Password successful!", response));
    }

    @Operation(summary = "Delete user account with UUID.")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID uuid) {
        authService.delete(uuid);
        return ResponseEntity.ok(SingleResponse.success("Delete account successfully.", null));
    }

    @Operation(summary = "Retrieve all user with verify email.")
    @GetMapping
    public ResponseEntity<MultipleResponse<UserResponse>> findAll(
            @RequestParam(defaultValue = "1") int number ,@RequestParam(defaultValue = "10") int size ,
            @RequestParam(defaultValue = "ASC") String sort ,
            @RequestParam(defaultValue = "id") String property
    ) {
        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(number - 1 , size , Sort.by(direction , property.equalsIgnoreCase("id") ? "id" : property));
        Page<UserResponse> responses = authService.findAll(pageable);
        return ResponseEntity.ok().body(MultipleResponse.success("Retrieve all user with pagination.", responses));
    }

}
