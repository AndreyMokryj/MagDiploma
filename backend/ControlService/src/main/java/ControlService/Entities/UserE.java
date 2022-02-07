package ControlService.Entities;

import ControlService.vo.UserVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users")
public class UserE {
    @Id
    private String id;
    private String username;
    private String password;
    private String phone;
    private String email;

    @Column(name = "station_id")
    private String stationId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String name) {
        this.password = name;
    }

    public static UserE fromVO(UserVO userVO){
        UserE user = new UserE();
        user.setId(userVO.getId());
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword());
        user.setPhone(userVO.getPhone());
        user.setEmail(userVO.getEmail());
        return user;
    }

    public String toString(){
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
