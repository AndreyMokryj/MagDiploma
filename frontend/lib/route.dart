import 'package:SUNMAX/app.dart';
import 'package:SUNMAX/ui/login_page.dart';
import 'package:SUNMAX/ui/panel_page.dart';
import 'package:SUNMAX/ui/panels_list.dart';
import 'package:SUNMAX/ui/station_page.dart';
import 'package:SUNMAX/ui/stations_list.dart';
import 'package:SUNMAX/ui/wrap_page.dart';
import 'package:flutter/material.dart';

class RoutePaths {
  static const stations = "stations";
  static const login = "login";
  static const panels = "panels";
}

Route<dynamic> getRoute(RouteSettings settings) {
  Widget page;
  final path = settings.name;
  List _words = path.split("/")..removeWhere((value) => value.isEmpty);
  switch(_words.length) {
    case 1:
      switch (_words[0]) {
        case RoutePaths.stations:
          page = StationsList();
          break;
        case RoutePaths.login:
          globalLoginNotifier.logOut();
          return MaterialPageRoute<void>(
            settings: settings,
            builder: (BuildContext context) => LoginPage(
              created: ((settings.arguments ?? "") as String) == "created",
            ),
          );
      }
      break;
    case 2:
      if(_words[0] == RoutePaths.stations) {
        page = StationPage(
          ukey: _words[1],
        );
      }
      break;
    case 3:
      if(_words[0] == RoutePaths.stations && _words[2] == RoutePaths.panels) {
        page = PanelsList(
          ukey: _words[1],
        );
      }
      break;
    case 4:
      if(_words[0] == RoutePaths.stations && _words[2] == RoutePaths.panels) {
        page = PanelPage(
          ukey: _words[1],
          panelId: _words[3],
        );
      }
      break;
    default:
      settings = settings.copyWith(
        name: "/stations",
      );
      page = StationsList();
      break;
  }

  if(settings.arguments == true) {
    return MaterialPageRoute<void>(
      settings: settings,
      builder: (BuildContext context) => WrapPage(child: page),
    );
  } else {
    return PageRouteBuilder(
      settings: settings,
      pageBuilder: (BuildContext context, a1, a2) => WrapPage(child: page),
    );
  }
}
