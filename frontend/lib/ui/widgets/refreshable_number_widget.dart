import 'dart:async';

import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/helpers/constants.dart';

class RefreshableNumberWidget extends StatefulWidget {
  final Panel panel;
  final future;

  const RefreshableNumberWidget({Key key, this.panel, this.future}) : super(key: key);

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
      future: widget.future(context, panel: widget.panel),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Text("${formatDouble(snapshot.data, 2)}");
        }
        return Container();
      }
    );
  }
}
