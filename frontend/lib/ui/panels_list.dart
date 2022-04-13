import 'package:SUNMAX/ui/main_page.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/ui/widgets/panel_widget.dart';

class PanelsList extends StatelessWidget{
  final String ukey;

  const PanelsList({Key key, this.ukey}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MainPage(
      title: "Панелі станції",
      future: DBProvider.db.getPanels(ukey),
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