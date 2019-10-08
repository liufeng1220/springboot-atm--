package co.fengfeng.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AtmController {
    @PostMapping("/transferDetails")
    public String transferDetails(String cardId, String username, Model model,HttpSession session) {
        session.setAttribute("transCardId", cardId);
        model.addAttribute("username", username);
        return "transfer_userInfo";
    }
    @GetMapping("/transgerMoney")
    public String transgerMoney() {
        return "transfer_money";
    }

    @PostMapping("/againPassword")
    public String againPassword(String password, Model model) {
        model.addAttribute("newPassword", password);
        return "again_password";
    }

    /**
     * 所有需要输入密码的业务都会来这里
     *
     * @param str
     * @param model
     * @return
     */
    @GetMapping("/password/{str}")
    public String forwardPassword(@PathVariable("str") String str, Model model) {
        if (StringUtils.isNotBlank(str)) {
            model.addAttribute("str", str);
        }
        return "login_password";
    }
    @GetMapping("/exit")
    public String exit() {
        return "exit";
    }


    @GetMapping("/saveMoney")
    public String saveMoney() {
        return "save_money";
    }

    @GetMapping("/transferAccount")
    public String transferAccount() {
        return "transfer_accounts";
    }

    @GetMapping("/printed")
    public String printed() {
        return "slip_printed";
    }

    @GetMapping("/receipt")
    public String receipt() {
        return "receipt";
    }

    @GetMapping("/payee")
    public String payee() {
        return "out_money";
    }

    @GetMapping("/getAccount")
    public String getAccount() {
        return "account";
    }

    @GetMapping("/load")
    public String getLoad() {
        return "load";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("newPassword")
    public String newPassword() {
        return "password_new";
    }

    @GetMapping("/checkMoney")
    public String checkMoney() {
        return "check_money";
    }

    @GetMapping("/withdrawal")
    public String getWithdrawal() {
        return "withdraw";
    }

    @GetMapping("/otherMoney")
    public String otherMoney() {
        return "other_money";
    }
}
