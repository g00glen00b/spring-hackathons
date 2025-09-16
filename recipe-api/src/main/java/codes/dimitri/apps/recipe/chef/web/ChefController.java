package codes.dimitri.apps.recipe.chef.web;

import codes.dimitri.apps.recipe.chef.Chef;
import codes.dimitri.apps.recipe.chef.usecase.RegisterChef;
import codes.dimitri.apps.recipe.core.JwtUser;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
class ChefController {
    private final RegisterChef registerChef;

    @PostMapping
    public ChefInfoResponseDTO register(@RequestBody RegisterChefRequestDTO request) {
        RegisterChef.Parameters parameters = request.toParameters();
        Chef chef = registerChef.execute(parameters);
        return ChefInfoResponseDTO.of(chef);
    }

    @GetMapping("/token")
    public TokenResponseDTO generateToken(@AuthenticationPrincipal JwtUser user) {
        return new TokenResponseDTO(user.jwt());
    }

    @GetMapping
    public ChefInfoResponseDTO getChef(@AuthenticationPrincipal DecodedJWT jwt) {
        return ChefInfoResponseDTO.of(jwt);
    }
}
