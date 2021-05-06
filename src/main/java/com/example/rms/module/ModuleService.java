package com.example.rms.module;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.rms.bucket.BucketName;
import com.example.rms.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final Storage storage;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, Storage storage) {
        this.moduleRepository = moduleRepository;
        this.storage = storage;
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

     void uploadModuleContent(Long moduleId, MultipartFile file) {
        //check if the file is not empty
        if (file.isEmpty()) { throw new  IllegalStateException("No file added");}

        //check whether the module exists
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalStateException("Module does not exist"));

        //prepare metadata
         Map<String, String> metadata = new HashMap<>();
         metadata.put("Content-Type", file.getContentType());
         metadata.put("Content-Length", String.valueOf(file.getSize()));

//         ObjectMetadata objectMetadata = new ObjectMetadata();
//         objectMetadata.setContentType(file.getContentType());
//         objectMetadata.setContentLength(file.getSize());

         //store the file
         //create a path depending on the module course, so that all of a course's content is in the same bucket
         String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), module.getCourse());
         //create a filename from original filename and random UUID
         String filename = String.format("%s-%s", file.getName(), UUID.randomUUID());

         try {
             storage.save(path, filename, Optional.of(metadata), file.getInputStream());
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
