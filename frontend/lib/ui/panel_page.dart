import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/user_model.dart';
import 'package:SUNMAX/route.dart';
import 'package:SUNMAX/ui/main_view.dart';
import 'package:SUNMAX/ui/widgets/refreshable_number_widget.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/ui/widgets/history_widget.dart';
import 'package:SUNMAX/ui/widgets/diagram_widget.dart';
import 'package:provider/provider.dart';
import 'package:timer_builder/timer_builder.dart';

class PanelPage extends StatefulWidget{
  final String ukey;
  final String panelId;

  const PanelPage({Key key, this.panelId, this.ukey, }) : super(key: key);

  @override
  _PanelPageState createState() => _PanelPageState();
}

class _PanelPageState extends State<PanelPage> {
  bool connected;

  @override
  Widget build(BuildContext context) {
    User user = Provider.of<LoginNotifier>(context, listen: false).user;

    double w = getWidth(context);

    int count = 1;
    if(w >= mediumLimit){
      count += 1;
    }

    return MainView(
      title: Text("Інформація про панель"),
      onWillPop: (){
        Navigator.of(context).pushNamed("/${RoutePaths.stations}/${widget.ukey}/${RoutePaths.panels}");
        return Future.value(true);
      },
      future: DBProvider.db.getStation(user.id, widget.ukey)
          .then((value) => DBProvider.db.getPanel(widget.panelId, value.id)),
      builder: (context, panelSnapshot) {
        if (panelSnapshot.hasData) {
          Panel panel = panelSnapshot.data;
          connected = panel.connected == 1;

          return Container(
            padding: EdgeInsets.symmetric(
              vertical: 10
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 20,
                  ),
                  child: Row(
                    children: <Widget>[
                      Expanded(child: Text("Назва панелі:")),
                      SizedBox(
                        width: 10,
                      ),
                      Expanded(
                          flex: count,
                          child: Text("${panel.name}")),
                    ],
                  ),
                ),
                Divider(),
                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 20,
                  ),
                  child: Row(
                    children: <Widget>[
                      Expanded(child: Text("Модель панелі:")),
                      SizedBox(
                        width: 10,
                      ),
                      Expanded(
                          flex: count,
                          child: Text("${panel.model}")),
                    ],
                  ),
                ),
                Divider(),
                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 20,
                  ),
                  child: Row(
                    children: <Widget>[
                      Expanded(child: Text("Номінальна потужність:")),
                      SizedBox(
                        width: 10,
                      ),
                      Expanded(
                          flex: count,
                          child: Text("${panel.nominalPower} W")),
                    ],
                  ),
                ),
                Divider(),
                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 20,
                  ),
                  child: Row(
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
                              stationId: panel.stationId,
                              panel: panel,
                            ),
                            Text(" W"),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                Divider(),
                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 20,
                  ),
                  child: Row(
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
                              panel: panel,
                            ),
                            Text(" кВат * год"),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                Divider(),

                TimerBuilder.periodic(
                  refreshRate,
                  builder: (context) =>
                      FutureBuilder(
                          future: getPanelInfo(panel),
                          builder: (context, snapshot) {
                            return Column(
                              children: [
                                Container(
                                  padding: EdgeInsets.symmetric(
                                    horizontal: 20,
                                  ),
                                  child: Row(
                                    children: <Widget>[
                                      Expanded(child: Text("Азимут:")),
                                      SizedBox(
                                        width: 10,
                                      ),
                                      Expanded(
                                        flex: count,
                                        child: Text("${snapshot.data?.azimuth}"),
                                      ),
                                    ],
                                  ),
                                ),
                                Divider(),
                                Container(
                                  padding: EdgeInsets.symmetric(
                                    horizontal: 20,
                                  ),
                                  child: Row(
                                    children: <Widget>[
                                      Expanded(child: Text("Висота:")),
                                      SizedBox(
                                        width: 10,
                                      ),
                                      Expanded(
                                        flex: count,
                                        child: Text("${snapshot.data?.altitude}"),
                                      ),
                                    ],
                                  ),
                                ),
                                Divider(),
                              ],
                            );
                          }
                      ),
                ),

                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 20,
                  ),
                  child: Row(
                    children: <Widget>[
                      Expanded(child: Text("Статус:")),
                      SizedBox(
                        width: 10,
                      ),
                      Expanded(
                        flex: count,
                        child: Row(
                          children: <Widget>[
                            Text(
                              connected ? "Підключено" : "Відключено",
                              style: TextStyle(
                                color: connected
                                    ? Colors.green
                                    : Colors.red,
                              ),
                            ),
                            SizedBox(
                              width: 20,
                            ),
                            Switch(
                              value: connected,
                              onChanged: (val) async {
                                panel.connected = val ? 1 : 0;
                                bool s = await DBProvider.db.switchPanel(panel);
                                if (s) {
                                  setState(() {
                                    connected = val;
                                  });
                                }
                              },
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                Divider(
                  thickness: 1.1,
                ),
                SizedBox(
                  height: 20,
                ),
                Container(
                  padding: EdgeInsets.symmetric(
                    horizontal: 10,
                  ),
                  child: DiagramWidget(
                    stationId: panel.stationId,
                    panel: panel,
                  ),
                ),
                SizedBox(
                  height: 30,
                ),
                Divider(
                  thickness: 1.1,
                ),
                HistoryWidget(
                  panel: panel,
                  stationId: panel.stationId,
                ),
                Divider(
                  thickness: 1.1,
                ),
              ],
            ),
          );
        } else {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }
}
