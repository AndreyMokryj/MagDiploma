import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/views/menu_widget.dart';
import 'package:SUNMAX/views/panel_page.dart';
import 'package:SUNMAX/views/panels_list.dart';
import 'package:SUNMAX/views/station_page.dart';
import 'package:provider/provider.dart';

class MainView extends StatelessWidget {
  final String name;
  final String panelId;

  const MainView({Key key, this.name, this.panelId}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Panel panel;
    double w = getWidth(context);

    return WillPopScope(
      onWillPop: () {
        if (name == "details") {
          Navigator.of(context).pushNamed("/panels");
        }
        else {
          if (name != 'home') {
            Navigator.of(context).pop();
          }
        }
      },
      child: FutureBuilder(
        future: panelId != null ? DBProvider.db.getPanel(panelId, Provider
          .of<LoginNotifier>(context, listen: false)
          .user) : null,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            panel = snapshot.data;
          }

          if (snapshot.hasData || panelId == null) {
            return Scaffold(
              appBar: AppBar(
                automaticallyImplyLeading: panelId != null,
                title: Text(pageNames[name]),
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
                      child: Builder(
                        builder: (context) {
                          switch (name) {
                            case 'panels':
                              return PanelsList();
                              break;
                            case 'details':
                              return PanelPage(
                                panel: panel,
                              );
                              break;
                            default:
                              return StationPage();
                          }
                        },
                      ),
                    ),
                  ),
                ],
              ),

              endDrawer: w <= largeLimit ? Drawer(
                child: MenuWidget(),
              ) : null,
            );
          }
          else {
            return Container();
          }
        }
      ),
    );
  }
}