import 'package:flutter/material.dart';
import 'package:SUNMAX/model/user_model.dart';

class LoginNotifier extends ChangeNotifier {
  bool loggedIn;
  User _user;
  get user => _user;

  void logIn(User u)  {
    _user = u;
    loggedIn = true;
    notifyListeners();
  }

  void logOut() {
    _user = null;
    loggedIn = false;
    notifyListeners();
  }
}