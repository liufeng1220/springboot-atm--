package co.fengfeng.controller;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@Api(value = "用户登陆controller", tags = {"用户登陆接口"})
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 校验卡号是否存在
     *
     * @param cardId
     * @param session
     * @return
     */
    @ApiOperation(value = "校验卡号是否正确", notes = "根据卡号验证账户是否存在", httpMethod = "POST")
    @PostMapping("/getCarIdInfo")
    public ResponseEntity<AjaxRes> getCarIdInfo(String cardId, HttpSession session) {
        AjaxRes ajaxRes = loginService.getUserInfo(cardId, session);
        return ResponseEntity.ok(ajaxRes);
    }

    /**
     * 验证银行卡信息是否正确
     *
     * @param cardId   卡号
     * @param password 密码
     * @return
     */
    @ApiOperation(value = "校验卡号信息密码是否正确", notes = "根据账号密码验证账号是否存在", httpMethod = "POST")
    @PostMapping("/userLogin")
    public ResponseEntity<AjaxRes> userLogin(String cardId, String password, HttpSession session) {
        AjaxRes ajaxRes = loginService.getCardInfo(cardId, password);
        session.setAttribute("loginUser", ajaxRes.getObject());
        return ResponseEntity.ok(ajaxRes);
    }

}
