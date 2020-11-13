package es.codeurjc.arpj.mvcapi.infrastructure.http;

import es.codeurjc.arpj.mvcapi.application.create.UserCreator;
import es.codeurjc.arpj.mvcapi.application.create.UserCreatorCommand;
import es.codeurjc.arpj.mvcapi.application.find.UserFinder;
import es.codeurjc.arpj.mvcapi.application.find.UserFinderQuery;
import es.codeurjc.arpj.mvcapi.application.find.UserFinderResponse;
import es.codeurjc.arpj.mvcapi.application.remove.UserRemover;
import es.codeurjc.arpj.mvcapi.application.remove.UserRemoverCommand;
import es.codeurjc.arpj.mvcapi.application.update.UserUpdater;
import es.codeurjc.arpj.mvcapi.application.update.UserUpdaterCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCreator userCreator;
    private final UserFinder userFinder;
    private final UserRemover userRemover;
    private final UserUpdater userUpdater;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        final List<UserResponse> users = userFinder.findAll()
                .stream()
                .map(u -> new UserResponse(u.id(), u.name(), u.surname(), u.email()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable final String id) {
        final UserFinderResponse response = userFinder.findById(new UserFinderQuery(id));
        if (response != null) {
            return ResponseEntity.ok().body(new UserResponse(response.id(), response.name(), response.surname(),
                    response.email()));
        } else {
            log.info("User not found!");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody final UserRequest req) {
        userCreator.create(new UserCreatorCommand(req.getId(), req.getName(), req.getSurname(), req.getEmail()));
        log.info("User created!");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> putUser(@RequestBody final UserRequest request) {
        userUpdater.update(new UserUpdaterCommand(request.getId(), request.getName(), request.getSurname(),
                request.getEmail()));
        log.info("User updated!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String id) {
        userRemover.remove(new UserRemoverCommand(id));
        log.info("User deleted!");
        return ResponseEntity.noContent().build();
    }
}