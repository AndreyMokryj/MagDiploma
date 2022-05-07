import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/station_model.dart';
import 'package:SUNMAX/route.dart';
import 'package:SUNMAX/ui/main_view.dart';
import 'package:SUNMAX/ui/widgets/station_card.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/user_model.dart';
import 'package:provider/provider.dart';

class StationsList extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    User user = Provider.of<LoginNotifier>(context, listen: false).user;

    int count = 1;
    double w = getWidth(context);
    if (w > smallLimit) {
      count += 1;
    }
    if (w > mediumLimit) {
      count += 1;
    }

    return MainView(
      title: Text("Мої станції"),
      onWillPop: (){
        Navigator.of(context).pushNamed("/${RoutePaths.login}");
        return Future.value(true);
      },
      automaticallyImplyLeading: false,
      future: DBProvider.db.getStations(user.id),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          final stationMaps = (snapshot.data as List);

          if (stationMaps.length > 0) {
            return GridView.count(
              crossAxisCount: count,
              shrinkWrap: true,
              mainAxisSpacing: 10,
              crossAxisSpacing: 10,
              childAspectRatio: 1.5,
              padding: EdgeInsets.all(5),
              children: stationMaps.map((map) {
                Station station = Station.fromMap(map);

                return StationCard(
                  station: station,
                );
              }).toList(),
              physics: NeverScrollableScrollPhysics(),
            );
          }
          else {
            return Center(
              child: Text("Немає станцій"),
            );
          }
        }
        else {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }
}