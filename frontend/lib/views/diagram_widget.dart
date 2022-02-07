import 'dart:async';

import 'package:SUNMAX/helpers/styles.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/model/log_model.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:syncfusion_flutter_charts/charts.dart';

class DiagramWidget extends StatefulWidget {
  final Panel panel;

  const DiagramWidget({Key key, this.panel}) : super(key: key);

  @override
  _DiagramWidgetState createState() => _DiagramWidgetState();
}

class _DiagramWidgetState extends State<DiagramWidget> {
  Future future;
  Timer _timer;

  @override
  void initState() {
    _timer = new Timer.periodic(refreshRate,
        (Timer timer)async {
          setState(() {});
        });
    super.initState();
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    double w = getWidth(context);
    double h = getHeight(context);
    future = widget.panel != null ? getPanelTodayLogs(widget.panel) : getAlllTodayLogs(context);

    return FutureBuilder(
    future: future,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          var logMaps = snapshot.data as List<Log>;

          times.forEach((time) {
            var logs = logMaps.where((element) =>
              element.time.startsWith(time));
            if (logs.length == 0) {
              Log newLog = Log(
                time: "${time}:00",
                produced: 0,
                given: 0,
              );
              logMaps.add(newLog);
            }
          });
          logMaps.sort((l1, l2) => l1.time.compareTo(l2.time));

          var list = [0];
          if (widget.panel == null) {
            list.add(1);
          }

          var series = list.map((e) =>
            ColumnSeries<Log, String>(
              dataSource: logMaps,
              xValueMapper: (Log log, _) => log.time.substring(0, 2),
              yValueMapper: (Log log, _) =>
              e == 0
                ? log.produced / 3600000
                : log.given / 3600000,
              legendItemText: legendItems[e]['title'],
              color: legendItems[e]['color'],
            )).toList();

          return Column(
            children: <Widget>[
              Text("Виробіток сьогодні:"),
              SizedBox(
                height: 5,
              ),
              FutureBuilder(
                future: getDateTime(context),
                builder: (context1, snapshot1){
                  return Text(snapshot1.hasData ? snapshot1.data : "");
                },
              ),
              SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: Container(
                  width: w < mediumLimit ? mediumLimit * 1.0 :
                  (w < largeLimit ? w * 0.95 : w * 0.75),
                  child: SfCartesianChart(
                    primaryXAxis: CategoryAxis(),
                    primaryYAxis: NumericAxis(
                      title: AxisTitle(
                        text: 'кВат * год',
                      )
                    ),
                    legend: Legend(isVisible: w >= mediumLimit),
                    tooltipBehavior: TooltipBehavior(enable: true),
                    series: series,
                  )
                ),
              ),
              Text(
                "Час",
                style: littleTextStyle,
              ),

              w < mediumLimit ? Column(
                children: list.map((el) =>
                  Row(
                    children: <Widget>[
                      Container(
                        margin: EdgeInsets.only(
                          top: 10,
                          left: 20,
                          right: 20,
                        ),
                        width: 20,
                        height: 20,
                        color: legendItems[el]['color'],
                      ),
                      Text(
                        legendItems[el]['title'],
                        style: littleTextStyle,
                      )
                    ],
                  )).toList()
              ) : Container(),
            ],
          );
        }
        else {
          return Container();
        }
      },
    );
  }
}
