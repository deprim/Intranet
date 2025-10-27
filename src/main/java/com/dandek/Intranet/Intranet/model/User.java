package com.dandek.Intranet.Intranet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "full_name")
    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @Column(name = "department")
    @NotBlank(message = "Department is required")
    @Size(max = 100, message = "Department must be less than 100 characters")
    private String department;

    @Column(name = "birthday")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Column(name = "phone")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\+?[0-9\\- ]{7,20}", message = "Phone number is invalid")
    private String phoneNumber;

    @Column(name = "position")
    @Size(max = 100, message = "Position must be less than 100 characters")
    private String position;

    @Column(name = "hire_date")
    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    @Column(name = "is_active")
    private boolean active; // boolean по умолчанию false, валидировать необязательно

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "role")
    @NotBlank(message = "Role is required")
    private String role;

    @Transient
    private String avatarUrl;


    public User() {
    }

    public User(String username,
                String password,
                String email,
                String fullName,
                LocalDate dateOfBirth,
                String phoneNumber,
                String position,
                LocalDate hireDate,
                boolean active,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String role,
                String department,
                String avatarUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.hireDate = hireDate;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.department = department;
        this.avatarUrl = "/images/default_avatar.png";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Full name is required") @Size(max = 100, message = "Full name must be less than 100 characters") String getFullName() {
        return fullName;
    }

    public void setFullName(@NotBlank(message = "Full name is required") @Size(max = 100, message = "Full name must be less than 100 characters") String fullName) {
        this.fullName = fullName;
    }

    public @NotBlank(message = "Department is required") @Size(max = 100, message = "Department must be less than 100 characters") String getDepartment() {
        return department;
    }

    public void setDepartment(@NotBlank(message = "Department is required") @Size(max = 100, message = "Department must be less than 100 characters") String department) {
        this.department = department;
    }

    public @NotNull(message = "Date of birth is required") LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull(message = "Date of birth is required") LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotBlank(message = "Phone number is required") @Pattern(regexp = "\\+?[0-9\\- ]{7,20}", message = "Phone number is invalid") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Phone number is required") @Pattern(regexp = "\\+?[0-9\\- ]{7,20}", message = "Phone number is invalid") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Size(max = 100, message = "Position must be less than 100 characters") String getPosition() {
        return position;
    }

    public void setPosition(@Size(max = 100, message = "Position must be less than 100 characters") String position) {
        this.position = position;
    }

    public @NotNull(message = "Hire date is required") LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(@NotNull(message = "Hire date is required") LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public @NotBlank(message = "Role is required") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Role is required") String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }



    @Override
    public String toString() {
        return fullName;
    }
}
