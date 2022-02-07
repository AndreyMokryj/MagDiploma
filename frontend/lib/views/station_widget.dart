import 'package:SUNMAX/helpers/constants.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/accumulator_model.dart' as acc;

class StationWidget extends StatefulWidget{
  final acc.Accumulator accumulator;

  const StationWidget({Key key, this.accumulator}) : super(key: key);

  @override
  _StationWidgetState createState() => _StationWidgetState();
}

class _StationWidgetState extends State<StationWidget> {
  acc.Accumulator _accumulator;
  bool showHistory;

  @override
  void initState() {
    _accumulator = widget.accumulator;
    showHistory = false;
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
          child: Image.asset(
            "assets/images/panels.png",
            fit: BoxFit.fitWidth,
          ),
        ),
        Container(
          width: 2,
          height: 20,
          color: Colors.black,
        ),
        Expanded(
          flex: w > mediumLimit ? 0 : 1,
          child: FlatButton(
            padding: EdgeInsets.zero,
            child: Image.asset(
              _accumulator.stationConnection == 1
                ? "assets/images/switch/h_switch_on.png"
                : "assets/images/switch/h_switch_off.png",
              fit: BoxFit.fitWidth,
            ),
            onPressed: () async {
              _accumulator.stationConnection =
                1 - _accumulator.stationConnection;
              await DBProvider.db.switchStation(_accumulator);
              setState(() {
                _accumulator = _accumulator;
              });
            },
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
          flex: w > mediumLimit ? 0 : 1,
          child: FlatButton(
            padding: EdgeInsets.zero,
            child: Image.asset(
              _accumulator.gridConnection == 1
                ? "assets/images/switch/h_switch_on.png"
                : "assets/images/switch/h_switch_off.png",
              fit: BoxFit.fitWidth,
            ),
            onPressed: () async {
              _accumulator.gridConnection = 1 - _accumulator.gridConnection;
              await DBProvider.db.switchGrid(_accumulator);

              setState(() {
                _accumulator = _accumulator;
              });
            },
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