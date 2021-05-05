package com.example.rms.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/module")
public class ModuleController {
    private ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    @CrossOrigin
    public List<Module> getModules() { return moduleService.getModules();}

    @GetMapping(path = "{moduleId}")
    @CrossOrigin
    public Optional<Module> getModule(@PathVariable("moduleId") Long moduleId){ return moduleService.getModule(moduleId);}

    @GetMapping(path = "/course/{course}")
    @CrossOrigin
    public List<Module> getModulesByCourse( @PathVariable("course") String course){ return moduleService.getModulesByCourse(course);}

    @PostMapping
    @CrossOrigin
    public void addNewModule(@RequestBody Module module)
    {
        moduleService.addNewModule(module);
    }

    @DeleteMapping(path = "{moduleId}")
    @CrossOrigin
    public void deleteModule(@PathVariable("moduleId") Long moduleId) {
        moduleService.deleteModule(moduleId);
    }

    @PutMapping(path = "{moduleId}")
    @CrossOrigin
    public void updateModule(
            @PathVariable("moduleId") Long moduleId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String moduleContentLink,
            @RequestParam(required = false) String startDate)
    {
        moduleService.updateModule(moduleId, title, course, moduleContentLink, startDate);
    }
}
