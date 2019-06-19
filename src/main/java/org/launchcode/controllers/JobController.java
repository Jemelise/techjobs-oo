package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam("id") int id) {

            Job singleJob = jobData.findById(id);
            model.addAttribute("singlejob", singleJob);
            return "job-detail";
        }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if(errors.hasErrors()) {
            return "new-job";
        }

        String name = jobForm.getName();

        int employId = jobForm.getEmployerId();
        Employer employer = jobData.getEmployers().findById(employId);

        int locId = jobForm.getLocationId();
        Location location = jobData.getLocations().findById(locId);

        int posId = jobForm.getPositionTypeId();
        PositionType positionType = jobData.getPositionTypes().findById(posId);

        int coreId = jobForm.getCoreCompetencyId();
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(coreId);

        Job shinyJob = new Job(name, employer, location, positionType, coreCompetency);
        jobData.add(shinyJob);
        return "redirect:/job/?id=" + shinyJob.getId();
    }
}
