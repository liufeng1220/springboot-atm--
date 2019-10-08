package co.fengfeng.controller;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.service.PasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "密码管理", tags = {"密码操作接口"})
public class PasswordController {
    @Autowired
    private PasswordService passwordService;

    @ApiOperation(value = "检查用户信息", notes = "修改密码，验证用户输入的原密码是否正确", httpMethod = "POST")
    @PostMapping("/checkPassword")
    public ResponseEntity<AjaxRes> checkPassword(String cardId, String password) {
        AjaxRes ajax = passwordService.checkPassword(cardId, password);
        return ResponseEntity.ok(ajax);
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<AjaxRes> updatePassword(String cardId, String password) {
        AjaxRes ajax = passwordService.updatePassword(cardId,password);
        return ResponseEntity.ok(ajax);
    }
}
