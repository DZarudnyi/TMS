package layron.tms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import layron.tms.validation.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(firstString = "password", secondString = "repeatPassword")
public class UserRegistrationRequestDto {
    @NotBlank
    @Length(min = 4, max = 35)
    private String username;
    @NotBlank
    @Length(min = 4, max = 35)
    private String password;
    @NotBlank
    @Length(min = 4, max = 35)
    private String repeatPassword;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
