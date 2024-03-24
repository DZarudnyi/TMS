package layron.tms.dto.user;

public record UpdateUserRequestDto(
        String email,
        String firstName,
        String lastName
) {
}
