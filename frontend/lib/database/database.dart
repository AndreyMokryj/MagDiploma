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
    return _user;
  }

//  Panels
  Future<List> getPanels(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

  Future<Panel> getPanel(String panelId, String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/${panelId}"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
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

  Future<double> getPanelsTotalPower(String stationId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}panels/power/total/"),
      headers: {
        'content-type': 'application/json'
      },
      body: stationId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody;
  }

//  Stations
  Future<List<Station>> getStations(String userId) async {
    final response = await http.post(
      Uri.parse("${baseUrl}stations/userId/}"),
      headers: {
        'content-type': 'application/json'
      },
      body: userId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody.map((e) => Station.fromMap(e)).toList();
  }

  Future<Station> getStation(String userId, String ukey) async {
    final response = await http.post(
        Uri.parse("${baseUrl}stations/ukey/${ukey}"),
        headers: {
          'content-type': 'application/json'
        },
      body: userId,
    );

    final responseBody = jsonDecode(response.body);
    return responseBody.map((e) => Station.fromMap(e)).toList();
  }

  Future<bool> switchGrid(Station station) async {
    final response = await http.post(
      Uri.parse("${baseUrl}stations/turn-grid-${station.gridConnection}"),
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
      Uri.parse("${baseUrl}stations/turn-station-${station.stationConnection}"),
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