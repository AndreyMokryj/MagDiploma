import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/ui/main_page.dart';
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
    User user = Provider.of<LoginNotifier>(context).user;

    double w = getWidth(context);
    double h = getHeight(context);

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

    return MainPage(
      title: "Станція",
      future: DBProvider.db.getStation(user.id, ukey),
      builder:(context, snapshot) {
        if (snapshot.hasData) {
          return SingleChildScrollView(
            child: Container(
              child: Column(
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.all(10),
                    child: Text("Моя станція")
                  ),
                  StationWidget(
                    accumulator: snapshot.data as acc.Station,
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
                    stationId: snapshot.id,
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Divider(
                    thickness: 1.1,
                  ),

                  HistoryWidget(
                    stationId: snapshot.id,
                  ),
                  Divider(
                    thickness: 1.1,
                  ),
                ],
              ),
            ),
          );
        }
        else {
          return Container();
        }
      }
    );
  }
}