import 'package:SUNMAX/model/station_model.dart';

class User{
  String id;
  String username;
  String password;
  String phone;
  String email;
  Station station;

  User({this.id, this.username, this.password, this.phone, this.email, this.station});

  Map<String, dynamic> toMap(){
    return {
      'id' : id,
      'username' : username,
      'password' : password,
      'phone' : phone,
      'email' : email,
    };
  }

  factory User.fromMap(Map<String, dynamic> map){
    return User(
      id: map['id'] as String,
      username: map['username'] as String,
      password: map['password'] as String,
      email: map['email'] as String,
      phone: map['phone'] as String,
    );
  }
}