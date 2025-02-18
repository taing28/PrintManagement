package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.Permission;
import org.example.printmanagement.model.entities.Role;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.PermissionRepo;
import org.example.printmanagement.model.repositories.RoleRepo;
import org.example.printmanagement.model.repositories.TeamRepo;
import org.example.printmanagement.model.repositories.UserRepo;
import org.example.printmanagement.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private RoleRepo _roleRepo;

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
    public Set<User> getAllDeliver() throws Exception {
        Role role = _roleRepo.findRoleByRoleCodeEquals("DELIVER");
        Set<User> deliverSet = new HashSet<>();
        List<Permission> deliverPermissList = _permissionRepo.findAllByRoleId(role.getId());
        for (Permission permission : deliverPermissList) {
            deliverSet.add(_userRepo.findById(permission.getUserId()).get());
        }
        return deliverSet;
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

        Team freeTeam = _teamRepo.findTeamByNameEquals("Free");//Team for new account
        user.setTeam(new Team(freeTeam.getId()));
        user.setTeamId(freeTeam.getId());
        //Save user for taking id
        user = _userRepo.save(user);
        Role roleEmployee = _roleRepo.findRoleByRoleCodeEquals("EMPLOYEE");
        _permissionRepo.save(new Permission(user.getId(), roleEmployee.getId(), new User(user.getId()), new Role(roleEmployee.getId())));
        return user;
    }

    @Override
    public User updatePassword(int userId, String password) {
        User user = _userRepo.findById(userId).get();
        user.setPassword(_encoder.encode(password));
        return _userRepo.save(user);
    }

    @Override
    public void changeActiveUser(int userId) throws Exception{
        if(!_userRepo.existsById(userId)) {
            throw new Exception("User not found");
        }
        User user = _userRepo.findById(userId).get();
        user.setActive(!user.isActive());
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
