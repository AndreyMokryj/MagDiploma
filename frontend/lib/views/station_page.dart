import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/views/diagram_widget.dart';
import 'package:SUNMAX/views/history_widget.dart';
import 'package:SUNMAX/views/refreshable_number_widget.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/user_model.dart';
import 'package:SUNMAX/model/accumulator_model.dart' as acc;
import 'package:SUNMAX/views/station_widget.dart';
import 'package:provider/provider.dart';
import 'package:timer_builder/timer_builder.dart';

class StationPage extends StatelessWidget{
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

    return FutureBuilder(
      future: DBProvider.db.getAccumulator(user.id),
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
                    accumulator: snapshot.data as acc.Accumulator,
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
                  DiagramWidget(),
                  SizedBox(
                    height: 20,
                  ),
                  Divider(
                    thickness: 1.1,
                  ),

                  HistoryWidget(),
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