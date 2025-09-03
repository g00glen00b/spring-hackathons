package codes.dimitri.apps.chat.room.web;

import codes.dimitri.apps.chat.room.Room;
import codes.dimitri.apps.chat.room.usecase.GetRoomInfo;
import codes.dimitri.apps.chat.room.usecase.ListRooms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final ListRooms listRooms;
    private final GetRoomInfo getRoomInfo;

    @GetMapping
    public String redirectToFirstRoom() {
        Room room = listRooms.execute().getFirst();
        return "redirect:/room/" + room.getId();
    }

    @GetMapping("/{id}")
    public String showRoom(@PathVariable UUID id, Model model) {
        Room room = getRoomInfo.execute(new GetRoomInfo.Parameters(id));
        model.addAttribute("room", room);
        return "room";
    }

    @GetMapping("/fragment/list")
    public String rooms(@RequestParam UUID active, Model model) {
        List<RoomResponse> rooms = listRooms.execute()
            .stream()
            .map(room -> RoomResponse.from(room, active))
            .toList();
        model.addAttribute("rooms", rooms);
        return "fragments/room-list";
    }
}
