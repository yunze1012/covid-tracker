package com.personal.covidtracker.controller;

import com.personal.covidtracker.model.Statistics;
import com.personal.covidtracker.service.DeathsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeathsController {

    @Autowired
    DeathsService deathsService;

    @GetMapping("/deaths")
    public String deaths(Model model) {
        List<Statistics> stats = deathsService.getCurrentStats();
        int totalCasesWW = stats.stream().mapToInt(stat -> stat.getCases()).sum();
        int totalDiffWW = stats.stream().mapToInt(stat -> stat.getDailyDelta()).sum();
        model.addAttribute("stats", stats);
        model.addAttribute("totalCasesWW", totalCasesWW);
        model.addAttribute("totalDiffWW", totalDiffWW);
        model.addAttribute("time", deathsService.getCurrentTime());
        return "deaths";
    }
}
