import 'dart:convert';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/model/accumulator_model.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/model/user_model.dart' as u;
import 'package:http/http.dart' as http;

class DBProvider {
  DBProvider._();

  static final DBProvider db = DBProvider._();

//  Sign up
  Future<bool> newUser(u.User user) async {
    final response = await http.post(
      "${baseUrl}users/",
      body: jsonEncode(user.toMap()),
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

//  Login
  Future<u.User> checkUser(u.User user) async {
    final response = await http.post(
      "${baseUrl}users/check/",
      body: jsonEncode(user.toMap()),
      headers: {
        'content-type': 'application/json'
      }
    );
    if (response.body == ""){
      return null;
    }

    final responseBody = jsonDecode(response.body);
    return u.User.fromMap(responseBody);
  }

//  Panels
  Future<List> getPanels(u.User user) async {
    final response = await http.get(
      "${baseUrl}panels/userId/${user.id}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<Panel> getPanel(String panelId, String userId) async {
    final response = await http.get(
      "${baseUrl}panels/${panelId}/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return Panel.fromMap(responseBody);
  }

  Future<bool> switchPanel(Panel panel) async {
    final response = await http.get(
      "${baseUrl}panels/turn-${panel.connected}/${panel.id}/userId/${panel.userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<double> getPanelPower(Panel panel) async {
    final response = await http.get(
      "${baseUrl}panels/power/${panel.id}/userId/${panel.userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<double> getPanelsTotalPower(String userId) async {
    final response = await http.get(
      "${baseUrl}panels/power/total/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

//  Accumulator
  Future<Accumulator> getAccumulator(String userId) async {
    final response = await http.get(
      "${baseUrl}accumulator/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return Accumulator.fromMap(responseBody);
  }

  Future<bool> switchGrid(Accumulator accumulator) async {
    final response = await http.get(
      "${baseUrl}accumulator/turn-grid-${accumulator.gridConnection}/userId/${accumulator.id}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<bool> switchStation(Accumulator accumulator) async {
    final response = await http.get(
      "${baseUrl}accumulator/turn-station-${accumulator.stationConnection}/userId/${accumulator.id}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

//  Logs
  Future<List> getHistoryProducedLogs(String userId) async {
    final response = await http.get(
      "${baseUrl}logs/history-produced/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<List> getHistoryGivenLogs(String userId) async {
    final response = await http.get(
      "${baseUrl}logs/history-given/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<List> getTodayProducedLogs(String userId) async {
    final response = await http.get(
      "${baseUrl}logs/today-produced/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<List> getTodayGivenLogs(String userId) async {
    final response = await http.get(
      "${baseUrl}logs/today-given/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<String> getDateTime(String userId) async {
    final response = await http.get(
      "${baseUrl}time/userId/${userId}",
      headers: {
        'content-type': 'application/json'
      }
    );

    return response.body;
  }
}