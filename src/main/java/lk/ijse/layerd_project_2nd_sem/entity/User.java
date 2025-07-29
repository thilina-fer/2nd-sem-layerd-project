package lk.ijse.layerd_project_2nd_sem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String contact;
    private String address;
    private String role;
}
