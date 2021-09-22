package com.demo.emt.shareridecatalog.xport.rest;

import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/login")
public class LoginController {
    //rest kontroler za komuniciranje so react vo koj logiram i registriram korisnik
    private final SiteUserService siteUserService;
    public LoginController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }
    @GetMapping
    public ResponseEntity getLoginPage(){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users")
    public List<SiteUser> getUsers(){
        return siteUserService.findAll();
    }

    @PostMapping
    public ResponseEntity<SiteUser> login(@RequestBody SiteUser siteUser){
        SiteUser user =this.siteUserService.login(siteUser.getUsername(),siteUser.getPassword());
        if (user != null){
            return ResponseEntity.ok().body(user);
        }else {
            return  ResponseEntity.badRequest().build();
        }
//        return this.siteUserService.login(username,password)
//                .map(user -> ResponseEntity.ok().body(user))
//                .orElseGet(() -> ResponseEntity.badRequest().build());


    }

}
