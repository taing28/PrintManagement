package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.Permission;
import org.example.printmanagement.model.entities.Role;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.PermissionRepo;
import org.example.printmanagement.model.repositories.TeamRepo;
import org.example.printmanagement.model.repositories.UserRepo;
import org.example.printmanagement.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepo _userRepo;
    @Autowired
    private PermissionRepo _permissionRepo;
    @Autowired
    private TeamRepo _teamRepo;
    @Autowired
    private PasswordEncoder _encoder;

    @Override
    public List<User> getAllUser() {
        return _userRepo.findAll();
    }

    @Override
    public User getUserById(int id) throws Exception{
        User user = _userRepo.findById(id).get();
        if (user == null) {
            throw new Exception("User are not available");
        }
        return user;
    }

    @Override
    public User createUser(SignUpRequest request) throws Exception {
        if (!request.getPassword().equals(request.getCfPassword())) {
            throw new Exception("Password not matched");
        }
        if (_userRepo.existsByUserNameEqualsIgnoreCase(request.getUsername())) {
            throw new Exception("Username already existed");
        }
        User user = request.toEntity();
        user.setPassword(_encoder.encode(request.getPassword()));
        user.setTeam(new Team(3));//Free team
        user.setTeamId(3);
        //Save user for taking id
        user = _userRepo.save(user);
        _permissionRepo.save(new Permission(user.getId(), 4, new User(user.getId()), new Role(4)));
        return user;
    }

    @Override
    public User updatePassword(int userId, String password) {
        User user = _userRepo.findById(userId).get();
        user.setPassword(_encoder.encode(password));
        return _userRepo.save(user);
    }

    @Override
    public void disableUser(int userId) throws Exception{
        if(!_userRepo.existsById(userId)) {
            throw new Exception("User not found");
        }
        User user = _userRepo.findById(userId).get();
        user.setActive(false);
        _userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return _userRepo.findUserByEmail(email);
    }

    @Override
    public User changeUserTeam(int userId, int teamId) throws Exception {
        //Take current User
        User oldUser = _userRepo.findById(userId).get();
        //Take old Team
        Team oldTeam = _teamRepo.findById(oldUser.getTeamId()).get();
        //Check for update or add new team
        if (oldTeam != null) {
            if (teamId == oldTeam.getId()) {
                throw new Exception("There is no change");
            }

            //Need to set new manager before transfer
            if(oldTeam.getManagerId() == oldUser.getId()) {
                throw new Exception("Can not transfer team manager");
            }
        }
        //Update Team Id
        oldUser.setTeam(new Team(teamId));
        oldUser.setTeamId(teamId);
        return _userRepo.save(oldUser);
    }
}
