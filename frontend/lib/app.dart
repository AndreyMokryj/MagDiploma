import 'package:SUNMAX/helpers/styles.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/notifiers/name_notifier.dart';
import 'package:SUNMAX/route.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class App extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider.value(
      value: globalLoginNotifier,
      child: ChangeNotifierProvider(
        create: (context) => NameNotifier(),
        child: MaterialApp(
          title: 'SUNMAX',
          debugShowCheckedModeBanner: false,
          theme: ThemeData(
            primarySwatch: Colors.blue,
            textTheme: TextTheme(
              // Regular text
              bodyText2: normalTextStyle,

              // ListTile
              subtitle1: bigTextStyle,

              //Buttons
              button: normalTextStyle,
            ),
          ),
          // home: StationsList(),
          initialRoute: '/',
          onGenerateRoute: getRoute,
        ),
      ),
    );
  }
}

final globalLoginNotifier = LoginNotifier();
