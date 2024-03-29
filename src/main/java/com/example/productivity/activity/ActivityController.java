package com.example.productivity.activity;

import com.example.productivity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// This is a controller
@RestController
// The base url is...
@RequestMapping("api/v1/activities")
@CrossOrigin
public class ActivityController {

    // The dependencies that were injected:
    @Autowired
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    // Constructor created for injected dependencies
    public ActivityController(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    // Get all statuses
    @GetMapping
    public List<Activity> getAllStatuses() {
        return activityRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<Activity> getOneStatus(@PathVariable long id) {
        System.out.println("I'm running");
        return activityRepository.findByUserId(id);
    }

    // Add one status
    @PostMapping("/user/{userId}")
    public List<Activity> addOneStatus(@PathVariable long userId, @RequestBody Activity activity) {
        // Go find a user with the id path variable
        userRepository.findById(userId).map(user -> {
            // Set the created_at field

            // Set the user to be the user that was found with findById
            activity.setUser(user);
            // Save the status with the updated fields
            return activityRepository.save(activity);
        });
        return activityRepository.findByUserId(userId);
    }

    // Update existing status
    // Make sure to send ENTIRE status, not just pieces of it
    @PatchMapping
    public Activity updateOneStatus(@RequestBody Activity updatedActivity) {
        return activityRepository.save(updatedActivity);
    }

    // Delete a status with the status id
    @DeleteMapping("/{id}")
    public Activity deleteOneStatus(@PathVariable long id) {
        Activity removedStatus = activityRepository.findById(id).get();
        activityRepository.deleteById(id);
        return removedStatus;
    }

}


