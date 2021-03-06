import 'package:SUNMAX/helpers/styles.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/station_model.dart';
import 'package:SUNMAX/ui/widgets/refreshable_number_widget.dart';
import 'package:SUNMAX/ui/widgets/station_widget.dart';
import 'package:flutter/material.dart';

class StationCard extends StatelessWidget{
  final Station station;

  const StationCard({Key key, this.station}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      borderRadius: BorderRadius.circular(10),
      hoverColor: Colors.orange.withOpacity(0.12),
      splashColor: Colors.orange.withOpacity(0.12),
      highlightColor: Colors.orange.withOpacity(0.12),
      focusColor: Colors.orange.withOpacity(0.12),
      onTap: (){
        Navigator.of(context).pushNamed("/stations/${station.ukey}");
      },
      child: Container(
        padding: EdgeInsets.all(5),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10),
            border: Border.all(
              color: Colors.orange,
            )
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            StationWidget(
              station: station,
              primary: false,
            ),
            Spacer(),
            Divider(),
            Row(
              children: [
                SizedBox(
                  width: 10,
                ),
                Expanded(
                  child: Text(
                    station.name,
                    style: normalTextStyle,
                  )
                ),
                Expanded(
                  child: Row(
                    children: <Widget>[
                      RefreshableNumberWidget(
                        future: getRequiredPower,
                        stationId: station.id,
                      ),
                      Text(" W"),
                    ],
                  ),
                ),
              ],
            ),
            Divider(),
          ],
        ),
      ),
    );
  }
}