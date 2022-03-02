package am.task.controllers;

import am.task.model.ResponseModel;
import am.task.services.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("user/")
@Api(value = "user")
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "API for admin to block/unblock user")
    @PostMapping("blockUnblock")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseModel blockUnblock(@RequestParam("userId") Long userId
            , @ApiParam("status must be '1' for ACTIVE, '2' for BLOCK ") @RequestParam("status") int status) {
        try {
            return createResult(userService.changeStatus(userId, status), "User status changed successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }
}