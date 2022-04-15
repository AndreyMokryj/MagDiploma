import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/route.dart';
import 'package:SUNMAX/ui/widgets/menu_widget.dart';
import 'package:flutter/material.dart';

class MainView extends StatelessWidget{
  final onWillPop;
  final future;
  final builder;
  final bool automaticallyImplyLeading;
  final String title;

  const MainView({Key key, this.onWillPop, this.future, this.builder, this.automaticallyImplyLeading = true, this.title = ""}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    double w = getWidth(context);
    final _onWillPop = onWillPop ?? () {
      Navigator.of(context).pop();
    };

    return WillPopScope(
      onWillPop: _onWillPop,
      child: Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          leading: automaticallyImplyLeading ? IconButton(
            icon: Icon(Icons.arrow_back_ios),
            onPressed: _onWillPop,
          ) : Container(),
          title: Container(
            height: kToolbarHeight,
            child: Stack(
              children:  [
                Align(child: Text(title)),
                Align(
                  alignment: Alignment.centerLeft,
                  child: w > largeLimit ? Container(
                    padding: EdgeInsets.all(3),
                    child: GestureDetector(
                      onTap: (){
                        Navigator.of(context).pushNamed("/${RoutePaths.stations}");
                      },
                      child: Image.asset(
                        "assets/images/logo.png",
                        fit: BoxFit.fitHeight,
                      ),
                    ),
                  ) : null,
                ),
              ],
            ),
          ),
          centerTitle: true,
          actions: <Widget>[
            w <= largeLimit
              ? Builder(
                  builder: (context) =>
                    IconButton(
                      icon: Icon(Icons.menu),
                      onPressed: () {
                        Scaffold.of(context).openEndDrawer();
                      },
                    ),
                )
              : IconButton(
                  icon: Icon(Icons.exit_to_app),
                  onPressed: () {
                    Navigator.of(context).pushNamed("/${RoutePaths.login}");
                  },
                ),
          ],
        ),
        body: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            w > largeLimit ?
            Expanded(
              flex: 1,
              child: Container(),
            ) : Container(),
            Expanded(
              flex: 8,
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: FutureBuilder(
                  future: future,
                  builder: builder,
                ),
              ),
            ),
            w > largeLimit ?
            Expanded(
              flex: 1,
              child: Container(),
            ) : Container(),
          ],
        ),

        endDrawer: w <= largeLimit ? Drawer(
          child: MenuWidget(),
        ) : null,
      ),
    );
  }
}