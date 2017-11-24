package xyz.javista.core.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Length(min = 1, max = 32, message = "The name must be between 1 and 50 characters")
    private String name;

    @NotNull
    @Length(min = 1, max = 255, message = "The email must be between 1 and 255 characters")
    private String email;

    @NotNull
    @Length(min = 1, max = 32, message = "The login must be between 1 and 32 characters")
    @Column(name="login", unique = true)
    private String login;

    @NotNull
    @Length(min = 1, max = 255, message = "The password must be between 1 and 255 characters")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "author")
    private List<Order> createdOrders;

    @OneToMany(mappedBy = "purchaser")
    private List<OrderLineNumber> purchasedProducts;

    public User() {
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (username != null && !"".equals(username) && password != null) {
            this.login = username;
            this.password = password;
            /*this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));*/
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Order> getCreatedOrders() {
        return createdOrders;
    }

    public void setCreatedOrders(List<Order> createdOrders) {
        this.createdOrders = createdOrders;
    }

    public List<OrderLineNumber> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<OrderLineNumber> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}
