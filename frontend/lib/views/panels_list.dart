import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/model/user_model.dart';
import 'package:SUNMAX/views/panel_widget.dart';
import 'package:provider/provider.dart';

class PanelsList extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    User user = Provider.of<LoginNotifier>(context, listen: false).user;

    return FutureBuilder(
      future: DBProvider.db.getPanels(user),

      builder: (context, snapshot){
        if (snapshot.hasData){
          final panelMaps = (snapshot.data as List);

          if (panelMaps.length > 0) {
            return SingleChildScrollView(
              child: Column(
                children: panelMaps.map((map) {
                  Panel panel = Panel.fromMap(map);

                  return PanelWidget(
                    panel: panel,
                  );
                }).toList(),
              ),
            );
          }
          else {
            return Center(
              child: Text("Немає панелей"),
            );
          }
        }
        else{
          return Container();
        }
      },
    );
  }
}