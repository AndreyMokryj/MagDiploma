import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/ui/widgets/menu_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:provider/provider.dart';

class MainPage extends StatelessWidget{
  final onWillPop;
  final future;
  final builder;
  final bool automaticallyImplyLeading;
  final String title;

  const MainPage({Key key, this.onWillPop, this.future, this.builder, this.automaticallyImplyLeading = true, this.title = ""}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    bool loggedIn = Provider.of<LoginNotifier>(context, listen: false).loggedIn ?? false;
    double w = getWidth(context);

    SchedulerBinding.instance.addPostFrameCallback((_) {
      if (!loggedIn) {
        Navigator.of(context).pushNamed("/login");
      }
    });
    if (loggedIn) {
      return WillPopScope(
        onWillPop: onWillPop ?? () {
          Navigator.of(context).pop();
        },
        child: Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: automaticallyImplyLeading,
            title: Text(title),
            centerTitle: true,
            actions: <Widget>[
              w <= largeLimit ?
              Builder(
                builder: (context) =>
                    IconButton(
                      icon: Icon(Icons.menu),
                      onPressed: () {
                        Scaffold.of(context).openEndDrawer();
                      },
                    ),
              ) : Container(),
            ],
          ),
          body: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              w > largeLimit ?
              Expanded(
                flex: 1,
                child: MenuWidget(),
              ) : Container(),
              Expanded(
                flex: 4,
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: FutureBuilder(
                    future: future,
                    builder: builder,
                  ),
                ),
              ),
            ],
          ),

          endDrawer: w <= largeLimit ? Drawer(
            child: MenuWidget(),
          ) : null,
        ),
      );
    }
    else {
      return Container();
    }
  }
}