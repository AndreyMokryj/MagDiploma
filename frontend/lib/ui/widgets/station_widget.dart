import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/route.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/station_model.dart';

class StationWidget extends StatefulWidget{
  final Station station;
  final bool primary;

  const StationWidget({Key key, this.station, this.primary = true}) : super(key: key);

  @override
  _StationWidgetState createState() => _StationWidgetState();
}

class _StationWidgetState extends State<StationWidget> {
  Station _station;
  bool interactive = false;

  @override
  void initState() {
    _station = widget.station;
    interactive = widget.primary;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    double w = getWidth(context);

    return Row(
      mainAxisSize: MainAxisSize.min,
      children: <Widget>[
        Expanded(
          flex: 2,
          child: GestureDetector(
            onTap: interactive ? (){
              Navigator.of(context).pushNamed("/${RoutePaths.stations}/${_station.ukey}/${RoutePaths.panels}");
            } : null,
            child: Column(
              children: [
                Align(
                  alignment: Alignment.center,
                  child: Icon(
                    Icons.more_horiz,
                    color: Colors.transparent,
                  ),
                ),
                Image.asset(
                  "assets/images/panels.png",
                  fit: BoxFit.fitWidth,
                ),
                Align(
                  alignment: Alignment.center,
                  child: Icon(
                    Icons.more_horiz,
                    color: interactive ? Colors.grey : Colors.transparent,
                  ),
                ),
              ],
            ),
          ),
        ),
        Container(
          width: 2,
          height: 20,
          color: Colors.black,
        ),
        Expanded(
          flex: w > mediumLimit && interactive ? 0 : 1,
          child: FlatButton(
            padding: EdgeInsets.zero,
            child: Image.asset(
              _station.stationConnection == 1
                ? "assets/images/switch/h_switch_on.png"
                : "assets/images/switch/h_switch_off.png",
              fit: BoxFit.fitWidth,
            ),
            onPressed: interactive ? () async {
              _station.stationConnection =
                1 - _station.stationConnection;
              await DBProvider.db.switchStation(_station);
              setState(() {
                _station = _station;
              });
            } : null,
          ),
        ),
        Container(
          width: 2,
          height: 20,
          color: Colors.black,
        ),
        Expanded(
          flex: 2,
          child: Image.asset(
            "assets/images/battery.png",
            fit: BoxFit.fitWidth,
          )
        ),
        Container(
          width: 2,
          height: 20,
          color: Colors.black,
        ),
        Expanded(
          flex: w > mediumLimit && interactive ? 0 : 1,
          child: FlatButton(
            padding: EdgeInsets.zero,
            child: Image.asset(
              _station.gridConnection == 1
                ? "assets/images/switch/h_switch_on.png"
                : "assets/images/switch/h_switch_off.png",
              fit: BoxFit.fitWidth,
            ),
            onPressed: interactive ? () async {
              _station.gridConnection = 1 - _station.gridConnection;
              await DBProvider.db.switchGrid(_station);

              setState(() {
                _station = _station;
              });
            } : null,
          ),
        ),
        Container(
          width: 2,
          height: 20,
          color: Colors.black,
        ),
        Expanded(
          flex: 2,
          child: Image.asset(
            "assets/images/grid.png",
            fit: BoxFit.fitWidth,
          ),
        ),
      ],
    );
  }
}