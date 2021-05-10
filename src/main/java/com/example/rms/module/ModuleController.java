package com.example.rms.module;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/module")
@CrossOrigin(origins = "http://localhost:8080")
public class ModuleController {
    private ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<Module> getModules() { return moduleService.getModules();}

    @GetMapping(path = "{moduleId}")
    @CrossOrigin
    public Optional<Module> getModule(@PathVariable("moduleId") Long moduleId){ return moduleService.getModule(moduleId);}

    @GetMapping(path = "/course/{course}")
    @CrossOrigin
    public List<Module> getModulesByCourse( @PathVariable("course") String course){ return moduleService.getModulesByCourse(course);}

    @GetMapping(path = "title/{title}")
    public Optional<Module> getModuleByTitle(@PathVariable("title") String title) {return moduleService.getModuleByTitle(title);}

    @PostMapping
    public void addNewModule(@RequestBody Module module) { moduleService.addNewModule(module); }

    @DeleteMapping(path = "{moduleId}")
    public void deleteModule(@PathVariable("moduleId") Long moduleId) {
        moduleService.deleteModule(moduleId);
    }

    @PutMapping(path = "{moduleId}")
    public void updateModule(
            @PathVariable("moduleId") Long moduleId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String moduleContentLink,
            @RequestParam(required = false) String startDate)
    {
        moduleService.updateModule(moduleId, title, course, moduleContentLink, startDate);
    }

    @PostMapping(
            path = "{moduleId}/content/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadModuleContent(@PathVariable("moduleId") Long moduleId,
                                    @RequestParam("file") MultipartFile file) {
        moduleService.uploadModuleContent(moduleId, file);
    }

    @GetMapping(path = "{moduleId}/content/download")
    public S3ObjectInputStream downloadModuleContent(@PathVariable("moduleId") Long moduleId){
        return moduleService.downloadModuleContent(moduleId);
    }
}
