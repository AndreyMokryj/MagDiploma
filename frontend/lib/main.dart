import 'package:SUNMAX/app.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/ui/main_view.dart';
import 'package:provider/provider.dart';

void main() => runApp(
  App()
);

// class MainPage extends StatelessWidget {
//   final String name;
//   final String panelId;
//
//   MainPage({Key key, this.name = "home", this.panelId}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     bool loggedIn = Provider.of<LoginNotifier>(context, listen: false).loggedIn ?? false;
//
//     SchedulerBinding.instance.addPostFrameCallback((_) {
//       if (!loggedIn) {
//         Navigator.of(context).pushNamed("/login");
//       }
//     });
//     if (loggedIn) {
//       return MainView(
//         name: name,
//         panelId: panelId,
//       );
//     }
//     else {
//       return Container();
//     }
//   }
// }
