package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable Integer id) {

        return ResponseEntity.ok("Utilisateur trouvé avec l'ID : " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserRequest request
    ) {

        return ResponseEntity.ok("Utilisateur mis à jour avec succès.");
    }
}
