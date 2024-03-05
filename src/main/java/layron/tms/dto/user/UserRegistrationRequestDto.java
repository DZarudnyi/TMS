package layron.tms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import layron.tms.validation.FieldMatch;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(firstString = "password", secondString = "repeatPassword")
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 4, max = 35)
    private String password;
    @NotBlank
    @Length(min = 4, max = 35)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
