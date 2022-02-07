import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/styles.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/notifiers/login_notifier.dart';
import 'package:SUNMAX/model/user_model.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatefulWidget{
  final bool created;

  const LoginPage({Key key, this.created = false}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();

  User newUser = User();
  bool validate;
  String errorMsg;

  @override
  void initState() {
    validate = false;
    errorMsg = "";

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      if (widget.created) {
        Scaffold.of(context).showSnackBar(
          SnackBar(content: Text("Новий користувач створений!")),
        );
      }
    });

    bool loggedIn = Provider
      .of<LoginNotifier>(context, listen: false)
      .loggedIn ?? false;

    double w = getWidth(context);

    return WillPopScope(
      onWillPop: () {
        Navigator.of(context).pushNamed('/');
      },
      child: Scaffold(
        primary: false,
        body: SafeArea(
          child: Form(
            key: _formKey,
            child: SingleChildScrollView(
              padding: EdgeInsets.all(10.0),
              child: Center(
                child: Container(
                  width: w > smallLimit ? 450 : w,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: <Widget>[
                      Text("Введіть ім'я користувача та пароль",
                        style: normalTextStyle,
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 12.0),
                      TextFormField(
                        style: normalTextStyle,
                        enabled: true,
                        decoration: AppDecoration(
                          labelText: "Ім'я користувача",
                          hintText: "Ім'я користувача",
                        ),
                        keyboardType: TextInputType.emailAddress,
                        validator: (val) =>
                        val.isEmpty
                          ? "Введіть ім'я користувача"
                          : null,
                        autovalidate: validate,
                        onSaved: (val) => newUser.username = val,
                      ),
                      const SizedBox(height: 20.0),
                      TextFormField(
                        style: normalTextStyle,
                        enabled: true,
                        decoration: AppDecoration(
                          labelText: 'Пароль',
                          hintText: 'Пароль для входу'
                        ),
                        obscureText: true,
                        validator: (val) =>
                        val.isEmpty
                          ? "Введіть пароль"
                          : null,
                        autovalidate: validate,
                        onSaved: (val) => newUser.password = val,
                      ),
                      SizedBox(height: 10.0),
                      Text(
                        errorMsg,
                        style: errorTextStyle,
                      ),
                      SizedBox(height: 10.0),

                      FlatButton(
                        color: buttonColor,
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(6.0)),
                        child: SizedBox(
                          height: 50,
                          child: Align(
                            child: Text(
                              'Увійти',
                              style: TextStyle(
                                color: Colors.white,
                                fontSize: 16
                              ),
                            ),
                          ),
                        ),
                        onPressed: () => _submit(),
                      ),
                    ],
                  ),
                ),
              ))
          ),
        ),
      ),
    );
  }

  void _submit() async {
    final FormState form = _formKey.currentState;
    if (form.validate()) {
      form.save();
      User regUser = await DBProvider.db.checkUser(newUser);
      if (regUser != null) {
        Provider.of<LoginNotifier>(context, listen: false).logIn(regUser);
        Navigator.of(context).pushNamed('/');
      }
      else{
        setState(() {
          errorMsg = "Неправильне ім'я користувача або пароль.";
        });
      }
    }
    else {
      setState(() {
        validate = true;
      });
    }
  }
}