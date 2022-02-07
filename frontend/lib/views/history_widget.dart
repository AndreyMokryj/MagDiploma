import 'package:SUNMAX/helpers/constants.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';

class HistoryWidget extends StatefulWidget{
  final Panel panel;

  const HistoryWidget({Key key, this.panel}) : super(key: key);

  @override
  _HistoryWidgetState createState() => _HistoryWidgetState();
}

class _HistoryWidgetState extends State<HistoryWidget> {
  bool showHistory;

  @override
  void initState() {
    showHistory = false;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    double w = getWidth(context);
    double h = getHeight(context);

    int count = 1;
    if(w >= mediumLimit){
      count += 1;
    }

    return Column(
      children: <Widget>[
        Container(
          child: ListTile(
            leading: Icon(
              showHistory ? Icons.keyboard_arrow_up : Icons.keyboard_arrow_down,
            ),
            title: Text("Історія"),
            onTap: () {
              setState(() {
                showHistory = !showHistory;
              });
            },
          ),
        ),
        showHistory ? FutureBuilder(
          future: widget.panel != null
            ? getPanelHistoryLogs(widget.panel)
            : getAlllHistoryLogs(context),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              final logMaps = snapshot.data as List;
              logMaps.sort((l1, l2) => l2.date.compareTo(l1.date));

              return Column(
                children: <Widget>[
                  Divider(
                    thickness: 1.1,
                  ),
                  Row(
                    children: <Widget>[
                      Expanded(
                        child: Text("Дата"),
                      ),
                      Expanded(
                        flex: count,
                        child: Text("Вироблено енергії, кВт-год"),
                      ),
                      widget.panel == null
                        ? Expanded(
                        flex: count,
                        child: Text("Продано енергії, кВт-год"),
                      )
                        : Container(),
                    ],
                  ),
                ] +
                  logMaps.map((e) {
                    return Column(
                      children: <Widget>[
                        Divider(),
                        Row(
                          children: <Widget>[
                            Expanded(
                              child: Text(formatDate(e.date)),
                            ),
                            Expanded(
                              flex: count,
                              child: Text(
                                "${formatDouble(e.produced / 3600000, 2)}"
                              ),
                            ),
                            widget.panel == null
                              ? Expanded(
                              flex: count,
                              child: Text("${formatDouble(e.given / 3600000, 2)}"),
                            )
                              : Container(),
                          ],
                        ),
                      ],
                    );
                  }
                  ).toList(),
              );
            }
            else {
              return Container();
            }
          },
        ) : Container(),
      ],
    );
  }
}
