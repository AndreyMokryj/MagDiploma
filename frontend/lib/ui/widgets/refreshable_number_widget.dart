import 'dart:async';

import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/helpers/constants.dart';

class RefreshableNumberWidget extends StatefulWidget {
  final Panel panel;
  final String stationId;
  final String ukey;
  final future;

  const RefreshableNumberWidget({Key key, this.panel, this.future, this.stationId, this.ukey}) : super(key: key);

  @override
  _RefreshableNumberWidgetState createState() => _RefreshableNumberWidgetState();
}

class _RefreshableNumberWidgetState extends State<RefreshableNumberWidget> {
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
    return FutureBuilder(
      future: widget.ukey == null
          ? widget.future(stationId: widget.stationId, panel: widget.panel)
          : widget.future(context, widget.ukey),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Text("${formatDouble(snapshot.data, 2)}");
        }
        return Container();
      }
    );
  }
}
