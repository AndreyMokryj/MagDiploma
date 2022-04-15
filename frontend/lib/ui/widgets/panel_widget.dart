import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/styles.dart';
import 'package:SUNMAX/route.dart';
import 'package:SUNMAX/ui/widgets/refreshable_number_widget.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:timer_builder/timer_builder.dart';

class PanelWidget extends StatefulWidget{
  final Panel panel;
  final String ukey;

  const PanelWidget({Key key, this.panel, this.ukey}) : super(key: key);

  @override
  _PanelWidgetState createState() => _PanelWidgetState();
}

class _PanelWidgetState extends State<PanelWidget> {
  Panel _panel;

  @override
  void initState() {
    _panel = widget.panel;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(
        top: 5,
        left: 5,
        right: 5,
        bottom: 10,
      ),
      padding: EdgeInsets.all(5),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        border: Border.all(
          color: Colors.orange,
        )
      ),
      child: Row(
        children: <Widget>[
          Expanded(
            flex: 2,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Row(
                  children: <Widget>[
                    Text("${_panel.name}"),
                    SizedBox(
                      width: 50,
                    ),
                    RefreshableNumberWidget(
                      future: getRequiredPower,
                      panel: _panel,
                    ),
                    Text(" W"),
                  ],
                ),
                SizedBox(
                  height: 10,
                ),

                TimerBuilder.periodic(
                  refreshRate,
                  builder : (context) => FutureBuilder(
                    future: getPanelInfo(_panel),
                    builder: (context, snapshot) {
                      return Row(
                        children: [
                          Text(
                            "Азимут: ${snapshot.data?.azimuth}",
                            style: lightTextStyle,
                          ),
                          SizedBox(
                            width: 30,
                          ),
                          Text(
                            "Висота: ${snapshot.data?.altitude}",
                            style: lightTextStyle,
                          ),
                        ],
                      );
                    }
                  ),
                ),
                SizedBox(
                  height: 3,
                ),
                Text(
                  _panel.model,
                  style: lightTextStyle,
                ),
                SizedBox(
                  height: 3,
                ),
                Text(
                  "${_panel.nominalPower} W",
                  style: lightTextStyle,
                ),
                Container(
                  margin: EdgeInsets.only(
                    top: 15,
                    left: 10,
                    bottom: 5,
                  ),
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      primary: Colors.red,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(40),
                      ),
                    ),
                    child: Text(
                      "Деталі",
                      style: TextStyle(
                        color: Colors.white,
                      ),
                    ),
                    onPressed: (){
                      Navigator.of(context).pushNamed("/${RoutePaths.stations}/${widget.ukey}/${RoutePaths.panels}/${widget.panel.id}");
                    },
                  ),
                ),
              ],
            ),
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
    );
  }
}
