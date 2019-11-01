package com.qv.learn.controller;

import com.qv.learn.service.ImageService;
import com.qv.learn.validator.LocationValidator;
import org.jqurantree.orthography.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ImageService imageService;

    @GetMapping( "/home" )
    public String home(){

        return "home";
    }

    @PostMapping( "/home" )
    public String postHome(

            @RequestParam( "chapter" ) Integer chapter,
            @RequestParam( "verse" ) Integer verse,
            @RequestParam( value = "token", required = false ) Integer token,
            Model model
    ){

        try {
            
            model.addAttribute( "chapter", chapter );
            model.addAttribute( "verse", verse );
            model.addAttribute( "token", token );
            LocationValidator.validateLocation(new Location(chapter, verse, token));
        }
        catch (Exception e ){

            model.addAttribute( "error", e.getMessage() );
            return "/home";
        }

        if( token == null ) token = 0;
        Location location = new Location( chapter, verse, token );

        List<Integer> tokenNumbers = imageService.getTokenNumber( location );

        model.addAttribute( "tokenNumbers", tokenNumbers );

        return "home";
    }
}
