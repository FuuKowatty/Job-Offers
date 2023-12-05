package pl.bartoszmech.domain.accountidentifier;

import pl.bartoszmech.domain.accountidentifier.dto.UserDto;

class UserMapper {
    static UserDto mapFromUser(User user) {
        return UserDto
                .builder()
                .username(user.username())
                .password(user.password())
                .build();
    }

    public static User mapToUser(UserDto userDto) {
        return User
                .builder()
                .username(userDto.username())
                .password(userDto.password())
                .build();
    }
}
