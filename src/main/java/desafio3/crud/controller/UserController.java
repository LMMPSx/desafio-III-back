package desafio3.crud.controller;

import lombok.AllArgsConstructor;
import desafio3.crud.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import desafio3.crud.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {
    private final UserRepository repository;

    @GetMapping
    public List<UserModel> getAllUsers() {
        return repository.findAll();
    }

    @PostMapping
    public UserModel createUser(@RequestBody UserModel userModel) {
        return repository.save(userModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel userDetails) {
        return repository.findById(id)
                .map(userModel -> {
                    userModel.setName(userDetails.getName());
                    userModel.setEmail(userDetails.getEmail());
                    UserModel updatedUser = repository.save(userModel);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
