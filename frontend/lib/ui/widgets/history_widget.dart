import 'package:SUNMAX/helpers/constants.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';

class HistoryWidget extends StatefulWidget{
  final Panel panel;
  final String stationId;

  const HistoryWidget({Key key, this.panel, this.stationId}) : super(key: key);

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

    int count = 1;
    bool showSpacer = false;
    TextAlign dateAlign = TextAlign.left;
    if(w >= mediumLimit){
      count += 1;
      dateAlign = TextAlign.center;
      showSpacer = widget.panel != null;
    }
    if(widget.panel != null) {
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
            : getAlllHistoryLogs(widget.stationId),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              final logMaps = snapshot.data as List;
              logMaps.sort((l1, l2) => l2.date.compareTo(l1.date));

              return Column(
                children: <Widget>[
                  Divider(
                    thickness: 1.1,
                  ),
                  Container(
                    padding: EdgeInsets.only(
                      left: 15,
                    ),
                    child: Row(
                      children: <Widget>[
                        Expanded(
                          child: Text(
                            "Дата",
                            textAlign: dateAlign,
                          ),
                        ),
                        Expanded(
                          flex: count,
                          child: Text(
                            "Вироблено енергії, кВт-год",
                            textAlign: TextAlign.center,
                          ),
                        ),
                        widget.panel == null
                          ? Expanded(
                          flex: count,
                          child: Text(
                            "Продано енергії, кВт-год",
                            textAlign: TextAlign.center,
                          ),
                        )
                          : Container(),
                        showSpacer
                            ? Expanded(
                          flex: 1,
                          child: Container(),
                        )
                            : Container(),
                      ],
                    ),
                  ),
                ] +
                  logMaps.map((e) {
                    return Column(
                      children: <Widget>[
                        Divider(),
                        Container(
                          padding: EdgeInsets.only(
                            left: 15,
                          ),
                          child: Row(
                            children: <Widget>[
                              Expanded(
                                child: Text(
                                  formatDate(e.date),
                                  textAlign: dateAlign,
                                ),
                              ),
                              Expanded(
                                flex: count,
                                child: Text(
                                  "${formatDouble(e.produced / 3600000, 2)}",
                                  textAlign: TextAlign.center,
                                ),
                              ),
                              widget.panel == null
                                ? Expanded(
                                flex: count,
                                child: Text(
                                  "${formatDouble(e.given / 3600000, 2)}",
                                  textAlign: TextAlign.center,
                                ),
                              )
                                : Container(),
                              showSpacer
                                  ? Expanded(
                                flex: 1,
                                child: Container(),
                              )
                                  : Container(),
                            ],
                          ),
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
