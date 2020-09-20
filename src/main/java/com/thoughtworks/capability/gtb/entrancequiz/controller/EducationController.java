package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/{id}/educations")
    @ResponseStatus(HttpStatus.OK)
    public List<Education> getEducationByUserId(@PathVariable Long id) {
        return educationService.getEducationByUserId(id);
    }

    @PostMapping("/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public Education createEducation(@RequestBody @Valid Education education,
                                     @PathVariable Long id) {
        return educationService.createEducation(education,id);
    }
}
