import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:provider/provider.dart';

class WrapPage extends StatelessWidget {
  final Widget child;

  const WrapPage({Key key, this.child}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    bool loggedIn = Provider.of<LoginNotifier>(context, listen: false).loggedIn ?? false;
    SchedulerBinding.instance.addPostFrameCallback((_) {
      if (!loggedIn) {
        Navigator.of(context).pushNamed("/login");
      }
    });

    if (loggedIn) {
      return child;
    }
    else {
      return Container();
    }
  }
}
