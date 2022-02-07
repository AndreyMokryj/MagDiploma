class Log{
  String id;
  String date;
  String time;
  String userId;
  String panelId;
  double produced;
  double given;

  Log({this.id, this.userId, this.date, this.time, this.panelId, this.produced, this.given,});

  Map<String, dynamic> toMap(){
    return {
      'id' : id,
      'userId' : userId,
      'date' : date,
      'time' : time,
      'panelId' : panelId,
      'produced' : produced,
      'given' : given,
    };
  }

  factory Log.fromMap(Map<String, dynamic> map){
    return Log(
      id: map['id'] as String,
      userId: map['userId'] as String,
      date: map['date'] as String,
      time: map['time'] as String,
      panelId: map['panelId'] as String,
      produced: map['produced'] as double,
      given: map['given'] as double,
    );
  }
}