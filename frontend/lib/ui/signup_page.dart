import 'package:flutter/material.dart';
import 'package:SUNMAX/database/database.dart';
import 'package:SUNMAX/helpers/constants.dart';
import 'package:SUNMAX/helpers/styles.dart';
import 'package:SUNMAX/helpers/utils.dart';
import 'package:SUNMAX/model/user_model.dart';

class SignupPage extends StatefulWidget{
  @override
  _SignupPageState createState() => _SignupPageState();
}

class _SignupPageState extends State<SignupPage> {
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
    double w = getWidth(context);

    return Scaffold(
        body: SafeArea(
          child: Form(
            key: _formKey,
            child: SingleChildScrollView(
              padding: EdgeInsets.all(10.0),
              child: Center(
                child: Container(
                  width: w > smallLimit ? 500 : w,
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
                        validator: (val) => val.isEmpty ? "Введіть ім'я користувача" :
                          val.length < 5 ? "Має містити мінімум 5 символів" : null,
                        autovalidateMode: AutovalidateMode.onUserInteraction,
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
                        validator: (val) => val.isEmpty ? "Введіть пароль" :
                        val.length < 5 ? "Має містити мінімум 5 символів" : null,
                        autovalidateMode: AutovalidateMode.onUserInteraction,
                        onSaved: (val) => newUser.password = val,
                      ),

                      SizedBox(height: 15.0),
                      Text(
                        errorMsg,
                        style: errorTextStyle,
                      ),
                      SizedBox(height: 15.0),

                      TextButton(
                        child: Text(
                          'Зареєструватися'
                        ),
                        onPressed: () {
                          _submit();
                        }
                      ),
                    ],
                  ),
                ),
              ))
          ),
        ),
      );
  }

  void _submit() async {
    final FormState form = _formKey.currentState;
    if (form.validate()) {
      form.save();
      if (await DBProvider.db.newUser(newUser)) {
        Navigator.of(context).pushNamed('/login', arguments: "created");
      }
      else {
        setState(() {
          errorMsg = "Дане ім'я користувача зайняте. Спробуйте інше";
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