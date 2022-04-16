import 'package:SUNMAX/helpers/styles.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';

class MenuWidget extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    double w = getWidth(context);

    return Container(
      color: Color.fromRGBO(180, 180, 180, 1),
      child: ListView(
        children: <Widget>[
          w <= largeLimit ? Container(
            child: Stack(
              children: [
                Align(
                  alignment: Alignment.centerLeft,
                  child: IconButton(
                    icon: Icon(Icons.menu),
                    onPressed: (){
                      Navigator.of(context).pop();
                    },
                  ),
                ),
                Align(
                  alignment: Alignment.centerRight,
                  child: Container(
                    height: kToolbarHeight - 12,
                    margin: EdgeInsets.only(
                      bottom: 20,
                    ),
                    child: Image.asset(
                      "assets/images/logo.png",
                      fit: BoxFit.fitHeight,
                    ),
                  ),
                )
              ],
            ),
          ) : Container(),
          ListTile(
            leading: Icon(Icons.home),
            title: Text(
              "Головна",
              style: normalTextStyle,
            ),
            onTap: (){
              Navigator.of(context).pushNamed('/');
            },
          ),
          ListTile(
            leading: Icon(Icons.exit_to_app),
            title: Text(
              "Вихід",
              style: normalTextStyle,
            ),
            onTap: () {
              Navigator.of(context).pushNamed('/login');},
          ),
        ],
      ),
    );
  }
}