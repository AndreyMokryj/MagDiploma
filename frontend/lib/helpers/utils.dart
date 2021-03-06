import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/model/station_model.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/log_model.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:provider/provider.dart';

double getWidth(BuildContext context) => MediaQuery.of(context).size.width;
double getHeight(BuildContext context) => MediaQuery.of(context).size.height;

double formatDouble(double number, [int n = 0]){
  String _string = number.toStringAsFixed(n);
  double res = double.parse(_string);
  return res;
}

Future<List<Log>> getPanelHistoryLogs(Panel panel) async{
  List<Log> result = [];
  final logProducedMaps = await DBProvider.db.getHistoryProducedLogs(panel.stationId);

  logProducedMaps.forEach((element) {
    Log log = Log.fromMap(element);

    if (log.panelId == panel.id) {
      result.add(log);
    }
  });

  return result;
}

Future<List<Log>> getPanelTodayLogs(Panel panel) async{
  List<Log> result = [];
  final logProducedMaps = await DBProvider.db.getTodayProducedLogs(panel.stationId);

  logProducedMaps.forEach((element) {
    Log log = Log.fromMap(element);

    if (log.panelId == panel.id) {
      result.add(log);
    }
  });

  return result;
}

Future<List<Log>> getAlllHistoryLogs(String stationId) async{
  List<Log> result = [];

  final logProducedMaps = await DBProvider.db.getHistoryProducedLogs(stationId);
  final logGivenMaps = await DBProvider.db.getHistoryGivenLogs(stationId);

  for(Map element in logGivenMaps) {
    Log resultLog = Log.fromMap(element);
    resultLog.produced = 0;

    for(Map map in logProducedMaps){
      Log log = Log.fromMap(map);
      if(log.date == resultLog.date){
        resultLog.produced = resultLog.produced + log.produced;
      }
    }
    result.add(resultLog);
  }

  return result;
}

Future<List<Log>> getAlllTodayLogs(String stationId) async{
  List<Log> result = [];

  final logProducedMaps = await DBProvider.db.getTodayProducedLogs(stationId);
  final logGivenMaps = await DBProvider.db.getTodayGivenLogs(stationId);

  logGivenMaps.forEach((element) {
    Log resultLog = Log.fromMap(element);
    resultLog.produced = 0;

    for(Map map in logProducedMaps){
      Log log = Log.fromMap(map);
      if(log.time == resultLog.time){
        resultLog.produced += log.produced;
      }
    }
    result.add(resultLog);
  });

  return result;
}

Future<double> getRequiredPower({String stationId, Panel panel}) async{
  final power = panel == null
    ? await DBProvider.db.getPanelsTotalPower(stationId)
    : await DBProvider.db.getPanelPower(panel);

  return power;
}

Future<double> getTodayProducedEnergy({String stationId, Panel panel}) async{
  final logs = panel == null
    ? await getAlllTodayLogs(stationId)
    : await getPanelTodayLogs(panel);

  double work = 0.0;
  logs.forEach((element) {
    work += element.produced;
  });

  work = work / 3600000;
  return work;
}

Future<double> getTodayGivenEnergy({String stationId, Panel panel}) async{
  final logs = panel == null
    ? await getAlllTodayLogs(stationId)
    : await getPanelTodayLogs(panel);

  double work = 0.0;
  logs.forEach((element) {
    work += element.given;
  });

  work = work / 3600000;
  return work;
}

String formatDate(String date){
  List<String> dates = date.split('-');
  final year = dates[0];
  final month = monthNames[dates[1]];
  final day = dates[2];

  return '$day $month $year';
}

String formatDateTime(String dateTime){
  final dateTimes = dateTime.split('T');
  final date = dateTimes[0];
  final time = dateTimes[1].substring(0, 5);


  return '${formatDate(date)} $time';
}

Future<String> getDateTime(String stationId) async{
  final String dt = await DBProvider.db.getDateTime(stationId);

  return formatDateTime(dt);
}

Future<double> getAccumulatedEnergy(BuildContext context, String ukey) async{
  final user = Provider.of<LoginNotifier>(context, listen: false).user;
  final station = await DBProvider.db.getStation(user.id, ukey);

  return station.energy / 3600000;
}

Future<Station> getStation(BuildContext context, String ukey) async{
  final user = Provider.of<LoginNotifier>(context, listen: false).user;
  final station = await DBProvider.db.getStation(user.id, ukey);
  return station;
}

Future<Panel> getPanelInfo(Panel panel) async{
  return await DBProvider.db.getPanel(panel.id, panel.stationId);
}
