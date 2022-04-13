import 'package:SUNMAX/helpers/styles.dart';
import 'package:flutter/material.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:provider/provider.dart';

class MenuWidget extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    double w = getWidth(context);

    return Container(
      color: Color.fromRGBO(180, 180, 180, 1),
      child: ListView(
        children: <Widget>[
          w <= largeLimit ? Align(
            alignment: Alignment.centerLeft,
            child: IconButton(
              icon: Icon(Icons.menu),
              onPressed: (){
                Navigator.of(context).pop();
              },
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
            leading: Icon(Icons.power),
            title: Text(
              "Мої панелі",
              style: normalTextStyle,
            ),
            onTap: (){
              Navigator.of(context).pushNamed('/panels');
            },
          ),
          ListTile(
            leading: Icon(Icons.exit_to_app),
            title: Text(
              "Вихід",
              style: normalTextStyle,
            ),
            onTap: (){
              Provider.of<LoginNotifier>(context, listen: false).logOut();
              Navigator.of(context).pushNamed('/login');            },
          ),
        ],
      ),
    );
  }
}