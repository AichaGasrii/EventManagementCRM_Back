package com.example.gestionauth.rest;

import com.example.gestionauth.persistence.entity.*;
import com.example.gestionauth.persistence.enumeration.Domaines;
import com.example.gestionauth.repositories.UserRepository;
import com.example.gestionauth.services.Implementation.EmailServiceImpl;
import com.example.gestionauth.services.Implementation.UserService;
import com.example.gestionauth.services.Implementation.VerificationTokenService;
import com.example.gestionauth.services.Interface.NotificationService;
import com.example.gestionauth.util.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userDao;
    @Autowired
    private EmailServiceImpl emailServ;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenService verificationTokenService;


    @Autowired
    NotificationService notificationService;


    @GetMapping({"/hello"})
    public String hello() {
        return ("hello auth service");
    }

    @GetMapping({"/users"})
    public List<User> getAll() {
        return userService.getAll();
    }

    // Get All Users
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get All Organizers
    @GetMapping("/getAllOrganizers")
    public List<User> getAllMembers() {
        return userService.getAllMembers();
    }

    @GetMapping("/byRoleName/{roleName}")
    public List<User> getUsersByRoleName(@PathVariable String roleName) {
        return userService.getUsersByRoleName(roleName);
    }


    @GetMapping({"/user/{userName}"})
    public User findOne(@PathVariable String userName) {
        return userService.findOne(userName);
    }

    @PutMapping("/user/updateAvecImage/{username}")
    public ResponseEntity<User> updateAvecImage(
            @PathVariable("username") String userName,
            @RequestParam("userFirstName") String userFirstName,
            @RequestParam("userLastName") String userLastName,
            @RequestParam("number") String number,
            @RequestParam("image") MultipartFile image,
            @RequestParam("domaines") Domaines domaines,
            @RequestParam("siteWeb") String siteWeb,
            @RequestParam("aboutMe") String aboutMe,
            @RequestParam("location") String location) {

        // Call the service to update the user with the new details and image
        User updatedUser = userService.UpdateAvecImage(userName, userFirstName, userLastName, number, image, domaines, siteWeb, aboutMe, location);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.retrieve(username);
    }

    @PutMapping("/updateUser/{username}")
    public User updateUser(@PathVariable String username , @RequestBody User userDetails) {

            return userService.updateUser(username,userDetails);

    }

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@Valid @RequestBody User user) {
        User savedUser = userService.registerNewUser(user);
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
        verificationTokenService.saveVerificationToken(verificationToken);
        // Logging whether a user was found
        notificationService.notificationOnRegistration(user);

        return savedUser;
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "This URL is only accessible to the admin";
    }


    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }

    @DeleteMapping({"/delete/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable String userName) {
        userService.delete(userName);
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('Admin')")
    public long count() {
        return userService.count();
    }

    @GetMapping("/countoperateur")
    @PreAuthorize("hasRole('Admin')")
    public long countoperateur() {
        return userService.countoperateur();
    }

    @GetMapping("/countadmin")
    @PreAuthorize("hasRole('Admin')")
    public long countadmin() {
        return userService.countadmin();
    }

    @GetMapping("/countusers")
    @PreAuthorize("hasRole('Admin')")
    public long countusers() {
        return userService.countusers();
    }

    @GetMapping({"/sms/{userName}"})
    public void SMS(@PathVariable String userName) {
        userService.sms(userName);
    }

    @PutMapping({"/addRole/{roleName}/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void addRoleToUser(@PathVariable String roleName, @PathVariable String userName) {
        userService.addRoleToUser(roleName, userName);


    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam String token) {
        User user = userService.activateUser(token);
        if (user != null) {
            return ResponseEntity.ok("Account activated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid activation token");
        }
    }

    // mail
    @PostMapping("/checkEmail")
    public UserAccountResponse resetPasswordEmail(@RequestBody UserResetPassword resetPassword) {
        User user = this.userService.findByUserEmail(resetPassword.getEmail());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if (user != null) {
            String code = UserCode.getCode();
            System.out.println("le code est" + code);
            UserMail mail = new UserMail(resetPassword.getEmail(), code);
            System.out.println("le mail est" + resetPassword.getEmail());
            System.out.println("la variable mail est" + mail);
            System.out.println("Adresse e-mail récupérée : " + resetPassword.getEmail());

            emailServ.sendCodeByMail(mail);
            System.out.println("la variable User est" + user);
            user.setUserCode(code);
            userDao.save(user);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }

    @PostMapping("/resetPassword")
    public UserAccountResponse resetPassword(@RequestBody UserNewPassword newPassword) {
        User user = this.userService.findByUserEmail(newPassword.getEmail());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if (user != null) {
            if (user.getUserCode().equals(newPassword.getCode())) {
                user.setUserPassword(passwordEncoder.encode(newPassword.getPassword()));
                userDao.save(user);
                accountResponse.setResult(1);
            } else {
                accountResponse.setResult(0);
            }
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }
    @ControllerAdvice
    public class CommandeControllerAdvice {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }
    }

}
