package com.example.rms.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getModules(){return moduleRepository.findAll();}

    public List<Module> getModulesByCourse(String course) {return moduleRepository.findModulesByCourse(course);}

    public Optional<Module> getModule(Long moduleId) {
        boolean moduleExists = moduleRepository.existsById(moduleId);
        if(!moduleExists) {
            throw new IllegalStateException("module does not exist");
        }

        return moduleRepository.findById(moduleId);
    }

    public void addNewModule(Module module) {
        Optional<Module> moduleByTitle = moduleRepository.findModuleByTitle(module.getTitle());
        if(moduleByTitle.isPresent()){
            throw new IllegalStateException("Module already exists");
        }
        moduleRepository.save(module);
    }

    public void deleteModule(Long moduleId) {
        boolean moduleExists = moduleRepository.existsById(moduleId);
        if(!moduleExists) {
            throw new IllegalStateException("module does not exist");
        }
        moduleRepository.deleteById(moduleId);
    }

    @Transactional
    public void updateModule(Long moduleId, String title, String course, String moduleContentLink, String startDate){
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalStateException("module does not exist"));

        if(title != null && title.length() > 0 && !Objects.equals(module.getTitle(), title)){
            module.setTitle(title);
        }

        if(course != null && course.length() > 0 && !Objects.equals(module.getCourse(), course)){
            module.setCourse(course);
        }

        if(moduleContentLink != null && moduleContentLink.length() > 0 && !Objects.equals(module.getModuleContentLink(), moduleContentLink)){
            module.setModuleContentLink(moduleContentLink);
        }

        if(startDate != null && !Objects.equals(module.getStartDate(), startDate)){
            module.setStartDate(LocalDate.parse(startDate));
        }
    }
}
