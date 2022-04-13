import 'package:SUNMAX/model/station_model.dart';
import 'package:flutter/material.dart';

class StationCard extends StatelessWidget{
  final Station station;

  const StationCard({Key key, this.station}) : super(key: key);

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
                    Text("${station.ukey}"),
                    SizedBox(
                      width: 30,
                    ),
                    // RefreshableNumberWidget(
                    //   future: getRequiredPower,
                    //   panel: _panel,
                    // ),
                    // Text(" W"),
                  ],
                ),
                SizedBox(
                  height: 10,
                ),

                // TimerBuilder.periodic(
                //   refreshRate,
                //   builder : (context) => FutureBuilder(
                //       future: getPanelInfo(_panel),
                //       builder: (context, snapshot) {
                //         return Text(
                //           "Азимут: ${snapshot.data?.azimuth}, Висота: ${snapshot.data?.altitude}",
                //           style: lightTextStyle,
                //         );
                //       }
                //   ),
                // ),
                // Text(
                //   _panel.model,
                //   style: lightTextStyle,
                // ),
                // Text(
                //   "${_panel.nominalPower} W",
                //   style: lightTextStyle,
                // ),
                Container(
                  margin: EdgeInsets.only(
                    top: 5,
                    left: 10,
                    bottom: 5,
                  ),
                  child: FlatButton(
                    color: Colors.red,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(40),
                    ),
                    child: Text(
                      "Деталі",
                      style: TextStyle(
                        color: Colors.white,
                      ),
                    ),
                    onPressed: (){
                      Navigator.of(context).pushNamed("/stations/${station.ukey}");
                    },
                  ),
                ),
              ],
            ),
          ),
          // Switch(
          //   value: _panel.connected == 1,
          //   onChanged: (val) async {
          //     _panel.connected = val ? 1 : 0;
          //     bool s = await DBProvider.db.switchPanel(_panel);
          //     if (s){
          //       setState(() {
          //         _panel = _panel;
          //       });
          //     }
          //   },
          // ),
        ],
      ),
    );
  }
}