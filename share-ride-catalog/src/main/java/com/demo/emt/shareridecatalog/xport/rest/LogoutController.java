package com.demo.emt.shareridecatalog.xport.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/logout")
public class LogoutController {
    //za logout na korisnik
    @GetMapping
    public String logoutUser(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
