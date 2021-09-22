package com.demo.emt.shareridecatalog.xport.rest;

import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import com.demo.emt.shareridecatalog.service.forms.PostForm;
import com.demo.emt.shareridecatalog.service.forms.SiteUserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/register")
public class RegisterController {
    //registracija na korisnik
    private final SiteUserService siteUserService;

    public RegisterController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }
    @GetMapping
    public ResponseEntity getRegisterPage(){
        return ResponseEntity.ok().build();
    }
    // ne sum sigurna deka ova ke treba
    @PostMapping("/addnewuser")
    public ResponseEntity<SiteUser> save(@RequestBody SiteUserForm siteUserForm) {
        //map ne mi raboti zatoa vaka
        SiteUser siteUser= this.siteUserService.register(siteUserForm);
        if (siteUser != null){
            return ResponseEntity.ok().body(siteUser);
        }else {
            return  ResponseEntity.badRequest().build();
        }
//        return this.siteUserService.register(siteUserForm)
//                .map(user -> ResponseEntity.ok().body(user))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
