package com.personal.covidtracker.controller;

import com.personal.covidtracker.model.Statistics;
import com.personal.covidtracker.service.CasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CasesController {

    @Autowired
    CasesService casesService;

    @GetMapping("/")
    public String cases(Model model) {
        List<Statistics> stats = casesService.getCurrentStats();
        int totalCasesWW = stats.stream().mapToInt(stat -> stat.getCases()).sum();
        int totalDiffWW = stats.stream().mapToInt(stat -> stat.getDailyDelta()).sum();
        model.addAttribute("stats", stats);
        model.addAttribute("totalCasesWW", totalCasesWW);
        model.addAttribute("totalDiffWW", totalDiffWW);
        model.addAttribute("time", casesService.getCurrentTime());
        return "cases";
    }
}
