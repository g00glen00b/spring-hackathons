package codes.dimitri.apps.moviereservation.user.web;

import codes.dimitri.apps.moviereservation.user.usecase.RegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final RegisterUser registerUser;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("request", RegisterUserRequest.blank());
        model.addAttribute("success", false);
        model.addAttribute("error", null);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterUserRequest request, ModelMap model) {
        model.addAttribute("request", request);
        model.addAttribute("success", false);
        model.addAttribute("error", null);
        try {
            registerUser.execute(request.toParameters());
            model.addAttribute("success", true);
        } catch (Throwable ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "register";
    }
}
