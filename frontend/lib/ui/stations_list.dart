import 'package:SUNMAX/model/station_model.dart';
import 'package:SUNMAX/ui/main_page.dart';
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

    return MainPage(
      title: "Мої станції",
      future: DBProvider.db.getStations(user?.id),
      builder: (context, snapshot){
        if (snapshot.hasData){
          final stationMaps = (snapshot.data as List);

          if (stationMaps.length > 0) {
            return SingleChildScrollView(
              child: Column(
                children: stationMaps.map((map) {
                  Station station = Station.fromMap(map);

                  return StationCard(
                    station: station,
                  );
                }).toList(),
              ),
            );
          }
          else {
            return Center(
              child: Text("Немає станцій"),
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