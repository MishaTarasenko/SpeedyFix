package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "User first name can't be blank")
    @Size(max = 200, message = "User first name size can't be more than 200 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "User last name can't be blank")
    @Size(max = 200, message = "User last name size can't be more than 200 characters")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "User invalid email")
    @NotBlank(message = "User email can't be blank")
    @Size(max = 200, message = "User email size can't be more than 200 characters")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "User telephone number can't be blank")
    @Pattern(regexp = "\\d{10}", message = "Telephone must be a 10-digit number")
    @Column(name = "telephone_number")
    private String telephoneNumber;
}
