package codes.dimitri.apps.moviereservation.theatre.web;

import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import codes.dimitri.apps.moviereservation.theatre.usecase.GetTheatreInfo;
import codes.dimitri.apps.moviereservation.theatre.usecase.GetTheatreInfoParameters;
import codes.dimitri.apps.moviereservation.theatre.usecase.ListAllTheatres;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/theatre")
@RequiredArgsConstructor
public class TheatreController {
    private final GetTheatreInfo theatreInfo;
    private final ListAllTheatres listAllTheatres;

    @GetMapping("/{id}")
    public String showTheatre(@PathVariable UUID id, Model model) {
        var parameters = new GetTheatreInfoParameters(new TheatreId(id));
        model.addAttribute("theatre", TheatreInfoResponse.of(theatreInfo.execute(parameters)));
        return "fragments/theatre";
    }

    @GetMapping("/checklist")
    public String checklist(Model model) {
        List<TheatreInfoResponse> theatres = listAllTheatres.execute().stream().map(TheatreInfoResponse::of).toList();
        model.addAttribute("theatres", theatres);
        return "fragments/theatre-checklist";
    }
}
