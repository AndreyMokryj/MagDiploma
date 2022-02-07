import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/views/refreshable_number_widget.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/views/history_widget.dart';
import 'package:SUNMAX/views/diagram_widget.dart';
import 'package:timer_builder/timer_builder.dart';

class PanelPage extends StatefulWidget{
  final Panel panel;

  const PanelPage({Key key, this.panel}) : super(key: key);

  @override
  _PanelPageState createState() => _PanelPageState();
}

class _PanelPageState extends State<PanelPage> {
  Panel _panel;

  @override
  void initState() {
    _panel = widget.panel;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
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

    return SingleChildScrollView(
      padding: EdgeInsets.all(20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Row(
            children: <Widget>[
              Expanded(child: Text("Назва панелі:")),
              SizedBox(
                width: 10,
              ),
              Expanded(
                flex: count,
                child: Text("${widget.panel.name}")),
            ],
          ),
          Divider(),
          Row(
            children: <Widget>[
              Expanded(child: Text("Модель панелі:")),
              SizedBox(
                width: 10,
              ),
              Expanded(
                flex: count,
                child: Text("${widget.panel.model}")),
            ],
          ),
          Divider(),
          Row(
            children: <Widget>[
              Expanded(child: Text("Номінальна потужність:")),
              SizedBox(
                width: 10,
              ),
              Expanded(
                flex: count,
                child: Text("${widget.panel.nominalPower} W")),
            ],
          ),
          Divider(),
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
                      panel: _panel,
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
                      panel: _panel,
                    ),
                    Text(" кВат * год"),
                  ],
                ),
              ),
            ],
          ),
          Divider(),

          TimerBuilder.periodic(
            refreshRate,
            builder : (context) => FutureBuilder(
              future: getPanelInfo(context, panel: _panel),
              builder: (context, snapshot) {
                return Column(
                  children: [
                    Row(
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
                    Divider(),
                    Row(
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
                    Divider(),
                  ],
                );
              }
            ),
          ),

          Row(
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
                      _panel.connected == 1 ? "Підключено" : "Відключено",
                      style: TextStyle(
                        color: _panel.connected == 1 ? Colors.green : Colors.red,
                      ),
                    ),
                    SizedBox(
                      width: 20,
                    ),
                    Switch(
                      value: _panel.connected == 1,
                      onChanged: (val) async {
                        _panel.connected = val ? 1 : 0;
                        bool s = await DBProvider.db.switchPanel(_panel);
                        if (s){
                          setState(() {
                            _panel = _panel;
                          });
                        }
                      },
                    ),
                  ],
                ),
              ),
            ],
          ),
          Divider(
            thickness: 1.1,
          ),
          SizedBox(
            height: 20,
          ),
          DiagramWidget(
            panel: widget.panel,
          ),
          SizedBox(
            height: 30,
          ),
          Divider(
            thickness: 1.1,
          ),
          HistoryWidget(
            panel: widget.panel,
          ),
          Divider(
            thickness: 1.1,
          ),
        ],
      ),
    );
  }
}
