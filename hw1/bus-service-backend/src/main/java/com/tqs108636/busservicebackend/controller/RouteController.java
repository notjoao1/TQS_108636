package com.tqs108636.busservicebackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tqs108636.busservicebackend.model.Route;

@RestController
@RequestMapping("api/routes")
public class RouteController {
    @GetMapping
    public ResponseEntity<List<Route>> getRoutes(@RequestParam(name = "source", required = false) String source) {
        return null;
    }
}
