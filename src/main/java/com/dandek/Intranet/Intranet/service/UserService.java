package com.dandek.Intranet.Intranet.service;

import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<User> findAllPagination(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    public Page<User> findFilteredAndPaginated(int page, int itemsPerPage, String search, String department,
                                               String sortBy, boolean showInactive) {


        Sort sort = getSort(sortBy);
        Pageable pageable = PageRequest.of(page, itemsPerPage, sort);
        return userRepository.findFilteredUsers(search, department, showInactive, pageable);
    }


    private Sort getSort(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            // sort by deafult by id
            return Sort.by(Sort.Direction.ASC, "id");
        }

        switch (sortBy) {
            case "fullName":
                return Sort.by(Sort.Direction.ASC, "fullName");
            case "department":
                return Sort.by(Sort.Direction.ASC, "department");
            default:
                return Sort.by(Sort.Direction.ASC, "id");
        }
    }

    public Page<User> findAllPaginationAndSortedDescending(int page, int itemsPerPage, String sortBy) {
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.Direction.DESC, sortBy);
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> birthdayThisMonth(){
        List<User> users = findAll();
        LocalDate today = LocalDate.now();

        return users.stream()
                .filter(user -> user.getDateOfBirth().getMonth() == today.getMonth())
                .collect(Collectors.toList());


    }

    public List<User> birthdayToday(){
        List<User> users = findAll();
        LocalDate today = LocalDate.now();

        return users.stream()
                .filter(user -> user.getDateOfBirth().getDayOfYear() == today.getDayOfYear())
                .collect(Collectors.toList());

    }

    public Integer timeInCompany(User user){

        Integer userYearsAtCompany = user.getHireDate().getYear();
        Integer yearNow = LocalDate.now().getYear();
        Integer yearsInCompany = yearNow - userYearsAtCompany;

        return yearsInCompany;

    }

    @Transactional
    public void registerUser(User user) {

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Long findActiveUsers(){
        return userRepository.findByActiveIsTrue().stream()
                .count();
    }
    public Long findNotActiveUsers(){
        return userRepository.findByActiveIsFalse().stream()
                .count();
    }

    public Integer departmentCount(){
        List<User> users = userRepository.findAll();

        Set<String> departments = new HashSet<>();

        for (User user : users) {
            departments.add(user.getDepartment());
        }

        return departments.size();

    }

    public Set<String> getDepartments(){
        Set<String> departments = new HashSet<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            departments.add(user.getDepartment());
        }
        return departments;
    }

    @Transactional
    public void editMyProfile(User oldUser, User editedUser) {

//        editedUser.setId(oldUser.getId()); // can't be edited
//        editedUser.setUsername(oldUser.getUsername()); // cam't be edited
//        editedUser.setPassword(oldUser.getPassword()); // cam't be edited
//        editedUser.setEmail(oldUser.getEmail()); // may be edited
//        editedUser.setFullName(oldUser.getFullName()); // may be edited
//        editedUser.setDepartment(oldUser.getDepartment()); // may be edited
//        editedUser.setDateOfBirth(oldUser.getDateOfBirth()); // may be edited
//        editedUser.setPhoneNumber(oldUser.getPhoneNumber()); // may be edited
//        editedUser.setPosition(oldUser.getPosition()); // may be edited
//        editedUser.setHireDate(oldUser.getHireDate()); // may be edited if not edited it null
//        editedUser.setActive(oldUser.isActive()); // can't be edited
//        editedUser.setCreatedAt(oldUser.getCreatedAt()); // should be by default
//        editedUser.setUpdatedAt(LocalDateTime.now()); // should be updated for NOW time
//        editedUser.setRole(oldUser.getRole()); // can't be edited



        editedUser.setId(oldUser.getId()); // can't be edited
        editedUser.setUsername(oldUser.getUsername()); // cam't be edited
        editedUser.setPassword(oldUser.getPassword()); // cam't be edited
        editedUser.setActive(oldUser.isActive()); // can't be edited
        editedUser.setCreatedAt(oldUser.getCreatedAt()); // should be by default
        editedUser.setUpdatedAt(LocalDateTime.now()); // should be updated for NOW time
        editedUser.setRole(oldUser.getRole()); // can't be edited

        if (editedUser.getHireDate() == null) {
            editedUser.setHireDate(oldUser.getHireDate());
        } else if (editedUser.getDateOfBirth() == null) {
            editedUser.setDateOfBirth(oldUser.getDateOfBirth());
        }

        userRepository.save(editedUser);



    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }



}
