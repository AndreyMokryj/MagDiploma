import 'dart:convert';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/model/station_model.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/model/user_model.dart' as u;
import 'package:http/http.dart' as http;

class DBProvider {
  DBProvider._();

  static final DBProvider db = DBProvider._();

//  Sign up
  Future<bool> newUser(u.User user) async {
    final response = await http.post(
      Uri.parse("${baseUrl}users/"),
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
      Uri.parse("${baseUrl}users/check/"),
      body: jsonEncode(user.toMap()),
      headers: {
        'content-type': 'application/json'
      }
    );
    if (response.body == ""){
      return null;
    }

    final responseBody = jsonDecode(response.body);
    u.User _user = u.User.fromMap(responseBody);
    _user.station = await getStation(_user.id);
    return _user;
  }

//  Panels
  Future<List> getPanels(u.User user) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/"),
      headers: {
        'content-type': 'application/json'
      },
      body: user.station.id,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<Panel> getPanel(String panelId, u.User user) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/${panelId}"),
      headers: {
        'content-type': 'application/json'
      },
      body: user.station.id,
    );

    final responseBody = jsonDecode(response.body);
    return Panel.fromMap(responseBody);
  }

  Future<bool> switchPanel(Panel panel) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/turn-${panel.connected}/${panel.id}"),
      headers: {
        'content-type': 'application/json'
      },
      body: panel.stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<double> getPanelPower(Panel panel) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/power/${panel.id}"),
      headers: {
        'content-type': 'application/json'
      },
      body: panel.stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<double> getPanelsTotalPower(u.User user) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/power/total/"),
      headers: {
        'content-type': 'application/json'
      },
      body: user.station.id,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

//  Station
  Future<Station> getStation(String userId) async {
    final response = await http.get(
      Uri.parse("${baseUrl}station/userId/${userId}"),
      headers: {
        'content-type': 'application/json'
      }
    );

    final responseBody = jsonDecode(response.body);
    return Station.fromMap(responseBody);
  }

  Future<bool> switchGrid(Station station) async {
    final response = await http.post(
      Uri.parse("${baseUrl}station/turn-grid-${station.gridConnection}"),
      headers: {
        'content-type': 'application/json'
      },
      body: station.id,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<bool> switchStation(Station station) async {
    final response = await http.post(
      Uri.parse("${baseUrl}station/turn-station-${station.stationConnection}"),
      headers: {
        'content-type': 'application/json'
      },
      body: station.id,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

//  Logs
  Future<List> getHistoryProducedLogs(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}logs/history-produced/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<List> getHistoryGivenLogs(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}logs/history-given/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<List> getTodayProducedLogs(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}logs/today-produced/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<List> getTodayGivenLogs(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}logs/today-given/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<String> getDateTime(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}time/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    return response.body;
  }
}