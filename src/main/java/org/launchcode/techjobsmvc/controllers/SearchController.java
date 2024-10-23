package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        // replaces 'all' in th:value with 'checked' so that it still defaults to 'all'
        // but can be updated upon changing search type
        model.addAttribute("checked", "all");
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.


    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {

        ArrayList<Job> jobs;
        // Creating variables to store and capture input values for radio and text inputs
        // which are linked by the name attribute,
        // then using those values to dynamically change the title based on user input

        model.addAttribute("columns", columnChoices);
        model.addAttribute("searchType", searchType);
        // Here, if search type changes, it will be passed to 'checked' which will update in th:value
        model.addAttribute("checked", searchType);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("title", "jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        // If a search term exists, filter based on column, including "All"
        if (!searchTerm.isEmpty()) {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        // If search field is blank, there is nothing to filter jobs by, therefore no matter the column choice, all jobs will display
        else {
            jobs = JobData.findAll();
        }
        model.addAttribute("jobs", jobs);

        return "search";
    }
}

