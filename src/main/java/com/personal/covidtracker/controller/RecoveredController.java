package com.personal.covidtracker.controller;

import com.personal.covidtracker.model.Statistics;
import com.personal.covidtracker.service.RecoveredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecoveredController {
    @Autowired
    RecoveredService recoveredService;

    @GetMapping("/recovered")
    public String deaths(Model model) {
        List<Statistics> stats = recoveredService.getCurrentStats();
        int totalCasesWW = stats.stream().mapToInt(stat -> stat.getCases()).sum();
        int totalDiffWW = stats.stream().mapToInt(stat -> stat.getDailyDelta()).sum();
        model.addAttribute("stats", stats);
        model.addAttribute("totalCasesWW", totalCasesWW);
        model.addAttribute("totalDiffWW", totalDiffWW);
        model.addAttribute("time", recoveredService.getCurrentTime());
        return "recovered";
    }
}
