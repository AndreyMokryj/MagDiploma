import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/station_model.dart';
import 'package:SUNMAX/route.dart';
import 'package:SUNMAX/ui/main_view.dart';
import 'package:SUNMAX/ui/widgets/diagram_widget.dart';
import 'package:SUNMAX/ui/widgets/history_widget.dart';
import 'package:SUNMAX/ui/widgets/refreshable_number_widget.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/user_model.dart';
import 'package:SUNMAX/model/station_model.dart' as acc;
import 'package:SUNMAX/ui/widgets/station_widget.dart';
import 'package:provider/provider.dart';

class StationPage extends StatelessWidget{
  final String ukey;

  const StationPage({Key key, this.ukey}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    User user = Provider.of<LoginNotifier>(context, listen: false).user;

    double w = getWidth(context);

    int count = 1;
    if(w >= smallLimit){
      count += 1;
    }
    if(w >= mediumLimit){
      count += 1;
    }
    if(w >= largeLimit){
      count += 1;
    }

    return MainView(
      automaticallyImplyLeading: true,
      onWillPop: (){
        Navigator.of(context).pushNamed("/${RoutePaths.stations}");
        return Future.value(true);
      },
      title: "Станція",
      future: DBProvider.db.getStation(user.id, ukey),
      builder:(context, snapshot) {
        if (snapshot.hasData) {
          Station station = snapshot.data as acc.Station;
          return Column(
            children: <Widget>[
              StationWidget(
                accumulator: station,
              ),

              SizedBox(
                height: 10,
              ),

              Row(
                children: <Widget>[
                  Expanded(child: Text("Поточна потужність:")),
                  SizedBox(
                    width: 10,
                  ),
                  Expanded(
                    flex: count,
                    child: Row(
                      children: <Widget>[

                        RefreshableNumberWidget(
                          future: getRequiredPower,
                          stationId: station.id,
                        ),
                        Text(" W"),
                      ],
                    ),
                  ),
                ],
              ),
              Divider(),
              Row(
                children: <Widget>[
                  Expanded(child: Text("Запас енергії накопичувача:")),
                  SizedBox(
                    width: 10,
                  ),
                  Expanded(
                    flex: count,
                    child: Row(
                      children: <Widget>[
                        RefreshableNumberWidget(
                          future: getAccumulatedEnergy,
                          ukey: station.ukey,
                        ),
                        Text(" кВат * год"),
                      ],
                    ),
                  ),
                ],
              ),

              Divider(),

              Row(
                children: <Widget>[
                  Expanded(child: Text("Вироблено з початку дня:")),
                  SizedBox(
                    width: 10,
                  ),
                  Expanded(
                    flex: count,
                    child: Row(
                      children: <Widget>[
                        RefreshableNumberWidget(
                          future: getTodayProducedEnergy,
                          stationId: station.id,
                        ),
                        Text(" кВат * год"),
                      ],
                    ),
                  ),
                ],
              ),
              Divider(),

              Row(
                children: <Widget>[
                  Expanded(child: Text("Продано з початку дня:")),
                  SizedBox(
                    width: 10,
                  ),
                  Expanded(
                    flex: count,
                    child: Row(
                      children: <Widget>[
                        RefreshableNumberWidget(
                          future: getTodayGivenEnergy,
                          stationId: station.id,
                        ),
                        Text(" кВат * год"),
                      ],
                    ),
                  ),
                ],
              ),

              Divider(),
              SizedBox(
                height: 20,
              ),
              DiagramWidget(
                stationId: station.id,
              ),
              SizedBox(
                height: 20,
              ),
              Divider(
                thickness: 1.1,
              ),

              HistoryWidget(
                stationId: station.id,
              ),
              Divider(
                thickness: 1.1,
              ),
            ],
          );
        }
        else {
          return Container();
        }
      }
    );
  }
}