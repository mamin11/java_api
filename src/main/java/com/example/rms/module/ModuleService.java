package com.example.rms.module;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.mediaconvert.model.S3ObjectCannedAcl;
import com.amazonaws.services.medialive.model.S3CannedAcl;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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

    public Optional<Module> getModuleByTitle(String title) {
        return moduleRepository.findModuleByTitle(title);
    }

    public void addNewModule(Module module) {
        Optional<Module> moduleByTitle = moduleRepository.findModuleByTitle(module.getTitle());
        if(moduleByTitle.isPresent()){
            throw new IllegalStateException("Module already exists");
        }
        moduleRepository.save(module);
    }

//    @Transactional
    public void deleteModule(Long moduleId) {
//        boolean moduleExists = moduleRepository.existsById(moduleId);
//        if(!moduleExists) {
//            throw new IllegalStateException("module does not exist");
//        }
        //delete content from s3
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalStateException("module does not exist"));

        String bucketName = BucketName.PROFILE_IMAGE.getBucketName();
        String key = String.format("%s/%s",module.getCourse(), module.getModuleContentLink().get());

        if((module.getModuleContentLink().get()).equals("")) {
            //just delete the module
            moduleRepository.deleteById(moduleId);

        } else {
            //delete module and its content
            storage.delete(bucketName, key);
            moduleRepository.deleteById(moduleId);
        }

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

        if(!Objects.equals(module.getModuleContentLink(), moduleContentLink)){
            module.setModuleContentLink(moduleContentLink);
        }

        if(startDate != null && !Objects.equals(module.getStartDate(), startDate)){
            module.setStartDate(LocalDate.parse(startDate));
        }
    }

    @Transactional
     public void uploadModuleContent(Long moduleId, MultipartFile file) {
        //check if the file is not empty
        if (file.isEmpty()) { throw new  IllegalStateException("No file added");}

        //check whether the module exists
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalStateException("Module does not exist"));

        //prepare metadata
         Map<String, String> metadata = new HashMap<>();
         metadata.put("Content-Type", file.getContentType());
         metadata.put("Content-Length", String.valueOf(file.getSize()));
//         metadata.put("CannedACL", String.valueOf(S3ObjectCannedAcl.PUBLIC_READ));
//        S3CannedAcl.PUBLIC_READ

         //store the file
         //create a path depending on the module course, so that all of a course's content is in the same bucket
         String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), module.getCourse());
         //create a filename from original filename and random UUID
         String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

         try {
             storage.save(path, filename, Optional.of(metadata), file.getInputStream());

             //update the module link after upload
             module.setModuleContentLink(filename);
             moduleRepository.saveAndFlush(module);

             System.out.println("AFTER UPDATE *******" + module.getModuleContentLink());
             System.out.println("FILENAME *******" + filename);
         } catch (IOException e) {
             throw new IllegalStateException("error", e);
         }
     }

    S3ObjectInputStream downloadModuleContent(Long moduleId) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalStateException("module does not exist"));
        if(module.getModuleContentLink().equals("")) {throw new IllegalStateException("Module does not have content");}
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), module.getCourse());
        return module.getModuleContentLink().map(key -> storage.download(path, key)).orElse(null);
    }

//    public void deleteModuleFile(String path, String key) {
//        storage.delete(path, key);
//    }
}
