package run.serverstatus.app.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import oshi.util.Util;
import run.serverstatus.app.entities.properties.Account;
import run.serverstatus.app.service.Impl.IndexServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class IndexController {


    private final IndexServiceImpl indexService;
    private final ConfigurableApplicationContext context;


    public IndexController(IndexServiceImpl indexService,
                           ConfigurableApplicationContext context) {
        this.indexService = indexService;
        this.context = context;
    }

    @RequestMapping("/")
    public String index() {
        if (indexService.isFirstTimeSignIn()) {
            return "sign-up";
        } else {
            return "sign-in";
        }
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,
                        Map<String, Object> map, HttpSession session) {
        Account account = indexService.findAccountByUsernameAndPassword(username, password);
        if (account != null && account.getPassword().equals(password)) {
            String rememberMe = request.getParameter("rememberMe");
            if (rememberMe != null && rememberMe.equals("true")) {
                session.setMaxInactiveInterval(60 * 60 * 24 * 15);//15 days
            }
            session.setAttribute("account", account);
            return "redirect:/home";
        } else {
            map.put("msg", "Sorry ! Wrong user name or password.");
            return "sign-in";
        }
    }


    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String recipient) {
        log.info(username + "    " + password + "  " + recipient);
        if (!indexService.isFirstTimeSignIn()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("flag", false);
            map.put("msg","Sorry, Only one account is allowed！");
            return JSON.toJSONString(map);
        }
        Account account = context.getBean("account", Account.class);
        account.setUsername(username);
        account.setPassword(password);
        /* TODO  正则 recipient*/
        indexService.insertAccount(account);
        indexService.updateMailSettingsTo(recipient);
        //Send boot info email
        indexService.sendBootInfo();
        //return
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", true);
        return JSON.toJSONString(map);
    }
}
