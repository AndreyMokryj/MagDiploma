import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/route.dart';
import 'package:SUNMAX/ui/main_view.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/panel_model.dart';
import 'package:SUNMAX/ui/widgets/panel_widget.dart';

class PanelsList extends StatelessWidget{
  final String ukey;

  const PanelsList({Key key, this.ukey}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MainView(
      title: FutureBuilder(
        future: getStation(context, ukey),
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return Text("Панелі станції ${snapshot.data.name ?? ""}");
          }
          return Container();
        },
      ),
      onWillPop: (){
        Navigator.of(context).pushNamed("/${RoutePaths.stations}/${ukey}");
        return Future.value(true);
      },
      future: getStation(context, ukey)
          .then((value) => DBProvider.db.getPanels(value.id)),
      builder: (context, snapshot){
        if (snapshot.hasData){
          final panelMaps = (snapshot.data as List);

          if (panelMaps.length > 0) {
            return Column(
              children: panelMaps.map((map) {
                Panel panel = Panel.fromMap(map);

                return PanelWidget(
                  panel: panel,
                  ukey: ukey,
                );
              }).toList(),
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