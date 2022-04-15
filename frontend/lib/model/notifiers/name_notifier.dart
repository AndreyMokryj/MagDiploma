import 'package:flutter/material.dart';

class NameNotifier extends ChangeNotifier {
  String _name;
  get name => _name;

  void setName(String newName)  {
    _name = newName;
    notifyListeners();
  }
}