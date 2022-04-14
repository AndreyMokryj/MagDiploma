class Station{
  String id;
  String userId;
  String ukey;
  String name;
  int maxOutputPower;
  double energy;
  int gridConnection;
  int stationConnection;

  Station({this.id, this.userId, this.ukey, this.name, this.maxOutputPower, this.energy, this.gridConnection, this.stationConnection});

  Map<String, dynamic> toMap(){
    return {
      'id' : id,
      'userId' : userId,
      'ukey' : ukey,
      'name' : name,
      'maxOutputPower' : maxOutputPower,
      'energy' : energy,
      'gridConnection' : gridConnection,
      'stationConnection' : stationConnection,
    };
  }

  factory Station.fromMap(Map<String, dynamic> map){
    return Station(
      id: map['id'] as String,
      userId: map['userId'] as String,
      ukey: map['ukey'] as String,
      name: map['name'] as String,
      maxOutputPower: map['maxOutputPower'] as int,
      energy: map['energy'] as double,
      gridConnection: map['gridConnection'] as int,
      stationConnection: map['stationConnection'] as int,
    );
  }
}