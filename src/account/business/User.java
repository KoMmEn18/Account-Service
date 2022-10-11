package account.business;

import account.business.annotations.NotBreached;
import account.business.annotations.UserExist;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = ".+@acme\\.com")
    //@UserExist(reversed = true, message = "User exist!")
    private String email;

    @NotBlank
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @NotBreached()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Role> roles = new HashSet<>();

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return authorities;
    }

    @JsonGetter("roles")
    public List<String> getRoles() {
        return roles.stream().map(r -> "ROLE_" + r.getName()).sorted(String::compareTo).collect(Collectors.toList());
    }

    public void grantAuthority(Role authority) {
        roles.add(authority);
    }

    public void removeAuthority(Role authority) {
        roles.remove(authority);
    }

    @JsonIgnore
    public boolean hasAuthority(String authority) {
        return getAuthorities().contains(new SimpleGrantedAuthority(authority));
    }

    @JsonIgnore
    public boolean isAdmin() {
        return getAuthorities().contains(new SimpleGrantedAuthority("ADMINISTRATOR"));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

