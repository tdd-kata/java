package org.xpdojo.springbootvue.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.xpdojo.springbootvue.domain.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final ObjectMapper objectMapper;

    public HomeController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String index(Model model) throws JsonProcessingException {
        model.addAttribute("eventName", "FIFA 2018");

        if (true) {
            Map<String, Object> player1 = new HashMap<>();
            player1.put("id", 1);
            player1.put("name", "Lionel Messi");
            player1.put("description", "Argentina's superstar");

            Map<String, Object> player2 = new HashMap<>();
            player2.put("id", 2);
            player2.put("name", "Christiano Ronaldo");
            player2.put("description", "Portugal top-ranked liar");

            List<Map<String, Object>> players = new ArrayList<>();
            players.add(player1);
            players.add(player2);

            // [{name=Lionel Messi, description=Argentina&#39;s superstar, id=1}, {name=Christiano Ronaldo, description=Portugal top-ranked liar, id=2}]
            model.addAttribute("players", objectMapper.writeValueAsString(players));
        } else {
            Player playerA = new Player(1L, "a", "aaa");
            Player playerB = new Player(2L, "b", "bbb");

            List<Player> players = new ArrayList<>();
            players.add(playerA);
            players.add(playerB);

            // [{id: 1, name: "a", description: 'aaa'}, {id: 2, name: "b", description: 'bbb'}]
            model.addAttribute("players", objectMapper.writeValueAsString(players));
        }

        return "index";
    }
}
