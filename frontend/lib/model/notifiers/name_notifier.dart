import 'package:flutter/material.dart';

class NameNotifier extends ChangeNotifier {
  String _name;
  get name => _name;

  void setName(String newName)  {
    if (_name != newName) {
      _name = newName;
      notifyListeners();
    }
  }
}