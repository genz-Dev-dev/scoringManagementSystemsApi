package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.payload.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.payload.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.PaginationRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPasswordRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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
    public ResponseEntity<MultipleResponse<UserResponse>> getAll(PaginationRequest request) {
        Page<UserResponse> responses = authService.findAll(request.toPageable());
        return ResponseEntity.ok(MultipleResponse
                .success("Retrieve all user with pagination.",
                        responses));
    }

    @GetMapping("/is-authenticated")
    public boolean isAuthenticated(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return authentication.isAuthenticated();
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<SingleResponse<Void>> updateStatus(@PathVariable UUID id, @RequestParam String status) {
        authService.updateStatus(id, status);
        return ResponseEntity.ok(SingleResponse.success("Update status successfully.", null));
    }

}
