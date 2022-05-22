import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

//const baseUrl = kReleaseMode ? "https://mavlabs.hopto.org/" : "http://localhost:4444/" ;
const _baseUrl = "http://localhost:4444/app/";
const _baseAndroidUrl = "http://10.0.2.2:4444/app/";
String baseUrl = (!kIsWeb && Platform.isAndroid) ? _baseAndroidUrl : _baseUrl;

const pageNames = {
  'home' : 'Головна',
  'panels' : 'Мої панелі',
  'details' : 'Інформація про панель',
};

const monthNames = {
  '01' : 'січ',
  '02' : 'лют',
  '03' : 'бер',
  '04' : 'кві',
  '05' : 'тра',
  '06' : 'чер',
  '07' : 'лип',
  '08' : 'сер',
  '09' : 'вер',
  '10' : 'жов',
  '11' : 'лис',
  '12' : 'гру',
};

const times = [
  '00',
  '01',
  '02',
  '03',
  '04',
  '05',
  '06',
  '07',
  '08',
  '09',
  '10',
  '11',
  '12',
  '13',
  '14',
  '15',
  '16',
  '17',
  '18',
  '19',
  '20',
  '21',
  '22',
  '23',
];

const smallLimit = 600;
const mediumLimit = 900;
const largeLimit = 1200;

const legendItems = [
  {
    'title':'Вироблено',
    'color': Colors.green,
  },
  {
    'title':'Продано',
    'color': Colors.orange,
  },
];

const refreshRate = Duration(seconds: 5);

const buttonColor = Colors.blue;