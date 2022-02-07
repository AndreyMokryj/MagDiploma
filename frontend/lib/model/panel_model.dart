class Panel{
  String id;
  String userId;
  String name;
  String model;
  int nominalPower;
  int azimuth;
  int altitude;
  int connected;

  Panel({this.id, this.userId, this.name, this.model, this.nominalPower, this.azimuth, this.altitude, this.connected});

  Map<String, dynamic> toMap(){
    return {
      'id' : id,
      'userId' : userId,
      'name' : name,
      'model' : model,
      'nominalPower' : nominalPower,
      'azimuth' : azimuth,
      'altitude' : altitude,
      'connected' : connected,
    };
  }

  factory Panel.fromMap(Map<String, dynamic> map){
    return Panel(
      id: map['id'] as String,
      userId: map['userId'] as String,
      name: map['name'] as String,
      model: map['model'] as String,
      nominalPower: map['nominalPower'] as int,
      azimuth: map['azimuth'] as int,
      altitude: map['altitude'] as int,
      connected: map['connected'] as int,
    );
  }
}